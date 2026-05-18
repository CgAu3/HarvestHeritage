package me.theabab2333.harvestheritage.api.item;

import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public interface ISeedItem {
    Holder<Item> seed(ItemStack itemStack);

    List<Holder<Item>> result(ItemStack itemStack);

    int speed(ItemStack itemStack);

    int output(ItemStack itemStack);
}