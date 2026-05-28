package me.theabab2333.harvestheritage.datagen.recipe;

import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.init.ModItems;
import me.theabab2333.harvestheritage.init.ModSeeds;
import me.theabab2333.harvestheritage.recipe.SeedPacketRecipe;
import me.theabab2333.harvestheritage.util.SeedUtil;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

public class SeedPacketRecipeProvider extends ModRecipeProvider {
    protected SeedPacketRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
        for (var entry : ModSeeds.ALL_SEED.entrySet()) {
            DataComponentPatch patch = SeedUtil.createSeedComponentPatch(entry.getKey(), entry.getValue());
            String path = "seed/seedpacket/" + SeedUtil.getPath(entry.getKey());

            output.accept(
                ResourceKey.create(Registries.RECIPE, HarvestHeritage.of(path)),
                new SeedPacketRecipe(
                    new Recipe.CommonInfo(false),
                    new CraftingRecipe.CraftingBookInfo(CraftingBookCategory.MISC, ""),
                    Ingredient.of(ModItems.KNOWN_SEED),
                    Ingredient.of(Items.PAPER),
                    new ItemStackTemplate(ModItems.SEED_PACKET.asItem(), 1, patch)
                ),
                null
            );
        }
    }
}
