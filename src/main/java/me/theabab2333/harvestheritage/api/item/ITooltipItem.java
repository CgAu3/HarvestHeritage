package me.theabab2333.harvestheritage.api.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public interface ITooltipItem {
    List<Component> getTooltip(ItemStack itemStack);
}
