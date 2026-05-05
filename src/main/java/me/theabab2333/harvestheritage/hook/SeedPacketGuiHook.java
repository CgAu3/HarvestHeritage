package me.theabab2333.harvestheritage.hook;

import me.theabab2333.harvestheritage.client.render.IItemDisplayInHand;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SeedPacketGuiHook {
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
        if (stack.getItem() instanceof IItemDisplayInHand hand) {
            if (PREVENT_RECURSION.get() == stack) return false;
            if (level != null) {
                var output = hand.getDisplayedItem(stack);
                if (!output.isEmpty() && output != stack) {
                    renderInstead(guiGraphics, livingEntity, level, output, x, y, seed);
                    return true;
                }
            }
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
            guiGraphics.item(livingEntity, level, stack, x, y, seed);
        } finally {
            PREVENT_RECURSION.remove();
        }
    }
}
