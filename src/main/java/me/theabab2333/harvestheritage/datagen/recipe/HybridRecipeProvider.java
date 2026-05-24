package me.theabab2333.harvestheritage.datagen.recipe;

import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.component.SeedComponent;
import me.theabab2333.harvestheritage.init.ModSeeds;
import me.theabab2333.harvestheritage.recipe.HybridRecipe;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HybridRecipeProvider extends ModRecipeProvider {
    protected HybridRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    private static final Identifier HYPRID = HarvestHeritage.of("hyprid/");

    private static final List<Item> COMMON_SEEDS = List.of(
        Items.WHEAT_SEEDS,
        Items.BEETROOT_SEEDS,
        Items.MELON_SEEDS,
        Items.PUMPKIN_SEEDS,
        Items.SWEET_BERRIES,
        Items.GLOW_BERRIES,
        Items.CHORUS_FRUIT,
        Items.CARROT,
        Items.POTATO
    );

    @Override
    protected void buildRecipes() {
        // TODO: 成就-疯狂疯狂星期四~
        HybridRecipe.Builder.builder(List.of(getHolder(Items.WHEAT), getHolder(Items.WHEAT)), List.of(fromItem(Items.CHICKEN)))
            .save(output, HYPRID.withSuffix("maimai"));
        // common seeds
        buildCommonSeeds();

    }

    private void buildCommonSeeds() {
        for (int i = 0; i < COMMON_SEEDS.size(); i++) {
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
                    .save(output, HYPRID.withSuffix("common/").withSuffix(getName(input1) + "_and_" + getName(input2)));
            }
        }
    }

    private SeedComponent fromItem(Item item) {
        ModSeeds.SeedInfo seedInfo = ModSeeds.SEEDS.get(item);
        if (seedInfo != null) {
            List<Holder<Item>> holders = seedInfo.results().stream().map(Item::builtInRegistryHolder).collect(Collectors.toList());
            return new SeedComponent(getHolder(item), holders, seedInfo.stage());
        }
        throw new IllegalArgumentException("No seed info found for item: " + item);
    }

    private static Holder<Item> getHolder(Item item) {
        return item.builtInRegistryHolder();
    }

    private static String getName(Item item) {
        return BuiltInRegistries.ITEM.getKey(item).getPath();
    }
}
