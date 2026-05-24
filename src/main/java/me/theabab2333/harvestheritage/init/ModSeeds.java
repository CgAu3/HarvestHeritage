package me.theabab2333.harvestheritage.init;

import me.theabab2333.harvestheritage.component.SeedComponent;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModSeeds {
    public record SeedInfo(List<Item> results, int stage) {
    }

    public static Map<Item, SeedInfo> SEEDS = new HashMap<>();

    static {
        SEEDS.put(Items.WHEAT_SEEDS, new SeedInfo(List.of(Items.WHEAT), 3));
        SEEDS.put(Items.BEETROOT_SEEDS, new SeedInfo(List.of(Items.BEETROOT), 3));
        SEEDS.put(Items.MELON_SEEDS, new SeedInfo(List.of(Items.MELON), 3));
        SEEDS.put(Items.PUMPKIN_SEEDS, new SeedInfo(List.of(Items.PUMPKIN), 3));
        SEEDS.put(Items.SWEET_BERRIES, new SeedInfo(List.of(Items.SWEET_BERRIES), 3));
        SEEDS.put(Items.GLOW_BERRIES, new SeedInfo(List.of(Items.GLOW_BERRIES), 3));
        SEEDS.put(Items.CHORUS_FRUIT, new SeedInfo(List.of(Items.CHORUS_FRUIT), 3));
        SEEDS.put(Items.CARROT, new SeedInfo(List.of(Items.CARROT), 3));
        SEEDS.put(Items.POTATO, new SeedInfo(List.of(Items.POTATO), 3));
        SEEDS.put(Items.CHICKEN, new SeedInfo(List.of(Items.CHICKEN, Items.FEATHER), 3));
    }

    public static List<ItemStack> getSeeds() {
        List<ItemStack> list = new ArrayList<>();
        for (var entry : SEEDS.entrySet()) {
            Item seedItem = entry.getKey();
            SeedInfo seedInfo = entry.getValue();
            var resultItems = seedInfo.results();
            int stage = seedInfo.stage();

            Holder<Item> seedHolder = seedItem.builtInRegistryHolder();
            List<Holder<Item>> resultHolders = resultItems.stream().map(item -> (Holder<Item>) item.builtInRegistryHolder()).toList();

            SeedComponent component = SeedComponent.createSeed(seedHolder, resultHolders, stage);
            DataComponentPatch patch = DataComponentPatch.builder().set(ModDataComponents.SEED_COMPONENT.get(), component).build();
            ItemStack stack = new ItemStack(ModItems.SEED_PACKET, 1, patch);
            list.add(stack);
        }
        return list;
    }
}
