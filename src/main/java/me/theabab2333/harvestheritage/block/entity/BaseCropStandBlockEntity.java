package me.theabab2333.harvestheritage.block.entity;

import lombok.Getter;
import me.theabab2333.harvestheritage.component.SeedComponent;
import me.theabab2333.harvestheritage.component.SeedPacketComponent;
import me.theabab2333.harvestheritage.init.ModDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public abstract class BaseCropStandBlockEntity extends BlockEntity {
    public BaseCropStandBlockEntity(BlockEntityType<?> type, BlockPos worldPosition, BlockState blockState) {
        super(type, worldPosition, blockState);
    }

    @Getter
    protected SeedPacketComponent seedPacketComponent;
    @Getter
    protected int stage = 0;

    public void seedUseOn(ItemStack itemStack) {
        if (seedPacketComponent == null) {
            itemStack.shrink(1);
            if (itemStack.get(ModDataComponents.SEED_COMPONENT) instanceof SeedComponent component) {
                this.seedPacketComponent = SeedPacketComponent.createSeedPacket(component, 1, 1);
            } else if (itemStack.get(ModDataComponents.SEED_PACKET_COMPONENT) instanceof SeedPacketComponent component) {
                this.seedPacketComponent = component;
            }
        }
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        this.seedPacketComponent = input.read("component", SeedPacketComponent.CODEC).orElse(null);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        if (seedPacketComponent != null) {
            output.store("component", SeedPacketComponent.CODEC, this.seedPacketComponent);
        }
    }
}
