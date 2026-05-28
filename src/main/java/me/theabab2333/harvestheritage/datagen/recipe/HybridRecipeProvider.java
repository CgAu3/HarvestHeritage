package me.theabab2333.harvestheritage.datagen.recipe;

import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.component.SeedComponent;
import me.theabab2333.harvestheritage.recipe.HybridRecipe;
import me.theabab2333.harvestheritage.util.SeedUtil;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

import static me.theabab2333.harvestheritage.init.ModSeeds.CROP_SEED;
import static me.theabab2333.harvestheritage.util.SeedUtil.getHolder;
import static me.theabab2333.harvestheritage.util.SeedUtil.getPath;

public class HybridRecipeProvider extends ModRecipeProvider {
    protected HybridRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    private static final Identifier HYPRID = HarvestHeritage.of("hyprid/");

    public static final List<Item> COMMON_SEEDS = new ArrayList<>();

    static {
        CROP_SEED.forEach((item, _) -> COMMON_SEEDS.add(item));
    }

    @Override
    protected void buildRecipes() {
        // common seeds
        buildCommonSeeds();

        // other
        buildSeed(List.of(Items.EGG, Items.SUGAR_CANE), Items.SLIME_BALL);
        buildSeed(List.of(Items.SLIME_BALL, Items.CHORUS_FRUIT), Items.ENDER_PEARL);
    }

    private void buildSeed(List<Item> inputs, Item output) {
        if (inputs.size() != 2) {
            HarvestHeritage.LOGGER.warn("Datagen hyprid Recipe is exception!");
            return;
        }
        List<Holder<Item>> holders = new ArrayList<>();
        inputs.forEach(item -> holders.add(getHolder(item)));
        HybridRecipe.Builder.builder(holders, List.of(fromItem(output))).save(this.output, HYPRID.withSuffix(getPath(output)));
    }

    private void buildCommonSeeds() {
        for (int i = 0; i < CROP_SEED.size(); i++) {
            for (int j = i + 1; j < COMMON_SEEDS.size(); j++) {
                Item input1 = COMMON_SEEDS.get(i);
                Item input2 = COMMON_SEEDS.get(j);
                List<Item> outputs = new ArrayList<>();
                for (Item seed : COMMON_SEEDS) {
                    if (seed != input1 && seed != input2) {
                        outputs.add(seed);
                    }
                }
                HybridRecipe.Builder.builder(List.of(getHolder(input1), getHolder(input2)), outputs.stream().map(this::fromItem).toList())
                    .save(output, HYPRID.withSuffix("common/").withSuffix(getPath(input1) + "_and_" + getPath(input2)));
            }
        }
    }

    private SeedComponent fromItem(Item item) {
        return SeedUtil.getSeedComponent(item);
    }
}
