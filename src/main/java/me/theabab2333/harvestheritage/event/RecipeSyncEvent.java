package me.theabab2333.harvestheritage.event;

import lombok.Getter;
import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.init.ModRecipes;
import me.theabab2333.harvestheritage.recipe.FindRecipe;
import me.theabab2333.harvestheritage.recipe.SeedPacketRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.RecipesReceivedEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = HarvestHeritage.MODID)
public class RecipeSyncEvent {
    @Getter
    private static final List<RecipeHolder<@NotNull FindRecipe>> FIND_SEED_RECIPES = new ArrayList<>();
    @Getter
    private static final List<RecipeHolder<@NotNull SeedPacketRecipe>> SEED_PACKET_RECIPES = new ArrayList<>();

    @SubscribeEvent
    public static void onDatapackSync(OnDatapackSyncEvent event) {
        event.sendRecipes(
            ModRecipes.FIND_TYPE.get(),
            ModRecipes.SEED_PACKET_TYPE.get()
        );
    }

    @SubscribeEvent
    public static void recipesReceived(RecipesReceivedEvent event) {
        FIND_SEED_RECIPES.clear();
        SEED_PACKET_RECIPES.clear();
        FIND_SEED_RECIPES.addAll(event.getRecipeMap().byType(ModRecipes.FIND_TYPE.get()));
        SEED_PACKET_RECIPES.addAll(event.getRecipeMap().byType(ModRecipes.SEED_PACKET_TYPE.get()));
    }

    @SubscribeEvent
    public static void clientLogOut(ClientPlayerNetworkEvent.LoggingOut event) {
        FIND_SEED_RECIPES.clear();
        SEED_PACKET_RECIPES.clear();
    }
}
