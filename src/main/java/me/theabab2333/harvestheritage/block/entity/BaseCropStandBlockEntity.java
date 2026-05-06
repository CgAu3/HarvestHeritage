package me.theabab2333.harvestheritage.block.entity;

import me.theabab2333.harvestheritage.api.item.ISeedItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public abstract class BaseCropStandBlockEntity extends BlockEntity {
    public BaseCropStandBlockEntity(
        BlockEntityType<?> type,
        BlockPos worldPosition,
        BlockState blockState
    ) {
        super(type, worldPosition, blockState);
    }

    public void seedUseOn(
        ItemStack itemStack,
        ISeedItem seed
    ) {

    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
    }
}
