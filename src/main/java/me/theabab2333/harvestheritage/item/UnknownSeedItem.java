package me.theabab2333.harvestheritage.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class UnknownSeedItem extends Item {
    public UnknownSeedItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(
        ItemStack itemStack,
        TooltipContext context,
        TooltipDisplay display,
        Consumer<Component> builder,
        TooltipFlag tooltipFlag
    ) {
        builder.accept(Component.translatable("item.harvestheritage.unknown_seed.tooltip").withStyle(ChatFormatting.GRAY));
    }
}
