package me.theabab2333.harvestheritage.datagen.recipe;

import me.theabab2333.harvestheritage.init.ModBlocks;
import me.theabab2333.harvestheritage.init.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;

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
        ShapedRecipeBuilder.shaped(registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, ModBlocks.CROP_STAND_BLOCK.asItem())
            .pattern("   ")
            .pattern("A A")
            .pattern("A A")
            .define('A', Items.STICK)
            .unlockedBy("has_stick", has(Items.STICK))
            .save(output);
        SimpleCookingRecipeBuilder.smelting(
            Ingredient.of(ModItems.SEED_PACKET.asItem()),
            RecipeCategory.FOOD,
            CookingBookCategory.FOOD,
            ModItems.FRIED_SEEDSACK.asItem(),
            0.1f,
            100
        ).unlockedBy("has_seed_packet", has(ModItems.FRIED_SEEDSACK.asItem())).save(output);
        SimpleCookingRecipeBuilder.campfireCooking(
            Ingredient.of(ModItems.SEED_PACKET.asItem()),
            RecipeCategory.FOOD,
            ModItems.FRIED_SEEDSACK.asItem(),
            0.1f,
            600
        ).unlockedBy("has_seed_packet", has(ModItems.FRIED_SEEDSACK.asItem())).save(output, "fried_seedsack_from_campfire_cooking");
        ShapelessRecipeBuilder.shapeless(registries.lookupOrThrow(Registries.ITEM), RecipeCategory.FOOD, ModItems.KFC)
            .requires(Items.PAPER)
            .requires(Items.COOKED_CHICKEN)
            .unlockedBy("has_cooked_chicken", has(Items.COOKED_CHICKEN))
            .save(output);
        ShapedRecipeBuilder.shaped(registries.lookupOrThrow(Registries.ITEM), RecipeCategory.REDSTONE, ModBlocks.ACTIVAOR_BLOCK.asItem())
            .pattern("ABA")
            .pattern("CDE")
            .pattern("AFA")
            .define('A', Items.CRYING_OBSIDIAN)
            .define('B', Items.NETHER_STAR)
            .define('C', Items.END_CRYSTAL)
            .define('D', ModItems.ZZZZ)
            .define('E', Items.WAXED_OXIDIZED_COPPER_BULB)
            .define('F', Items.ECHO_SHARD)
            .unlockedBy("has_zzzz", has(ModItems.ZZZZ))
            .save(output);
    }
}
