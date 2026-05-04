package me.theabab2333.harvestheritage.datagen.tag;

import me.theabab2333.harvestheritage.HarvestHeritage;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(
        PackOutput output,
        CompletableFuture<HolderLookup.Provider> lookupProvider
    ) {
        super(output, lookupProvider, HarvestHeritage.MODID);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {

    }
}
