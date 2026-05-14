package me.theabab2333.harvestheritage.datagen.recipe;

import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.component.SeedComponent;
import me.theabab2333.harvestheritage.init.ModSeeds;
import me.theabab2333.harvestheritage.recipe.HybridRecipe;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.List;
import java.util.stream.Collectors;

public class HybridRecipeProvider extends ModRecipeProvider {
    protected HybridRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
        HybridRecipe.Builder.builder(
            List.of(Items.WHEAT_SEEDS.builtInRegistryHolder(), Items.MELON_SEEDS.builtInRegistryHolder()),
            List.of(fromItem(Items.PUMPKIN_SEEDS), fromItem(Items.BEETROOT_SEEDS))
        ).save(output, HarvestHeritage.of("hyprid/test"));
    }

    private SeedComponent fromItem(Item item) {
        ModSeeds.SeedInfo seedInfo = ModSeeds.SEEDS.get(item);
        if (seedInfo != null) {
            List<Holder<Item>> holders = seedInfo.results().stream().map(Item::builtInRegistryHolder).collect(Collectors.toList());
            return new SeedComponent(item.builtInRegistryHolder(), holders, seedInfo.stage());
        }
        throw new IllegalArgumentException("No seed info found for item: " + item);
    }
}
