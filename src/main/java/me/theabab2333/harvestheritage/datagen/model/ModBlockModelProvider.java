package me.theabab2333.harvestheritage.datagen.model;

import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.init.ModBlocks;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.BlockModelDefinitionGenerator;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.function.Consumer;

import static net.minecraft.client.data.models.BlockModelGenerators.createBooleanModelDispatch;
import static net.minecraft.client.data.models.BlockModelGenerators.plainVariant;

public class ModBlockModelProvider {
    public static void registerModels(BlockModelGenerators blockModels) {
        Consumer<BlockModelDefinitionGenerator> blockStateOutput = blockModels.blockStateOutput;

        blockModels.createTrivialCube(ModBlocks.TEST_BLOCK.get());
        blockModels.createTrivialCube(ModBlocks.ACTIVAOR_BLOCK.get());

        blockStateOutput.accept(MultiPartGenerator.multiPart(ModBlocks.CROP_STAND_BLOCK.get())
            .with(plainVariant(HarvestHeritage.of("block/crop_stand_block"))));
        createScaffolding(blockModels);
    }

    private static void createScaffolding(BlockModelGenerators blockModels) {
        Identifier stableModel = ModelLocationUtils.getModelLocation(Blocks.SCAFFOLDING, "_stable");
        MultiVariant stable = plainVariant(stableModel);
        MultiVariant unstable = plainVariant(ModelLocationUtils.getModelLocation(Blocks.SCAFFOLDING, "_unstable"));
        blockModels.registerSimpleItemModel(ModBlocks.SCAFFOLDING_CROP_STAND_BLOCK.get(), stableModel);
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(ModBlocks.SCAFFOLDING_CROP_STAND_BLOCK.get())
            .with(createBooleanModelDispatch(BlockStateProperties.BOTTOM, unstable, stable)));
    }
}
