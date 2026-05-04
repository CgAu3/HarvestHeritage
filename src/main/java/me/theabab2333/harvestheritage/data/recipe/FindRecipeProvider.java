package me.theabab2333.harvestheritage.data.recipe;

import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.component.SeedComponent;
import me.theabab2333.harvestheritage.init.ModDataComponents;
import me.theabab2333.harvestheritage.init.ModItems;
import me.theabab2333.harvestheritage.recipe.FindRecipe;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

public class FindRecipeProvider extends ModRecipeProvider {
    public static List<Item> COMMON_SEEDS = List.of(
        Items.WHEAT_SEEDS,
        Items.BEETROOT_SEEDS,
        Items.MELON_SEEDS,
        Items.PUMPKIN_SEEDS
    );

    protected FindRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    private static void buildCommonSeed(RecipeOutput output) {
        List<ItemStackTemplate> list = new ArrayList<>();
        for (var item : COMMON_SEEDS) {
            var component = SeedComponent.setSeed(item.builtInRegistryHolder());
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
