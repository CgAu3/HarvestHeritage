package me.theabab2333.harvestheritage.integration.jei.category;

import me.theabab2333.harvestheritage.init.ModBlocks;
import me.theabab2333.harvestheritage.integration.jei.ModJeiPlugin;
import me.theabab2333.harvestheritage.recipe.HybridRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.List;

public class HybridRecipeCategory extends AbstractRecipeCategory<RecipeHolder<HybridRecipe>> {

    public HybridRecipeCategory(IGuiHelper guiHelper) {
        super(
            ModJeiPlugin.HYBRID_TYPE.get(),
            Component.translatable("jei.harvestheritage.hybrid"),
            guiHelper.createDrawableItemLike(ModBlocks.CROP_STAND_BLOCK),
            162,
            80
        );
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<HybridRecipe> holder, IFocusGroup focuses) {
        HybridRecipe recipe = holder.value();
        List<ItemStack> inputStacks = recipe.getInputSeeds().stream()
            .map(ItemStack::new)
            .toList();
        List<ItemStack> outputStacks = recipe.getOutputSeeds().stream()
            .map(comp -> new ItemStack(comp.seed()))
            .toList();

        builder
            .addInputSlot(52, 7)
            .setStandardSlotBackground()
            .addItemStacks(inputStacks);

        builder
            .addSlot(RecipeIngredientRole.RENDER_ONLY, 74, 7)
            .add(ModBlocks.CROP_STAND_BLOCK);

        builder
            .addInputSlot(96, 7)
            .setStandardSlotBackground()
            .addItemStacks(inputStacks.reversed());

        int perRow = 9;
        int slotSize = 18;
        int count = outputStacks.size();
        int startX = count < perRow ? (getWidth() - count * slotSize) / 2 + 2 : 2;
        for (int i = 0; i < count; i++) {
            builder
                .addSlot(RecipeIngredientRole.OUTPUT, startX + (i % perRow) * slotSize, 31 + (i / perRow) * slotSize)
                .setStandardSlotBackground()
                .add(outputStacks.get(i));
        }
    }
}
