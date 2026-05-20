package me.theabab2333.harvestheritage.block;

import me.theabab2333.harvestheritage.block.entity.ScaffoldingCropStandBlockEntity;
import me.theabab2333.harvestheritage.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

// 大部分代码来自原版脚手架
public class ScaffoldingCropStandBlock extends BaseCropStandBlock {
    public static final BooleanProperty BOTTOM = BlockStateProperties.BOTTOM;
    public static final IntegerProperty DISTANCE = BlockStateProperties.STABILITY_DISTANCE;

    public static final int STABILITY_MAX_DISTANCE = 7;

    private static final VoxelShape SHAPE_STABLE = Shapes.or(
        Block.column(16.0F, 14.0F, 16.0F),
        Shapes.rotateHorizontal(Block.box(0.0F, 0.0F, 0.0F, 2.0F, 16.0F, 2.0F)).values().stream().reduce(Shapes.empty(), Shapes::or)
    );
    private static final VoxelShape SHAPE_BELOW_BLOCK = Shapes.block().move(0.0, -1.0, 0.0).optimize();
    private static final VoxelShape SHAPE_UNSTABLE_BOTTOM = Block.column(16.0F, 0.0F, 2.0F);
    private static final VoxelShape SHAPE_UNSTABLE = Shapes.or(
        SHAPE_STABLE,
        SHAPE_UNSTABLE_BOTTOM,
        Shapes.rotateHorizontal(Block.boxZ(16.0F, 0.0F, 2.0F, 0.0F, 2.0F)).values().stream().reduce(Shapes.empty(), Shapes::or)
    );

    public ScaffoldingCropStandBlock(Properties properties) {
        properties.noCollision().noOcclusion().sound(SoundType.SCAFFOLDING);
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(BOTTOM, Boolean.FALSE).setValue(DISTANCE, STABILITY_MAX_DISTANCE));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();
        int distance = getDistance(level, pos);
        return this.defaultBlockState().setValue(DISTANCE, distance).setValue(BOTTOM, this.isBottom(level, pos, distance));
    }

    @Override
    protected VoxelShape getInteractionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return Shapes.block();
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int distance = getDistance(level, pos);
        BlockState newState = state.setValue(DISTANCE, distance).setValue(BOTTOM, this.isBottom(level, pos, distance));
        if (newState.getValue(DISTANCE) == 7) {
            if (state.getValue(DISTANCE) == 7) {
                FallingBlockEntity.fall(level, pos, newState);
            } else {
                level.destroyBlock(pos, true);
            }
        } else if (state != newState) {
            level.setBlock(pos, newState, 3);
        }
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        if (!level.isClientSide()) {
            level.scheduleTick(pos, this, 1);
        }
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
        if (!level.isClientSide()) {
            ticks.scheduleTick(pos, this, 1);
        }
        return state;
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (context.isPlacement()) {
            return Shapes.empty();
        } else if (context.isAbove(Shapes.block(), pos, true) && !context.isDescending()) {
            return SHAPE_STABLE;
        } else {
            return state.getValue(DISTANCE) != 0 && state.getValue(BOTTOM) && context.isAbove(SHAPE_BELOW_BLOCK, pos, true)
                   ? SHAPE_UNSTABLE_BOTTOM
                   : Shapes.empty();
        }
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(DISTANCE, BOTTOM);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (!context.isHoldingItem(state.getBlock().asItem())) {
            return state.getValue(BOTTOM) ? SHAPE_UNSTABLE : SHAPE_STABLE;
        } else {
            return Shapes.block();
        }
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ScaffoldingCropStandBlockEntity(blockPos, blockState);
    }

    private boolean isBottom(BlockGetter level, BlockPos pos, int distance) {
        return distance > 0 && !level.getBlockState(pos.below()).is(this);
    }

    public static int getDistance(BlockGetter level, BlockPos pos) {
        BlockPos.MutableBlockPos relativePos = pos.mutable().move(Direction.DOWN);
        BlockState belowState = level.getBlockState(relativePos);
        int distance = 7;
        if (belowState.is(ModBlocks.SCAFFOLDING_CROP_STAND_BLOCK) || belowState.is(Blocks.SCAFFOLDING)) {
            distance = belowState.getValue(DISTANCE);
        } else if (belowState.isFaceSturdy(level, relativePos, Direction.UP)) {
            return 0;
        }

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockState relativeState = level.getBlockState(relativePos.setWithOffset(pos, direction));
            if (relativeState.is(ModBlocks.SCAFFOLDING_CROP_STAND_BLOCK.get()) || relativeState.is(Blocks.SCAFFOLDING)) {
                distance = Math.min(distance, relativeState.getValue(DISTANCE) + 1);
                if (distance == 1) {
                    break;
                }
            }
        }

        return distance;
    }
}
