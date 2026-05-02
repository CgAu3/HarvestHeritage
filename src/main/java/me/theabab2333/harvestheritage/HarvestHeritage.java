package me.theabab2333.harvestheritage;

import com.mojang.logging.LogUtils;
import me.theabab2333.harvestheritage.init.ModBlocks;
import me.theabab2333.harvestheritage.init.ModConfig;
import me.theabab2333.harvestheritage.init.ModCreativeModeTab;
import me.theabab2333.harvestheritage.init.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

@Mod(HarvestHeritage.MODID)
public class HarvestHeritage {
    public static final String MODID = "harvestheritage";
    public static final Logger LOGGER = LogUtils.getLogger();

    public HarvestHeritage(IEventBus eventBus, ModContainer modContainer) {
        eventBus.addListener(this::commonSetup);

        ModBlocks.register(eventBus);
        ModItems.register(eventBus);
        ModCreativeModeTab.register(eventBus);

        NeoForge.EVENT_BUS.register(this);

        modContainer.registerConfig(net.neoforged.fml.config.ModConfig.Type.COMMON, ModConfig.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (ModConfig.LOG_DIRT_BLOCK.getAsBoolean()) {
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
        }

        LOGGER.info("{}{}", ModConfig.MAGIC_NUMBER_INTRODUCTION.get(), ModConfig.MAGIC_NUMBER.getAsInt());

        ModConfig.ITEM_STRINGS.get().forEach((item) -> LOGGER.info("ITEM >> {}", item));
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }
}
