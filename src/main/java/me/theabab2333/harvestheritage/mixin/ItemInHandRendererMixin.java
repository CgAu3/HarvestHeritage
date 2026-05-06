package me.theabab2333.harvestheritage.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import me.theabab2333.harvestheritage.hook.ItemDisplayHook;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public class ItemInHandRendererMixin {

    @Inject(method = "renderItem", at = @At(value = "RETURN"), cancellable = true)
    public void renderItem(
        LivingEntity mob,
        ItemStack itemStack,
        ItemDisplayContext type,
        PoseStack poseStack,
        SubmitNodeCollector submitNodeCollector,
        int lightCoords,
        CallbackInfo ci
    ) {
        if (ItemDisplayHook.onRenderHandItem(mob, itemStack, type, poseStack, submitNodeCollector, lightCoords)) ci.cancel();
    }
}
