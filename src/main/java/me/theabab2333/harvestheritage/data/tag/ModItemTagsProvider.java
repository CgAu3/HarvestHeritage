package me.theabab2333.harvestheritage.data.tag;

import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.init.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
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
        tag(ModTags.ItemTags.CAN_FIND_SEEDS)
            .add(Items.BEETROOT_SEEDS)
            .add(Items.MELON_SEEDS)
            .add(Items.PUMPKIN_SEEDS)
            .add(Items.WHEAT_SEEDS)
            .add(Items.TORCHFLOWER_SEEDS);
    }
}
