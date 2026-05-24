package me.theabab2333.harvestheritage.api.item;

import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public interface ISeedItem {
    Holder<Item> seed(ItemStack itemStack);
}