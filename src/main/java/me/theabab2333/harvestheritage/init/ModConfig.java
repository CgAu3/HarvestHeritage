package me.theabab2333.harvestheritage.init;

import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.ModConfigSpec;

public class ModConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec SPEC = BUILDER.build();

    public static final ModConfigSpec.IntValue SEED_SPEED_MAX = BUILDER.comment("Maximum seed growth rate")
        .defineInRange("seed_speed_max", 31, 0, Integer.MAX_VALUE);

    public static void register(ModContainer modContainer) {
        modContainer.registerConfig(net.neoforged.fml.config.ModConfig.Type.SERVER, ModConfig.SPEC);
    }
}
