package me.theabab2333.harvestheritage.api.render;

import net.minecraft.world.item.ItemStack;

public interface IItemDisplayInHand {
    ItemStack getDisplayedItem(ItemStack stack);
    int offsetX(ItemStack stack);
    int offsetY(ItemStack stack);
    int scale(ItemStack stack);
}