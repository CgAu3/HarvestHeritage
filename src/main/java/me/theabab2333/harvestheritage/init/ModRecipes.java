package me.theabab2333.harvestheritage.init;

import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.recipe.FindRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModRecipes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(
        Registries.RECIPE_TYPE,
        HarvestHeritage.MODID
    );

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(
        Registries.RECIPE_SERIALIZER,
        HarvestHeritage.MODID
    );

    public static final Supplier<RecipeType<FindRecipe>> FIND_TYPE = RECIPE_TYPES.register("find_type", RecipeType::simple);
    public static final Supplier<RecipeSerializer<FindRecipe>> FIND_TYPE_SERIALIZERS = RECIPE_SERIALIZERS.register(
        "find_type",
        () -> new RecipeSerializer<>(FindRecipe.CODEC, FindRecipe.STREAM_CODEC)
    );

    public static void register(IEventBus eventBus) {
        RECIPE_TYPES.register(eventBus);
        RECIPE_SERIALIZERS.register(eventBus);
    }
}