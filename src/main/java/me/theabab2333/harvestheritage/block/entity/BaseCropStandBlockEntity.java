package me.theabab2333.harvestheritage.block.entity;

import lombok.Getter;
import me.theabab2333.harvestheritage.block.BaseCropStandBlock;
import me.theabab2333.harvestheritage.component.SeedComponent;
import me.theabab2333.harvestheritage.component.SeedPacketComponent;
import me.theabab2333.harvestheritage.init.ModDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;

public abstract class BaseCropStandBlockEntity extends BlockEntity {
    public BaseCropStandBlockEntity(BlockEntityType<?> type, BlockPos worldPosition, BlockState blockState) {
        super(type, worldPosition, blockState);
    }

    @Getter
    protected SeedPacketComponent seedPacketComponent;
    @Getter
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
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
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

    public void tick(ServerLevel level, BlockPos pos, BlockState state, RandomSource random) {
        level.sendBlockUpdated(pos, state, state, 3);
        if (this.seedPacketComponent != null) {
            int speed = seedPacketComponent.speed();
            SeedComponent seedComponent = seedPacketComponent.seedComponent();
            int needStage = seedComponent.stage();
            if (random.nextInt(3) < speed) {
                if (this.stage < needStage) {
                    this.stage++;
                } else {
                    hybrid(level, pos);
                }
            }
        }
    }

    @SuppressWarnings("ConstantValue")
    public void hybrid(ServerLevel level, BlockPos pos) {
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
                        if (be2.getSeedPacketComponent() != null) {

                            return;
                        }
                    }
                }
            }
        }
    }
}
