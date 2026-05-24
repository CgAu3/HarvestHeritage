package me.theabab2333.harvestheritage.util;

import me.theabab2333.harvestheritage.component.SeedComponent;
import me.theabab2333.harvestheritage.init.ModSeeds;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.stream.Collectors;

public class SeedUtil {
    public static Component getSeedName(Item item) {
        return item.getDefaultInstance().getComponents().getOrDefault(DataComponents.ITEM_NAME, CommonComponents.EMPTY);
    }

    public static String getPath(Item item) {
        return BuiltInRegistries.ITEM.getKey(item).getPath();
    }

    public static ModSeeds.SeedInfo getSeedInfo(Item item) {
        return ModSeeds.ALL_SEED.get(item);
    }

    public static SeedComponent getSeedComponent(Item item) {
        ModSeeds.SeedInfo seedInfo = getSeedInfo(item);
        if (seedInfo != null) {
            List<Holder<Item>> holders = seedInfo.results().stream().map(Item::builtInRegistryHolder).collect(Collectors.toList());
            return new SeedComponent(getHolder(item), holders, seedInfo.stage());
        }
        throw new IllegalArgumentException("No seed info found for item: " + item);
    }

    public static Holder<Item> getHolder(Item item) {
        return item.builtInRegistryHolder();
    }
}
