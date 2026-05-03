package me.theabab2333.harvestheritage.data.loot;

import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.init.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.concurrent.CompletableFuture;

public class ModGlobalLootModifierProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, HarvestHeritage.MODID);
    }

    @Override
    protected void start() {
        add(
            "grass_shear_drop", new AddItemLootModifier(
                new LootItemCondition[]{
                    LootTableIdCondition.builder(Identifier.withDefaultNamespace("blocks/short_grass")).build(),
                    LootItemRandomChanceCondition.randomChance(0.0625f).build()
                }, 0, ModItems.UNKNOWN_SEED.get()
            )
        );
    }
}
