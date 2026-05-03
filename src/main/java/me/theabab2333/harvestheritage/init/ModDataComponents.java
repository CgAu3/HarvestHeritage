package me.theabab2333.harvestheritage.init;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.Supplier;

import static me.theabab2333.harvestheritage.HarvestHeritage.MODID;

public class ModDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS = DeferredRegister.create(
        Registries.DATA_COMPONENT_TYPE,
        MODID
    );

    public static final Supplier<DataComponentType<Identifier>> SEED_TYPE = DATA_COMPONENTS.register(
        "seed_type",
        () -> DataComponentType.<Identifier>builder().persistent(Identifier.CODEC).networkSynchronized(Identifier.STREAM_CODEC).build()
    );
    public static final Supplier<DataComponentType<List<Identifier>>> SEED_TYPE_LIST = DATA_COMPONENTS.register(
        "seed_type_list",
        () -> DataComponentType.<List<Identifier>>builder().persistent(Identifier.CODEC.listOf()).cacheEncoding().build()
    );

    public static void register(IEventBus event) {
        DATA_COMPONENTS.register(event);
    }
}
