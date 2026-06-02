package me.theabab2333.harvestheritage.datagen.model;

import me.theabab2333.harvestheritage.client.render.item.SeedPacketItemModel;
import me.theabab2333.harvestheritage.init.ModItems;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;

import java.util.List;
import java.util.Optional;

public class ModItemModelProvider {
    public static void registerModels(ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(ModItems.GRASS_SHEAR.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.UNKNOWN_SEED.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.KNOWN_SEED.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.GRAPE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.ZZZZ.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.FRIED_SEEDSACK.get(), ModelTemplates.FLAT_ITEM);
        magnifyingGlass(itemModels);
        kfc(itemModels);
        seedPacket(itemModels);
    }

    private static void magnifyingGlass(ItemModelGenerators itemModels) {
        itemModels.itemModelOutput.accept(
            ModItems.MAGNIFYING_GLASS.asItem(),
            ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(ModItems.MAGNIFYING_GLASS.asItem()))
        );
    }

    private static void kfc(ItemModelGenerators itemModels) {
        var kfcModel = itemModels.generateLayeredItem(
            ModItems.KFC.asItem(),
            new Material(Identifier.fromNamespaceAndPath("harvestheritage", "item/seed_packet")),
            new Material(Identifier.fromNamespaceAndPath("minecraft", "item/cooked_chicken"))
        );
        itemModels.itemModelOutput.accept(ModItems.KFC.asItem(), ItemModelUtils.plainModel(kfcModel));
    }

    private static void seedPacket(ItemModelGenerators itemModels) {
        Identifier baseModel = ModelTemplates.FLAT_ITEM.create(
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
