package me.theabab2333.harvestheritage.init;

import me.theabab2333.harvestheritage.HarvestHeritage;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {

    private static TagKey<Item> createItemTag(String name) {
        return net.minecraft.tags.ItemTags.create(Identifier.fromNamespaceAndPath(HarvestHeritage.MODID, name));
    }

    private static TagKey<Block> createBlockTag(String name) {
        return net.minecraft.tags.BlockTags.create(Identifier.fromNamespaceAndPath(HarvestHeritage.MODID, name));
    }

    public static class ItemTags {

    }

    public static class BlockTags {
        public static TagKey<Block> CAN_SHEAR = createBlockTag("can_shear");
    }
}
