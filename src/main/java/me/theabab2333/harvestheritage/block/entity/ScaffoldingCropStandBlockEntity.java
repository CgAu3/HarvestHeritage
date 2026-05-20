package me.theabab2333.harvestheritage.block.entity;

import me.theabab2333.harvestheritage.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class ScaffoldingCropStandBlockEntity extends BaseCropStandBlockEntity {
    public ScaffoldingCropStandBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(ModBlockEntities.SCAFFOLDING_CROP_STAND_BLOCK_ENTITY.get(), worldPosition, blockState);
    }
}
