package me.theabab2333.harvestheritage.data.tag;

import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.init.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(
        PackOutput output,
        CompletableFuture<HolderLookup.Provider> lookupProvider
    ) {
        super(output, lookupProvider, HarvestHeritage.MODID);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(ModTags.BlockTags.CAN_SHEAR)
            .add(Blocks.SHORT_GRASS)
            .add(Blocks.SHORT_DRY_GRASS)
            .add(Blocks.TALL_GRASS)
            .add(Blocks.TALL_DRY_GRASS)
            .add(Blocks.FERN)
            .add(Blocks.FERN);
    }
}
