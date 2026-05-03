package me.theabab2333.harvestheritage.item;

import me.theabab2333.harvestheritage.init.ModItems;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class MagnifyingGlassItem extends Item {
    public MagnifyingGlassItem(Properties properties) {
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
                    ItemStack itemStack = itemEntity.getItem().copy();
                    if (itemStack.is(ModItems.UNKNOWN_SEED)) {
                        itemStack.shrink(1);
                        itemEntity.setItem(itemStack);
                        Containers.dropItemStack(
                            level,
                            itemEntity.getX(),
                            itemEntity.getY(),
                            itemEntity.getZ(),
                            ModItems.KNOWN_SEED.toStack()
                        );
                    }
                }
            }
        }
        return InteractionResult.SUCCESS;
    }
}
