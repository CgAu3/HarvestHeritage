package me.theabab2333.harvestheritage.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ArableLandBlock extends BaseArableLandBlock {
    public ArableLandBlock(Properties properties) {
        properties
            .noCollision()
            .noOcclusion();
        super(properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return null;
    }
}
