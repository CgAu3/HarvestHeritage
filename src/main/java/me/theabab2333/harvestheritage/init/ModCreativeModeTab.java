package me.theabab2333.harvestheritage.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;

import static me.theabab2333.harvestheritage.HarvestHeritage.MODID;

public class ModCreativeModeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> COMMON_TAB = CREATIVE_MODE_TABS.register(
        "common_tab",
        () -> CreativeModeTab.builder()
            .displayItems((parameters, output) -> getItem().forEach(output::accept))
            .icon(() -> new ItemStack(ModItems.GRASS_SHEAR.get()))
            .title(Component.translatable("modmenu.nameTranslation.harvestheritage"))
            .build()
    );

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> SEED_TAB = CREATIVE_MODE_TABS.register(
        "seed_tab",
        () -> CreativeModeTab.builder()
            .displayItems((parameters, output) -> ModSeeds.getSeeds().forEach(output::accept))
            .icon(() -> new ItemStack(ModItems.SEED_PACKET.get()))
            .withTabsBefore(COMMON_TAB.getKey().identifier())
            .title(Component.translatable("creativetab.harvestheritage.seed_packet"))
            .build()
    );

    public static List<ItemLike> getItem() {
        List<ItemLike> items = new ArrayList<>();
        ModItems.ITEMS.getEntries().forEach(holder -> items.add(holder.get()));
        ModBlocks.BLOCKS.getEntries().forEach(holder -> items.add(holder.get()));
        return items;
    }

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
