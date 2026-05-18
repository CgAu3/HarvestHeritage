package me.theabab2333.harvestheritage.datagen.model;

import me.theabab2333.harvestheritage.client.render.item.SeedPacketItemModel;
import me.theabab2333.harvestheritage.init.ModItems;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;

import java.util.List;
import java.util.Optional;

public class ModItemModelProvider {
    public static void registerModels(ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(ModItems.GRASS_SHEAR.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.UNKNOWN_SEED.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.MAGNIFYING_GLASS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.KNOWN_SEED.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.GRAPE.get(), ModelTemplates.FLAT_ITEM);
        seedPacket(itemModels);
    }

    private static void seedPacket(ItemModelGenerators itemModels) {
        var baseModel = ModelTemplates.FLAT_ITEM.create(
            ModelLocationUtils.getModelLocation(ModItems.SEED_PACKET.asItem()),
            TextureMapping.layer0(ModItems.SEED_PACKET.asItem()),
            itemModels.modelOutput
        );
        itemModels.itemModelOutput.accept(
            ModItems.SEED_PACKET.asItem(),
            new SeedPacketItemModel.Unbaked(baseModel, List.of(), Optional.empty())
        );
    }
}
