package me.theabab2333.harvestheritage.client.event;

import me.theabab2333.harvestheritage.HarvestHeritage;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderHandEvent;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;

@EventBusSubscriber(modid = HarvestHeritage.MODID, value = Dist.CLIENT)
public class RenderSeedPacketEvent {
    @SubscribeEvent
    public static void renderInHand(RenderHandEvent event) {
//        var stack = event.getItemStack();
//        if (stack.getItem() instanceof IItemDisplayInHand display) {
//            float scale = display.scale(stack);
//            int offsetX = display.offsetX(stack);
//            int offsetY = display.offsetY(stack);
//
//            var poseStack = event.getPoseStack();
//            poseStack.pushPose();
//            poseStack.translate(0.024, 0.015 + 0.10 + offsetY * 0.03, 0.0225 - 0.1425 + offsetX * 0.03);
//            poseStack.scale(scale, scale, scale);
//
//            poseStack.popPose();
//        }
    }

    @SubscribeEvent
    public static void RenderToolTipItem(RenderTooltipEvent.Pre event) {
        // NONE
    }
}
