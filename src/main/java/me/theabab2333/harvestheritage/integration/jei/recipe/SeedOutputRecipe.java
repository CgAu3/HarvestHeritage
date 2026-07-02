package me.theabab2333.harvestheritage.integration.jei.recipe;

import net.minecraft.world.item.ItemStack;

import java.util.List;

public record SeedOutputRecipe(ItemStack seedPacket, List<ItemStack> outputs) {
}
