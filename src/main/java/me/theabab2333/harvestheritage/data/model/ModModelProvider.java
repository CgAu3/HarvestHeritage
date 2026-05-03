package me.theabab2333.harvestheritage.data.model;

import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.init.ModBlocks;
import me.theabab2333.harvestheritage.init.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;

public class ModModelProvider extends ModelProvider {
    public ModModelProvider(PackOutput output) {
        super(output, HarvestHeritage.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        blockModels.createTrivialCube(ModBlocks.TEST_BLOCK.get());
        itemModels.generateFlatItem(ModItems.GRASS_SHEAR.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.UNKNOWN_SEED.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.MAGNIFYING_GLASS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.KNOWN_SEED.get(), ModelTemplates.FLAT_ITEM);
    }
}
