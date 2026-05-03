package me.theabab2333.harvestheritage.data;

import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.data.lang.ENUSProvider;
import me.theabab2333.harvestheritage.data.lang.ZHCNProvider;
import me.theabab2333.harvestheritage.data.loot.ModGlobalLootModifierProvider;
import me.theabab2333.harvestheritage.data.model.ModModelProvider;
import me.theabab2333.harvestheritage.data.tag.ModBlockTagsProvider;
import me.theabab2333.harvestheritage.data.tag.ModItemTagsProvider;
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

        // LootTab
        event.createProvider(ModGlobalLootModifierProvider::new);

        // Lang
        event.createProvider(ENUSProvider::new);
        event.createProvider(ZHCNProvider::new);
    }
}
