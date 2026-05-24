package me.theabab2333.harvestheritage.datagen.recipe;

import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.component.SeedComponent;
import me.theabab2333.harvestheritage.init.ModDataComponents;
import me.theabab2333.harvestheritage.init.ModItems;
import me.theabab2333.harvestheritage.init.ModSeeds;
import me.theabab2333.harvestheritage.recipe.FindRecipe;
import me.theabab2333.harvestheritage.util.SeedUtil;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;

import java.util.ArrayList;
import java.util.List;

import static me.theabab2333.harvestheritage.init.ModSeeds.ANIMAL_SEED;
import static me.theabab2333.harvestheritage.init.ModSeeds.CROP_SEED;

public class FindRecipeProvider extends ModRecipeProvider {

    protected FindRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    private static final Identifier FIND = HarvestHeritage.of("find/");

    private static final List<Item> FIND_SEEDS = new ArrayList<>();

    static {
        CROP_SEED.forEach((item, _) -> FIND_SEEDS.add(item));
        ANIMAL_SEED.forEach((item, _) -> FIND_SEEDS.add(item));
    }

    private void buildFindSeeds(RecipeOutput output) {
        FIND_SEEDS.forEach(item -> {
            ModSeeds.SeedInfo seedInfo = SeedUtil.getSeedInfo(item);
            List<Item> resultItems = seedInfo.results();
            int stage = seedInfo.stage();

            Holder<Item> seedHolder = SeedUtil.getHolder(item);
            List<Holder<Item>> resultHolders = resultItems.stream().map(holder -> (Holder<Item>) holder.builtInRegistryHolder()).toList();
            SeedComponent component = SeedComponent.createSeed(seedHolder, resultHolders, stage);
            DataComponentPatch patch = DataComponentPatch.builder().set(ModDataComponents.SEED_COMPONENT.get(), component).build();
            ItemStackTemplate stack = new ItemStackTemplate(ModItems.KNOWN_SEED.get(), 1, patch);
            FindRecipe.Builder.builder(ModItems.UNKNOWN_SEED, List.of(stack)).save(output, FIND.withSuffix(SeedUtil.getPath(item)));
        });
    }

    @Override
    protected void buildRecipes() {
        buildFindSeeds(output);
    }
}
