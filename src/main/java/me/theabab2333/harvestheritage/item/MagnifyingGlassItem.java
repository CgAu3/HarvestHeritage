package me.theabab2333.harvestheritage.item;

import me.theabab2333.harvestheritage.init.ModRecipes;
import me.theabab2333.harvestheritage.recipe.FindRecipe;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class MagnifyingGlassItem extends Item {
    public MagnifyingGlassItem(Properties properties) {
        properties.stacksTo(1);
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide()) {
            Vec3 eyePos = player.getEyePosition();
            Vec3 lookVec = player.getViewVector(1.0f);
            double reachDistance = 5.0;

            Vec3 endPos = eyePos.add(lookVec.x * reachDistance, lookVec.y * reachDistance, lookVec.z * reachDistance);

            AABB searchBox = new AABB(eyePos, endPos).inflate(1.0);

            List<Entity> entities = level.getEntities(player, searchBox, entity -> entity instanceof ItemEntity);

            for (Entity entity : entities) {
                if (entity instanceof ItemEntity itemEntity) {
                    if (level instanceof ServerLevel serverLevel) {
                        RecipeManager manager = serverLevel.recipeAccess();
                        var holders = manager.recipeMap().byType(ModRecipes.FIND_TYPE.get());
                        holders.forEach(
                            holder -> {
                                FindRecipe recipe = holder.value();
                                ItemStack itemStack = itemEntity.getItem().copy();
                                Item item = itemStack.getItem();
                                if (recipe.getIngredient().test(item.getDefaultInstance())) {
                                    List<ItemStackTemplate> list = recipe.getResult();
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
