package me.theabab2333.harvestheritage.mixin;

import me.theabab2333.harvestheritage.hook.ItemDisplayHook;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiGraphicsExtractor.class)
public abstract class GuiGraphicsExtractorMixin {
    @Inject(
        method = "item(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;III)V",
        at = @At(value = "RETURN"),
        cancellable = true
    )
    protected void renderGuiItem(LivingEntity owner, Level level, ItemStack itemStack, int x, int y, int seed, CallbackInfo ci) {
        GuiGraphicsExtractor graphicsExtractor = (GuiGraphicsExtractor) (Object) this;
        if (ItemDisplayHook.onRenderGuiItem(graphicsExtractor, owner, level, itemStack, x, y, seed)) {
            ci.cancel();
        }
    }
}
