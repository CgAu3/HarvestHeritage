package me.theabab2333.harvestheritage.datagen.recipe;

import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.component.SeedPacketComponent;
import me.theabab2333.harvestheritage.init.ModDataComponents;
import me.theabab2333.harvestheritage.init.ModItems;
import me.theabab2333.harvestheritage.recipe.FindRecipe;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindRecipeProvider extends ModRecipeProvider {
    public static Map<Item, List<Item>> COMMON_SEEDS = new HashMap<>();

    static {
        COMMON_SEEDS.put(Items.WHEAT_SEEDS, List.of(Items.WHEAT));
        COMMON_SEEDS.put(Items.BEETROOT_SEEDS, List.of(Items.BEETROOT));
        COMMON_SEEDS.put(Items.MELON_SEEDS, List.of(Items.MELON));
        COMMON_SEEDS.put(Items.PUMPKIN_SEEDS, List.of(Items.PUMPKIN));
    }

    protected FindRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    private static void buildCommonSeed(RecipeOutput output) {
        List<ItemStackTemplate> list = new ArrayList<>();
        for (var entry : COMMON_SEEDS.entrySet()) {
            var seedItem = entry.getKey();
            var resultItems = entry.getValue();

            Holder<Item> seedHolder = seedItem.builtInRegistryHolder();
            List<Holder<Item>> resultHolders = resultItems.stream().map(item -> (Holder<Item>) item.builtInRegistryHolder()).toList();
            System.out.println(resultHolders);

            var component = SeedPacketComponent.createSeedPacket(seedHolder, resultHolders, 1, 2);
            var patch = DataComponentPatch.builder().set(ModDataComponents.SEED_PACKET_COMPONENT.get(), component).build();
            var stack = new ItemStackTemplate(ModItems.KNOWN_SEED.get(), 1, patch);
            list.add(stack);
        }
        FindRecipe.Builder.builder(ModItems.UNKNOWN_SEED, list).save(output, HarvestHeritage.of("find/known_seed"));
    }

    @Override
    protected void buildRecipes() {
        buildCommonSeed(output);
    }
}
