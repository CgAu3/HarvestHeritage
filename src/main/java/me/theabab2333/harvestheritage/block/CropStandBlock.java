package me.theabab2333.harvestheritage.block;

import me.theabab2333.harvestheritage.api.item.ISeedItem;
import me.theabab2333.harvestheritage.block.entity.BaseCropStandBlockEntity;
import me.theabab2333.harvestheritage.block.entity.CropStandStandBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CropStandBlock extends BaseCropStandBlock {
    private static final VoxelShape AABB = Block.box(0, -0.95, 0, 16, 15, 16);

    public CropStandBlock(Properties properties) {
        properties.noCollision().noOcclusion().sound(SoundType.WOOD);
        super(properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new CropStandStandBlockEntity(blockPos, blockState);
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
    protected InteractionResult useItemOn(
        ItemStack itemStack,
        BlockState state,
        Level level,
        BlockPos pos,
        Player player,
        InteractionHand hand,
        BlockHitResult hitResult
    ) {
        if (itemStack.getItem() instanceof ISeedItem seed) {
            BaseCropStandBlockEntity blockEntity = (BaseCropStandBlockEntity) level.getBlockEntity(pos);
            if (blockEntity != null) {
                blockEntity.seedUseOn(itemStack, seed);
                itemStack.shrink(1);
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.FAIL;
        } else {
            return InteractionResult.PASS;
        }
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos belowPos = pos.below();
        BlockState belowState = level.getBlockState(belowPos);
        return belowState.is(BlockTags.SUPPORTS_VEGETATION);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return AABB;
    }
}
