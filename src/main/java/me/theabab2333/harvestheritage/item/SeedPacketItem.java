package me.theabab2333.harvestheritage.item;

import me.theabab2333.harvestheritage.client.render.IItemDisplayInHand;
import me.theabab2333.harvestheritage.component.SeedComponent;
import me.theabab2333.harvestheritage.component.SeedPacketComponent;
import me.theabab2333.harvestheritage.init.ModDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class SeedPacketItem extends KnownSeedItem implements IItemDisplayInHand {
    public SeedPacketItem(Properties properties) {
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
        super.appendHoverText(itemStack, context, display, builder, tooltipFlag);

        SeedPacketComponent seedInfo = itemStack.get(ModDataComponents.SEED_PACKET_COMPONENT);
        if (seedInfo != null) {
            StringBuilder resultBuilder = new StringBuilder();
            for (int i = 0; i < seedInfo.seedComponent().result().size(); i++) {
                if (i > 0) resultBuilder.append(", ");
                resultBuilder.append(getName(seedInfo.seedComponent().result().get(i).value()).getString());
            }
            builder.accept(Component.translatable("item.harvestheritage.seed_packet.tooltip.result", resultBuilder.toString())
                .withStyle(ChatFormatting.YELLOW));

            builder.accept(Component.translatable("item.harvestheritage.seed_packet.tooltip.speed", seedInfo.speed())
                .withStyle(ChatFormatting.BLUE));
            builder.accept(Component.translatable("item.harvestheritage.seed_packet.tooltip.output", seedInfo.output())
                .withStyle(ChatFormatting.GOLD));
        }
    }

    @Override
    public ItemStack getDisplayedItem(ItemStack stack) {
        ItemStack itemStack = ItemStack.EMPTY;
        if (stack.get(ModDataComponents.SEED_COMPONENT) instanceof SeedComponent component) {
            itemStack = component.seed().value().getDefaultInstance();
        }
        return itemStack;
    }

    @Override
    public int offsetX(ItemStack stack) {
        return 0;
    }

    @Override
    public int offsetY(ItemStack stack) {
        return 0;
    }
}
