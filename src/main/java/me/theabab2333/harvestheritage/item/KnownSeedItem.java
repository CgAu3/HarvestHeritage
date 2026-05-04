package me.theabab2333.harvestheritage.item;

import me.theabab2333.harvestheritage.component.SeedPacketComponent;
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
        SeedPacketComponent seedInfo = itemStack.get(ModDataComponents.SEED_PACKET_COMPONENT);
        if (seedInfo == null) {
            Component component = Component.translatable("item.harvestheritage.seed_packet.tooltip.fail")
                .withStyle(ChatFormatting.DARK_RED);
            builder.accept(component);
        } else {
            builder.accept(Component.translatable(
                    "item.harvestheritage.seed_packet.tooltip.seed",
                    getName(seedInfo.seedComponent().seed().value())
                )
                .withStyle(ChatFormatting.GREEN));

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
    public Optional<TooltipComponent> getTooltipImage(ItemStack itemStack) {
        return super.getTooltipImage(itemStack);
    }

    public Component getName(Item item) {
        return item.getDefaultInstance().getComponents().getOrDefault(DataComponents.ITEM_NAME, CommonComponents.EMPTY);
    }
}
