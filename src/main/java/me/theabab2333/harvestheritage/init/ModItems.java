package me.theabab2333.harvestheritage.init;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static me.theabab2333.harvestheritage.HarvestHeritage.MODID;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    public static final DeferredItem<Item> GRASS_SHEAR = ITEMS.registerSimpleItem(
        "grass_shear",
        p -> p.food(new FoodProperties.Builder().alwaysEdible().nutrition(1).saturationModifier(2f).build())
    );


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
