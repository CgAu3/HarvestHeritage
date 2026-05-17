package me.theabab2333.harvestheritage.datagen.model;

import me.theabab2333.harvestheritage.init.ModItems;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;

public class ModItemModelProvider {
    public static void registerModels(ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(ModItems.GRASS_SHEAR.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.UNKNOWN_SEED.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.MAGNIFYING_GLASS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.KNOWN_SEED.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.SEED_PACKET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.GRAPE.get(), ModelTemplates.FLAT_ITEM);
    }
}
