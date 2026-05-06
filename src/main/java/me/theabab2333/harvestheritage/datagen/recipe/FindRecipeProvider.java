package me.theabab2333.harvestheritage.datagen.recipe;

import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.component.SeedComponent;
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
    public record SeedInfo(List<Item> results, int stage) {
    }

    public static Map<Item, SeedInfo> COMMON_SEEDS = new HashMap<>();

    static {
        COMMON_SEEDS.put(Items.WHEAT_SEEDS, new SeedInfo(List.of(Items.WHEAT), 3));
        COMMON_SEEDS.put(Items.BEETROOT_SEEDS, new SeedInfo(List.of(Items.BEETROOT), 3));
        COMMON_SEEDS.put(Items.MELON_SEEDS, new SeedInfo(List.of(Items.MELON), 3));
        COMMON_SEEDS.put(Items.PUMPKIN_SEEDS, new SeedInfo(List.of(Items.PUMPKIN), 3));
    }

    protected FindRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    private static void buildCommonSeed(RecipeOutput output) {
        List<ItemStackTemplate> list = new ArrayList<>();
        for (var entry : COMMON_SEEDS.entrySet()) {
            var seedItem = entry.getKey();
            var seedInfo = entry.getValue();
            var resultItems = seedInfo.results();
            var stage = seedInfo.stage();

            Holder<Item> seedHolder = seedItem.builtInRegistryHolder();
            List<Holder<Item>> resultHolders = resultItems.stream().map(item -> (Holder<Item>) item.builtInRegistryHolder()).toList();

            var component = SeedComponent.createSeed(seedHolder, resultHolders, stage);
            var patch = DataComponentPatch.builder().set(ModDataComponents.SEED_COMPONENT.get(), component).build();
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
