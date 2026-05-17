package me.theabab2333.harvestheritage.item;

import me.theabab2333.harvestheritage.api.render.IItemDisplayInHand;
import me.theabab2333.harvestheritage.component.SeedComponent;
import me.theabab2333.harvestheritage.component.SeedPacketComponent;
import me.theabab2333.harvestheritage.init.ModDataComponents;
import me.theabab2333.harvestheritage.util.SeedUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
public class SeedPacketItem extends KnownSeedItem implements IItemDisplayInHand {
    public SeedPacketItem(Properties properties) {
        super(properties);
    }

    @Override
    public List<Component> getTooltip(ItemStack itemStack) {
        List<Component> list = new ArrayList<>();
        SeedPacketComponent packetComponent = itemStack.get(ModDataComponents.SEED_PACKET_COMPONENT);
        if (packetComponent != null) {
            SeedComponent seedComponent = packetComponent.seedComponent();
            list.add(Component.translatable("item.harvestheritage.seed.tooltip.seed", SeedUtil.getSeedName(seedComponent.seed().value()))
                .withStyle(ChatFormatting.GREEN));
            list.add(Component.translatable("item.harvestheritage.seed.tooltip.stage", seedComponent.stage())
                .withStyle(ChatFormatting.LIGHT_PURPLE));
            StringBuilder resultBuilder = new StringBuilder();
            for (int i = 0; i < packetComponent.seedComponent().result().size(); i++) {
                if (i > 0) resultBuilder.append(", ");
                resultBuilder.append(SeedUtil.getSeedName(packetComponent.seedComponent().result().get(i).value()).getString());
            }
            list.add(Component.translatable("item.harvestheritage.seed_packet.tooltip.result", resultBuilder.toString())
                .withStyle(ChatFormatting.YELLOW));

            list.add(Component.translatable("item.harvestheritage.seed_packet.tooltip.speed", packetComponent.speed())
                .withStyle(ChatFormatting.BLUE));
            list.add(Component.translatable("item.harvestheritage.seed_packet.tooltip.output", packetComponent.output())
                .withStyle(ChatFormatting.GOLD));
        } else {
            list.addAll(super.getTooltip(itemStack));
        }

        return list;
    }

    @Override
    public ItemStack getDisplayedItem(ItemStack stack) {
        if (stack.get(ModDataComponents.SEED_PACKET_COMPONENT) instanceof SeedPacketComponent component) {
            return component.seedComponent().seed().value().getDefaultInstance();
        }

        if (stack.get(ModDataComponents.SEED_COMPONENT) instanceof SeedComponent component) {
            return component.seed().value().getDefaultInstance();
        }

        return ItemStack.EMPTY;
    }

    @Override
    public int offsetX(ItemStack stack) {
        return 5;
    }

    @Override
    public int offsetY(ItemStack stack) {
        return 2;
    }

    @Override
    public int scale(ItemStack stack) {
        return 16;
    }

    @Override
    public Holder<Item> seed(ItemStack itemStack) {
        return super.seed(itemStack);
    }

    @Override
    public List<Holder<Item>> result(ItemStack itemStack) {
        return super.result(itemStack);
    }

    @Override
    public int speed(ItemStack itemStack) {
        return Objects.requireNonNull(this.getSeedPacketComponent(itemStack)).speed();
    }

    @Override
    public int output(ItemStack itemStack) {
        return Objects.requireNonNull(this.getSeedPacketComponent(itemStack)).output();
    }
}
