package me.theabab2333.harvestheritage.block;

import me.theabab2333.harvestheritage.api.item.ISeedItem;
import me.theabab2333.harvestheritage.block.entity.BaseCropStandBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public abstract class BaseCropStandBlock extends Block implements EntityBlock {
    public BaseCropStandBlock(Properties properties) {
        properties.randomTicks();
        super(properties);
    }

    @Override
    protected BlockState updateShape(
        BlockState state,
        LevelReader level,
        ScheduledTickAccess ticks,
        BlockPos pos,
        Direction directionToNeighbour,
        BlockPos neighbourPos,
        BlockState neighbourState,
        RandomSource random
    ) {
        return !state.canSurvive(level, pos)
               ? Blocks.AIR.defaultBlockState()
               : super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        BaseCropStandBlockEntity blockEntity = (BaseCropStandBlockEntity) level.getBlockEntity(pos);
        if (blockEntity != null) {
            blockEntity.tick(level, pos, state, random);
        }
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos belowPos = pos.below();
        BlockState belowState = level.getBlockState(belowPos);
        return belowState.is(BlockTags.SUPPORTS_VEGETATION);
    }

    @Override
    protected InteractionResult useItemOn(
        ItemStack itemStack,
        BlockState state,
        Level level,
        BlockPos pos,
        Player player,
        InteractionHand hand,
        BlockHitResult hitResult
    ) {
        BaseCropStandBlockEntity blockEntity = (BaseCropStandBlockEntity) level.getBlockEntity(pos);
        if (blockEntity == null) return InteractionResult.FAIL;
        if (itemStack.getItem() instanceof ISeedItem) {
            blockEntity.seedUseOn(itemStack);
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.PASS;
        }
    }
}
