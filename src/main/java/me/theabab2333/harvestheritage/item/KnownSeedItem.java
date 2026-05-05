package me.theabab2333.harvestheritage.item;

import me.theabab2333.harvestheritage.component.SeedComponent;
import me.theabab2333.harvestheritage.init.ModDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.Optional;
import java.util.function.Consumer;

public class KnownSeedItem extends Item {
    public KnownSeedItem(Properties properties) {
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
        SeedComponent seedInfo = itemStack.get(ModDataComponents.SEED_COMPONENT);
        if (seedInfo == null) {
            Component component = Component.translatable("item.harvestheritage.seed_packet.tooltip.fail")
                .withStyle(ChatFormatting.DARK_RED);
            builder.accept(component);
        } else {
            builder.accept(Component.translatable("item.harvestheritage.seed_packet.tooltip.seed", getName(seedInfo.seed().value()))
                .withStyle(ChatFormatting.GREEN));
        }
    }

    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack itemStack) {
        return super.getTooltipImage(itemStack);
    }

    public Component getName(Item item) {
        return item.getDefaultInstance().getComponents().getOrDefault(DataComponents.ITEM_NAME, CommonComponents.EMPTY);
    }
}
