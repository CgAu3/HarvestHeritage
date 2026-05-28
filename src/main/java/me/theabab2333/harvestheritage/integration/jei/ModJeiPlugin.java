package me.theabab2333.harvestheritage.integration.jei;

import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.event.ModRecipeSyncEvent;
import me.theabab2333.harvestheritage.init.ModBlocks;
import me.theabab2333.harvestheritage.init.ModDataComponents;
import me.theabab2333.harvestheritage.init.ModItems;
import me.theabab2333.harvestheritage.init.ModRecipes;
import me.theabab2333.harvestheritage.init.ModSeeds;
import me.theabab2333.harvestheritage.integration.jei.category.FindRecipeCategory;
import me.theabab2333.harvestheritage.integration.jei.category.HybridRecipeCategory;
import me.theabab2333.harvestheritage.recipe.FindRecipe;
import me.theabab2333.harvestheritage.recipe.HybridRecipe;
import me.theabab2333.harvestheritage.util.SeedUtil;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.types.IRecipeHolderType;
import mezz.jei.api.registration.IExtraIngredientRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.ArrayList;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@JeiPlugin
public class ModJeiPlugin implements IModPlugin {
    public static final Supplier<IRecipeHolderType<FindRecipe>> FIND_TYPE = IRecipeHolderType.createDeferred(ModRecipes.FIND_TYPE);
    public static final Supplier<IRecipeHolderType<HybridRecipe>> HYBRID_TYPE = IRecipeHolderType.createDeferred(ModRecipes.HYBRID_TYPE);

    @Override
    public Identifier getPluginUid() {
        return HarvestHeritage.of("jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        var guiHelper = registration.getJeiHelpers().getGuiHelper();

        registration.addRecipeCategories(
            new FindRecipeCategory(guiHelper),
            new HybridRecipeCategory(guiHelper)
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        // find
        registration.addRecipes(FIND_TYPE.get(), ModRecipeSyncEvent.FIND_SEED_RECIPES);

        // hyprid
        var hybrids = ModRecipeSyncEvent.HYBRID_RECIPES.stream()
            .filter(r -> !r.id().identifier().getPath().startsWith("hyprid/common/"))
            .collect(Collectors.toCollection(ArrayList::new));
        var allSeeds = ModSeeds.CROP_SEED.keySet().stream().map(SeedUtil::getHolder).toList();
        var allOutputs = ModSeeds.CROP_SEED.entrySet().stream()
            .map(e -> SeedUtil.createSeedComponent(e.getKey(), e.getValue())).toList();
        hybrids.add(new RecipeHolder<>(
            ResourceKey.create(Registries.RECIPE, HarvestHeritage.of("hyprid/common/all")),
            new HybridRecipe(allSeeds, allOutputs)
        ));
        registration.addRecipes(HYBRID_TYPE.get(), hybrids);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addCraftingStation(FIND_TYPE.get(), ModItems.MAGNIFYING_GLASS);
        registration.addCraftingStation(HYBRID_TYPE.get(), ModBlocks.CROP_STAND_BLOCK);
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        registration.registerFromDataComponentTypes(
            ModItems.SEED_PACKET.get(),
            ModDataComponents.SEED_COMPONENT.get()
        );
        registration.registerFromDataComponentTypes(
            ModItems.KNOWN_SEED.get(),
            ModDataComponents.SEED_COMPONENT.get()
        );
    }

    @Override
    public void registerExtraIngredients(IExtraIngredientRegistration registration) {
        registration.addExtraItemStacks(ModSeeds.getSeedPackets());
    }
}
