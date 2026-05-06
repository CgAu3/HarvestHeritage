package me.theabab2333.harvestheritage.event;

import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.api.item.ITooltipItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddAttributeTooltipsEvent;

@EventBusSubscriber(modid = HarvestHeritage.MODID)
public class ModTooltipEvent {
    @SubscribeEvent
    public static void register(AddAttributeTooltipsEvent event) {
        ItemStack itemStack = event.getStack();
        if (itemStack.getItem() instanceof ITooltipItem tooltipItem) {
            tooltipItem.getTooltip(itemStack).forEach(event::addTooltipLines);
        }
    }
}
