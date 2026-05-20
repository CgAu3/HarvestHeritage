package me.theabab2333.harvestheritage.datagen.recipe;

import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.component.SeedComponent;
import me.theabab2333.harvestheritage.init.ModDataComponents;
import me.theabab2333.harvestheritage.init.ModItems;
import me.theabab2333.harvestheritage.init.ModSeeds;
import me.theabab2333.harvestheritage.recipe.FindRecipe;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;

import java.util.ArrayList;
import java.util.List;

import static me.theabab2333.harvestheritage.init.ModSeeds.SEEDS;

public class FindRecipeProvider extends ModRecipeProvider {

    protected FindRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    private static void buildCommonSeed(RecipeOutput output) {
        List<ItemStackTemplate> list = new ArrayList<>();
        for (var entry : SEEDS.entrySet()) {
            Item seedItem = entry.getKey();
            ModSeeds.SeedInfo seedInfo = entry.getValue();
            List<Item> resultItems = seedInfo.results();
            int stage = seedInfo.stage();

            Holder<Item> seedHolder = seedItem.builtInRegistryHolder();
            List<Holder<Item>> resultHolders = resultItems.stream().map(item -> (Holder<Item>) item.builtInRegistryHolder()).toList();

            SeedComponent component = SeedComponent.createSeed(seedHolder, resultHolders, stage);
            DataComponentPatch patch = DataComponentPatch.builder().set(ModDataComponents.SEED_COMPONENT.get(), component).build();
            ItemStackTemplate stack = new ItemStackTemplate(ModItems.KNOWN_SEED.get(), 1, patch);
            list.add(stack);
        }
        FindRecipe.Builder.builder(ModItems.UNKNOWN_SEED, list).save(output, HarvestHeritage.of("find/known_seed"));
    }

    @Override
    protected void buildRecipes() {
        buildCommonSeed(output);
    }
}
