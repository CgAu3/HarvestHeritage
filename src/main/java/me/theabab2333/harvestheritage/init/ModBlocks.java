package me.theabab2333.harvestheritage.init;

import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.block.ArableLandBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

import static me.theabab2333.harvestheritage.HarvestHeritage.MODID;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);

    public static final DeferredBlock<Block> ARABLE_LAND_BLOCK = registerBlockWithItem(
        "arable_land_block",
        ArableLandBlock::new
    );

    public static final DeferredBlock<Block> TEST_BLOCK = registerBlockWithItem(
        "test_block",
        Block::new
    );

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

    private static <B extends Block> DeferredBlock<B> registerBlockWithItem(
        String name,
        Function<BlockBehaviour.Properties, ? extends B> func
    ) {
        DeferredBlock<B> block = BLOCKS.registerBlock(name, func);
        registerBlockItem(name, block);
        return block;
    }

    private static <B extends Block> void registerBlockItem(String name, DeferredBlock<B> block) {
        ModItems.ITEMS.register(
            name, () -> new BlockItem(
                block.get(), new Item.Properties().useBlockDescriptionPrefix()
                .setId(
                    ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(HarvestHeritage.MODID, name))
                )
            )
        );
    }
}
