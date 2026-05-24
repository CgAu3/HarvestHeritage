package me.theabab2333.harvestheritage.datagen;

import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.datagen.lang.ENUSProvider;
import me.theabab2333.harvestheritage.datagen.lang.ZHCNProvider;
import me.theabab2333.harvestheritage.datagen.loot.ModLootTables;
import me.theabab2333.harvestheritage.datagen.model.ModModelProvider;
import me.theabab2333.harvestheritage.datagen.recipe.ModRecipeProvider;
import me.theabab2333.harvestheritage.datagen.tag.ModBlockTagsProvider;
import me.theabab2333.harvestheritage.datagen.tag.ModItemTagsProvider;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = HarvestHeritage.MODID, value = Dist.CLIENT)
public class Datagen {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        // Model
        event.createProvider(ModModelProvider::new);

        // Tag
        event.createProvider(ModItemTagsProvider::new);
        event.createProvider(ModBlockTagsProvider::new);

        // Recipe
        event.createProvider(ModRecipeProvider.Runner::new);

        // LootTab
        event.createProvider(ModLootTables::new);

        // Lang
        event.createProvider(ENUSProvider::new);
        event.createProvider(ZHCNProvider::new);
    }
}
