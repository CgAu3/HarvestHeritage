package me.theabab2333.harvestheritage.init;

import me.theabab2333.harvestheritage.item.UnknownSeedItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static me.theabab2333.harvestheritage.HarvestHeritage.MODID;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    public static final DeferredItem<Item> GRASS_SHEAR = ITEMS.registerSimpleItem("grass_shear", p -> p);

    public static final DeferredItem<Item> UNKNOWN_SEED = ITEMS.registerItem("unknown_seed", UnknownSeedItem::new);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
