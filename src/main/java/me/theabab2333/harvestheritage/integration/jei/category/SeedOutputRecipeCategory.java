package me.theabab2333.harvestheritage.integration.jei.category;

import me.theabab2333.harvestheritage.init.ModItems;
import me.theabab2333.harvestheritage.integration.jei.ModJeiPlugin;
import me.theabab2333.harvestheritage.integration.jei.recipe.SeedOutputRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class SeedOutputRecipeCategory extends AbstractRecipeCategory<SeedOutputRecipe> {

    public SeedOutputRecipeCategory(IGuiHelper guiHelper) {
        super(
            ModJeiPlugin.SEED_OUTPUT_TYPE,
            Component.translatable("jei.harvestheritage.seed_output"),
            guiHelper.createDrawableItemLike(ModItems.SEED_PACKET),
            120,
            54
        );
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, SeedOutputRecipe recipe, IFocusGroup focuses) {
        builder.addInputSlot(1, 18)
            .setStandardSlotBackground()
            .add(recipe.seedPacket());

        List<ItemStack> outputs = recipe.outputs();
        int perRow = 9;
        int startX = 50;
        int startY = 18;
        for (int i = 0; i < outputs.size(); i++) {
            builder.addSlot(RecipeIngredientRole.OUTPUT, startX + (i % perRow) * 18, startY + (i / perRow) * 18)
                .setStandardSlotBackground()
                .add(outputs.get(i));
        }
    }
}
