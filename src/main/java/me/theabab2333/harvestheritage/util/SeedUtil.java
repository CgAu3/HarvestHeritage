package me.theabab2333.harvestheritage.util;

import me.theabab2333.harvestheritage.component.SeedComponent;
import me.theabab2333.harvestheritage.init.ModDataComponents;
import me.theabab2333.harvestheritage.init.ModSeeds;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;

import java.util.List;

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
            return createSeedComponent(item, seedInfo);
        }
        throw new IllegalArgumentException("No seed info found for item: " + item);
    }

    public static Holder<Item> getHolder(Item item) {
        return item.builtInRegistryHolder();
    }

    /**
     * 从种子物品和种子信息创建 SeedComponent
     */
    public static SeedComponent createSeedComponent(Item seedItem, ModSeeds.SeedInfo seedInfo) {
        List<Holder<Item>> resultHolders = seedInfo.results().stream()
            .map(item -> (Holder<Item>) item.builtInRegistryHolder())
            .toList();
        return SeedComponent.createSeed(getHolder(seedItem), resultHolders, seedInfo.stage());
    }

    /**
     * 从 SeedComponent 创建包含 SEED_COMPONENT 的 DataComponentPatch
     */
    public static DataComponentPatch createSeedComponentPatch(SeedComponent component) {
        return DataComponentPatch.builder()
            .set(ModDataComponents.SEED_COMPONENT.get(), component)
            .build();
    }

    /**
     * 从种子物品和种子信息直接创建包含 SEED_COMPONENT 的 DataComponentPatch
     */
    public static DataComponentPatch createSeedComponentPatch(Item seedItem, ModSeeds.SeedInfo seedInfo) {
        return createSeedComponentPatch(createSeedComponent(seedItem, seedInfo));
    }
}
