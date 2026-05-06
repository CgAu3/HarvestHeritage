package me.theabab2333.harvestheritage.hook;

import com.mojang.blaze3d.vertex.PoseStack;
import me.theabab2333.harvestheritage.api.render.IItemDisplayInHand;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.joml.Matrix3x2fStack;

public class ItemDisplayHook {
    private static final ThreadLocal<ItemStack> PREVENT_RECURSION = new ThreadLocal<>();

    public static boolean onRenderGuiItem(
        GuiGraphicsExtractor guiGraphics,
        LivingEntity livingEntity,
        Level level,
        ItemStack stack,
        int x,
        int y,
        int seed
    ) {
        if (stack.getItem() instanceof IItemDisplayInHand display) {
            if (PREVENT_RECURSION.get() == stack) return false;
            if (level != null) {
                var output = display.getDisplayedItem(stack);
                if (!output.isEmpty() && output != stack) {
                    renderInstead(
                        guiGraphics,
                        livingEntity,
                        level,
                        output,
                        x,
                        y,
                        seed
                    );
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean onRenderHandItem(
        LivingEntity entity,
        ItemStack stack,
        ItemDisplayContext type,
        PoseStack poseStack,
        SubmitNodeCollector collector,
        int lightCoords
    ) {
        if (stack.getItem() instanceof IItemDisplayInHand display) {
            // idk
        }
        return false;
    }

    private static void renderInstead(
        GuiGraphicsExtractor guiGraphics,
        LivingEntity livingEntity,
        Level level,
        ItemStack stack,
        int x,
        int y,
        int seed
    ) {
        PREVENT_RECURSION.set(stack);
        try {
            Matrix3x2fStack pose = guiGraphics.pose();
            pose.pushMatrix();
            guiGraphics.item(livingEntity, level, stack, x, y, seed);
            pose.popMatrix();
        } finally {
            PREVENT_RECURSION.remove();
        }
    }
}
