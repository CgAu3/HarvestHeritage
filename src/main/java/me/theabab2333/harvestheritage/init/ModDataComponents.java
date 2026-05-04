package me.theabab2333.harvestheritage.init;

import me.theabab2333.harvestheritage.component.SeedPacketComponent;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static me.theabab2333.harvestheritage.HarvestHeritage.MODID;

public class ModDataComponents {
    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(
        Registries.DATA_COMPONENT_TYPE,
        MODID
    );

    public static final Supplier<DataComponentType<SeedPacketComponent>> SEED_PACKET_COMPONENT = DATA_COMPONENTS.registerComponentType(
        "seed_packet_component",
        builder -> builder
            .networkSynchronized(SeedPacketComponent.STREAM_CODEC)
            .persistent(SeedPacketComponent.CODEC)
    );

    public static void register(IEventBus event) {
        DATA_COMPONENTS.register(event);
    }
}
