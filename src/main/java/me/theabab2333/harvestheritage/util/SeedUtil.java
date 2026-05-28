package me.theabab2333.harvestheritage.util;

import me.theabab2333.harvestheritage.component.SeedComponent;
import me.theabab2333.harvestheritage.component.SeedPacketComponent;
import me.theabab2333.harvestheritage.init.ModConfigs;
import me.theabab2333.harvestheritage.init.ModDataComponents;
import me.theabab2333.harvestheritage.init.ModSeeds;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
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

    public static SeedComponent createSeedComponent(Item seedItem, ModSeeds.SeedInfo seedInfo) {
        List<Holder<Item>> resultHolders = seedInfo.results().stream().map(item -> (Holder<Item>) item.builtInRegistryHolder()).toList();
        return SeedComponent.createSeed(getHolder(seedItem), resultHolders, seedInfo.stage());
    }

    public static DataComponentPatch createSeedComponentPatch(SeedComponent component) {
        return DataComponentPatch.builder().set(ModDataComponents.SEED_COMPONENT.get(), component).build();
    }

    public static DataComponentPatch createSeedComponentPatch(Item seedItem, ModSeeds.SeedInfo seedInfo) {
        return createSeedComponentPatch(createSeedComponent(seedItem, seedInfo));
    }

    public static int rollStat(RandomSource random, int val1, int val2, int min, int max) {
        int minVal = Math.min(val1, val2);
        int avgVal = (val1 + val2) / 2;
        double roll = random.nextDouble();
        if (roll < 0.05) {
            return Math.max(min, minVal - random.nextInt(2) - 1);
        } else if (roll < 0.8) {
            return Math.min(max, avgVal + random.nextInt(2) + 1);
        } else {
            return avgVal;
        }
    }

    public static SeedComponent rollSeed(RandomSource random, SeedComponent seed1, SeedComponent seed2, List<SeedComponent> hybrids) {
        if (random.nextDouble() < 0.5) {
            return random.nextBoolean() ? seed1 : seed2;
        }
        if (!hybrids.isEmpty()) {
            return hybrids.get(random.nextInt(hybrids.size()));
        }
        return random.nextBoolean() ? seed1 : seed2;
    }

    public static SeedPacketComponent mergeSeedPackets(
        RandomSource random,
        SeedPacketComponent comp1,
        SeedPacketComponent comp2,
        List<SeedComponent> hybrids
    ) {
        int maxSpeed = ModConfigs.SEED_SPEED_MAX.getAsInt();
        int outputMax = ModConfigs.OUTPUT_MAX.getAsInt();
        int speed = rollStat(random, comp1.speed(), comp2.speed(), 1, maxSpeed);
        int output = rollStat(random, comp1.output(), comp2.output(), 1, outputMax);
        SeedComponent seed = rollSeed(random, comp1.seedComponent(), comp2.seedComponent(), hybrids);
        return new SeedPacketComponent(seed, speed, output);
    }
}
