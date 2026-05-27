package me.theabab2333.harvestheritage.init;

import me.theabab2333.harvestheritage.component.SeedComponent;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ModSeeds {
    public record SeedInfo(List<Item> results, int stage) {
    }

    public static Map<Item, SeedInfo> ALL_SEED = new LinkedHashMap<>();
    public static Map<Item, SeedInfo> CROP_SEED = new LinkedHashMap<>();
    public static Map<Item, SeedInfo> ANIMAL_SEED = new LinkedHashMap<>();
    public static Map<Item, SeedInfo> MOB_SEED = new LinkedHashMap<>();

    static {
        // common
        CROP_SEED.put(Items.WHEAT_SEEDS, new SeedInfo(List.of(Items.WHEAT), 3));
        CROP_SEED.put(Items.BEETROOT_SEEDS, new SeedInfo(List.of(Items.BEETROOT), 3));
        CROP_SEED.put(Items.MELON_SEEDS, new SeedInfo(List.of(Items.MELON), 3));
        CROP_SEED.put(Items.PUMPKIN_SEEDS, new SeedInfo(List.of(Items.PUMPKIN), 3));
        CROP_SEED.put(Items.SWEET_BERRIES, new SeedInfo(List.of(Items.SWEET_BERRIES), 3));
        CROP_SEED.put(Items.GLOW_BERRIES, new SeedInfo(List.of(Items.GLOW_BERRIES), 3));
        CROP_SEED.put(Items.CHORUS_FRUIT, new SeedInfo(List.of(Items.CHORUS_FRUIT), 3));
        CROP_SEED.put(Items.CARROT, new SeedInfo(List.of(Items.CARROT), 3));
        CROP_SEED.put(Items.POTATO, new SeedInfo(List.of(Items.POTATO), 3));
        CROP_SEED.put(Items.SUGAR_CANE, new SeedInfo(List.of(Items.SUGAR_CANE), 3));
        CROP_SEED.put(Items.BAMBOO, new SeedInfo(List.of(Items.BAMBOO), 3));
        CROP_SEED.put(Items.SEA_PICKLE, new SeedInfo(List.of(Items.SEA_PICKLE), 3));
        CROP_SEED.put(Items.KELP, new SeedInfo(List.of(Items.KELP), 3));

        // animal
        ANIMAL_SEED.put(Items.CHICKEN, new SeedInfo(List.of(Items.CHICKEN, Items.FEATHER), 4));
        ANIMAL_SEED.put(Items.BEEF, new SeedInfo(List.of(Items.BEEF, Items.LEATHER), 4));
        ANIMAL_SEED.put(Items.PORKCHOP, new SeedInfo(List.of(Items.PORKCHOP), 4));
        ANIMAL_SEED.put(Items.MUTTON, new SeedInfo(List.of(Items.MUTTON), 4));
        ANIMAL_SEED.put(Items.RABBIT, new SeedInfo(List.of(Items.RABBIT, Items.RABBIT_FOOT), 4));
        ANIMAL_SEED.put(Items.SALMON, new SeedInfo(List.of(Items.SALMON), 4));
        ANIMAL_SEED.put(Items.COD, new SeedInfo(List.of(Items.COD), 4));
        ANIMAL_SEED.put(Items.TROPICAL_FISH, new SeedInfo(List.of(Items.TROPICAL_FISH), 4));
        ANIMAL_SEED.put(Items.PUFFERFISH, new SeedInfo(List.of(Items.PUFFERFISH), 4));
        ANIMAL_SEED.put(Items.INK_SAC, new SeedInfo(List.of(Items.INK_SAC, Items.GLOW_INK_SAC), 4));
        ANIMAL_SEED.put(Items.ARMADILLO_SCUTE, new SeedInfo(List.of(Items.ARMADILLO_SCUTE), 4));
        ANIMAL_SEED.put(Items.EGG, new SeedInfo(List.of(Items.EGG, Items.BROWN_EGG, Items.BLUE_EGG), 4));
        ANIMAL_SEED.put(Items.TURTLE_SCUTE, new SeedInfo(List.of(Items.TURTLE_SCUTE), 4));
        ANIMAL_SEED.put(Items.HONEYCOMB, new SeedInfo(List.of(Items.HONEYCOMB, Items.HONEY_BOTTLE), 4));

        // mob
        MOB_SEED.put(Items.BLAZE_ROD, new SeedInfo(List.of(Items.BLAZE_ROD), 4));
        MOB_SEED.put(Items.BREEZE_ROD, new SeedInfo(List.of(Items.BREEZE_ROD), 4));
        MOB_SEED.put(Items.ENDER_PEARL, new SeedInfo(List.of(Items.ENDER_PEARL), 4));
        MOB_SEED.put(Items.ECHO_SHARD, new SeedInfo(List.of(Items.ECHO_SHARD), 4));
        MOB_SEED.put(Items.SLIME_BALL, new SeedInfo(List.of(Items.SLIME_BALL), 4));
        MOB_SEED.put(Items.GUNPOWDER, new SeedInfo(List.of(Items.GUNPOWDER), 4));
        MOB_SEED.put(Items.PRISMARINE_CRYSTALS, new SeedInfo(List.of(Items.PRISMARINE_CRYSTALS, Items.PRISMARINE_SHARD), 4));
        MOB_SEED.put(Items.SPIDER_EYE, new SeedInfo(List.of(Items.SPIDER_EYE), 4));
        MOB_SEED.put(Items.MAGMA_CREAM, new SeedInfo(List.of(Items.MAGMA_CREAM), 4));
        MOB_SEED.put(Items.GHAST_TEAR, new SeedInfo(List.of(Items.GHAST_TEAR), 4));
        MOB_SEED.put(Items.PHANTOM_MEMBRANE, new SeedInfo(List.of(Items.PHANTOM_MEMBRANE), 4));
        MOB_SEED.put(Items.ROTTEN_FLESH, new SeedInfo(List.of(Items.ROTTEN_FLESH), 4));
        MOB_SEED.put(Items.BONE, new SeedInfo(List.of(Items.BONE), 4));
        MOB_SEED.put(Items.STRING, new SeedInfo(List.of(Items.STRING), 4));

        // all
        ALL_SEED.putAll(CROP_SEED);
        ALL_SEED.putAll(ANIMAL_SEED);
        ALL_SEED.putAll(MOB_SEED);
    }

    public static List<ItemStack> getSeedPackets() {
        List<ItemStack> list = new ArrayList<>();
        for (var entry : ALL_SEED.entrySet()) {
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
