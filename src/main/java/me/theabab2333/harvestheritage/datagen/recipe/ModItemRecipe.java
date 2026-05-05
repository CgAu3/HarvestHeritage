package me.theabab2333.harvestheritage.datagen.recipe;

import me.theabab2333.harvestheritage.init.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;

public class ModItemRecipe extends ModRecipeProvider {
    protected ModItemRecipe(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
        ShapedRecipeBuilder.shaped(registries.lookupOrThrow(Registries.ITEM), RecipeCategory.TOOLS, ModItems.GRASS_SHEAR)
            .pattern("   ")
            .pattern(" BA")
            .pattern(" A ")
            .define('A', Items.STICK)
            .define('B', Items.SHEARS)
            .unlockedBy("has_shears", has(Items.SHEARS))
            .save(output);
        ShapedRecipeBuilder.shaped(registries.lookupOrThrow(Registries.ITEM), RecipeCategory.TOOLS, ModItems.MAGNIFYING_GLASS)
            .pattern(" A ")
            .pattern("ABA")
            .pattern(" AC")
            .define('A', Items.GLASS_PANE)
            .define('B', Items.AMETHYST_SHARD)
            .define('C', Items.STICK)
            .unlockedBy("has_amethyst_shard", has(Items.AMETHYST_SHARD))
            .save(output);


    }
}
