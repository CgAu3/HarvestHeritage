package me.theabab2333.harvestheritage.client.render;

import net.minecraft.world.item.ItemStack;

public interface IItemDisplayInHand {
    ItemStack getDisplayedItem(ItemStack stack);
    int offsetX(ItemStack stack);
    int offsetY(ItemStack stack);
}
