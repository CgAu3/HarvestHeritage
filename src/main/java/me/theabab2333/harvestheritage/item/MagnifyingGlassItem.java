package me.theabab2333.harvestheritage.item;

import me.theabab2333.harvestheritage.init.ModRecipes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class MagnifyingGlassItem extends Item {
    public MagnifyingGlassItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide()) {
            var eyePos = player.getEyePosition();
            var lookVec = player.getViewVector(1.0f);
            double reachDistance = 5.0;

            var endPos = eyePos.add(lookVec.x * reachDistance, lookVec.y * reachDistance, lookVec.z * reachDistance);

            var searchBox = new AABB(eyePos, endPos).inflate(1.0);

            var entities = level.getEntities(player, searchBox, entity -> entity instanceof ItemEntity);

            for (var entity : entities) {
                if (entity instanceof ItemEntity itemEntity) {
                    if (level instanceof ServerLevel serverLevel) {
                        var manager = serverLevel.recipeAccess();
                        var holders = manager.recipeMap().byType(ModRecipes.FIND_TYPE.get());
                        holders.forEach(
                            holder -> {
                                var recipe = holder.value();
                                var itemStack = itemEntity.getItem().copy();
                                var item = itemStack.getItem();
                                if (recipe.getIngredient().test(item.getDefaultInstance())) {
                                    var list = recipe.getResult();
                                    if (!list.isEmpty()) {
                                        var random = serverLevel.getRandom();
                                        var nextInt = random.nextInt(list.size());
                                        itemStack.shrink(1);
                                        itemEntity.setItem(itemStack);
                                        var result = list.get(nextInt).create();
                                        Containers.dropItemStack(
                                            level,
                                            itemEntity.getX(),
                                            itemEntity.getY(),
                                            itemEntity.getZ(),
                                            result
                                        );
                                    }
                                }
                            }
                        );
                    }
                }
            }
        }
        return InteractionResult.SUCCESS;
    }
}
