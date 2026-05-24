package me.theabab2333.harvestheritage.block.entity;

import lombok.Getter;
import lombok.Setter;
import me.theabab2333.harvestheritage.block.BaseCropStandBlock;
import me.theabab2333.harvestheritage.component.SeedComponent;
import me.theabab2333.harvestheritage.component.SeedPacketComponent;
import me.theabab2333.harvestheritage.init.ModDataComponents;
import me.theabab2333.harvestheritage.init.ModRecipes;
import me.theabab2333.harvestheritage.recipe.HybridRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseCropStandBlockEntity extends BlockEntity {
    public BaseCropStandBlockEntity(BlockEntityType<?> type, BlockPos worldPosition, BlockState blockState) {
        super(type, worldPosition, blockState);
    }

    @Getter
    protected SeedPacketComponent seedPacketComponent;
    @Getter
    @Setter
    protected int stage = 0;

    public void seedUseOn(ItemStack itemStack) {
        if (level == null || level.isClientSide()) return;
        level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        if (seedPacketComponent == null) {
            if (itemStack.get(ModDataComponents.SEED_COMPONENT) instanceof SeedComponent component) {
                this.seedPacketComponent = SeedPacketComponent.createSeedPacket(component, 1, 1);
            } else if (itemStack.get(ModDataComponents.SEED_PACKET_COMPONENT) instanceof SeedPacketComponent component) {
                this.seedPacketComponent = component;
            }
            itemStack.shrink(1);
        }
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return this.saveWithoutMetadata(registries);
    }

    @Override
    public void handleUpdateTag(ValueInput input) {
        input.read("component", SeedPacketComponent.CODEC);
        input.getIntOr("stage", 0);
        super.handleUpdateTag(input);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ValueInput valueInput) {
        valueInput.read("component", SeedPacketComponent.CODEC);
        valueInput.getIntOr("stage", 0);
        super.onDataPacket(net, valueInput);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        this.seedPacketComponent = input.read("component", SeedPacketComponent.CODEC).orElse(null);
        this.stage = input.getIntOr("stage", 0);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        if (seedPacketComponent != null) {
            output.store("component", SeedPacketComponent.CODEC, this.seedPacketComponent);
        }
        output.putInt("stage", this.stage);
    }

    public void setSeedPacketComponent(SeedPacketComponent seedPacketComponent) {
        if (level != null) {
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            this.seedPacketComponent = seedPacketComponent;
        }
    }

    public void tick(ServerLevel level, BlockPos pos, BlockState state, RandomSource random) {
        level.sendBlockUpdated(pos, state, state, 3);
        if (this.seedPacketComponent != null) {
            int speed = seedPacketComponent.speed();
            SeedComponent seedComponent = seedPacketComponent.seedComponent();
            int needStage = seedComponent.stage();
            if (random.nextInt(3) < speed) {
                if (this.stage < needStage) {
                    this.stage++;
                } else if (needStage == this.stage) {
                    find(level, pos);
                }
            }
        }
    }

    @SuppressWarnings("ConstantValue")
    public void find(ServerLevel level, BlockPos pos) {
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos pos1 = pos.relative(direction, 1);
            BlockState state1 = level.getBlockState(pos1);
            if (state1.getBlock() instanceof BaseCropStandBlock) {
                BaseCropStandBlockEntity be1 = (BaseCropStandBlockEntity) level.getBlockEntity(pos1);
                if (be1 == null) return;
                if (be1.getSeedPacketComponent() == null) {
                    BlockPos pos2 = pos.relative(direction, 2);
                    BlockState state2 = level.getBlockState(pos2);
                    if (state2.getBlock() instanceof BaseCropStandBlock) {
                        BaseCropStandBlockEntity be2 = (BaseCropStandBlockEntity) level.getBlockEntity(pos2);
                        SeedPacketComponent component = be2.getSeedPacketComponent();
                        if (component != null && component.seedComponent().stage() == be2.stage) {
                            hybrid(be1, this.seedPacketComponent, component, level);
                            return;
                        }
                    }
                }
            }
        }
    }

    public void hybrid(
        BaseCropStandBlockEntity cropStandBlock,
        SeedPacketComponent component1,
        SeedPacketComponent component2,
        ServerLevel level
    ) {
        var holders = level.recipeAccess().recipeMap().byType(ModRecipes.HYBRID_TYPE.get());

        if (holders.isEmpty()) return;

        SeedComponent seed1 = component1.seedComponent();
        SeedComponent seed2 = component2.seedComponent();

        for (RecipeHolder<HybridRecipe> holder : holders) {
            HybridRecipe recipe = holder.value();
            List<Holder<Item>> inputList = recipe.getInputSeeds();
            if (inputList.size() == 2) {
                Holder<Item> input1 = seed1.seed();
                Holder<Item> input2 = seed2.seed();

                boolean match1 = inputList.stream().map(Holder::value).anyMatch(item -> item == input1.value());
                boolean match2 = inputList.stream().map(Holder::value).anyMatch(item -> item == input2.value());

                if (match1 && match2 && input1.value() != input2.value()) {
                    List<SeedComponent> outputs = recipe.getOutputSeeds();
                    List<SeedComponent> seeds = new ArrayList<>(outputs);
                    seeds.add(seed1);
                    seeds.add(seed2);

                    SeedPacketComponent component = this.updateSeed(level, component1, component2, seeds);
                    cropStandBlock.setSeedPacketComponent(component);
                    return;
                }
            }
        }

        SeedPacketComponent component = this.updateSeed(level, component1, component2, List.of());
        cropStandBlock.setSeedPacketComponent(component);
    }

    public SeedPacketComponent updateSeed(
        Level level,
        SeedPacketComponent component1,
        SeedPacketComponent component2,
        List<SeedComponent> seeds
    ) {
        int speed1 = component1.speed();
        int output1 = component1.output();

        int speed2 = component2.speed();
        int output2 = component2.output();

        RandomSource random = level.getRandom();

        // speed
        int minSpeed = Math.min(speed1, speed2);
        int avgSpeed = (speed1 + speed2) / 2;
        int finalSpeed;

        double speedRoll = random.nextDouble();
        if (speedRoll < 0.05) {
            finalSpeed = Math.max(1, minSpeed - random.nextInt(2) - 1);
        } else if (speedRoll < 0.8) {
            finalSpeed = Math.min(31, avgSpeed + random.nextInt(2) + 1);
        } else {
            finalSpeed = avgSpeed;
        }

        // output
        int minOutput = Math.min(output1, output2);
        int avgOutput = (output1 + output2) / 2;
        int finalOutput;

        double outputRoll = random.nextDouble();
        if (outputRoll < 0.05) {
            finalOutput = Math.max(1, minOutput - random.nextInt(2) - 1);
        } else if (outputRoll < 0.8) {
            finalOutput = Math.min(31, avgOutput + random.nextInt(2) + 1);
        } else {
            finalOutput = avgOutput;
        }

        // seed
        SeedComponent finalSeedComponent;
        double seedRoll = random.nextDouble();
        if (seedRoll < 0.5) {
            finalSeedComponent = random.nextBoolean() ? component1.seedComponent() : component2.seedComponent();
        } else {
            if (!seeds.isEmpty()) {
                finalSeedComponent = seeds.get(random.nextInt(seeds.size()));
            } else {
                finalSeedComponent = random.nextBoolean() ? component1.seedComponent() : component2.seedComponent();
            }
        }


        return new SeedPacketComponent(finalSeedComponent, finalSpeed, finalOutput);
    }
}
