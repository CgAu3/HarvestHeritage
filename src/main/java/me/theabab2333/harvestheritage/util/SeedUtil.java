package me.theabab2333.harvestheritage.util;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;

public class SeedUtil {
    public static Component getSeedName(Item item) {
        return item.getDefaultInstance().getComponents().getOrDefault(DataComponents.ITEM_NAME, CommonComponents.EMPTY);
    }
}
