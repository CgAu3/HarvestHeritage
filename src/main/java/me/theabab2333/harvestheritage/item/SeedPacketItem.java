package me.theabab2333.harvestheritage.item;

import me.theabab2333.harvestheritage.api.render.IItemDisplayInHand;
import me.theabab2333.harvestheritage.component.SeedComponent;
import me.theabab2333.harvestheritage.component.SeedPacketComponent;
import me.theabab2333.harvestheritage.init.ModDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
public class SeedPacketItem extends KnownSeedItem implements IItemDisplayInHand {
    public SeedPacketItem(Properties properties) {
        super(properties);
    }

    @Override
    public List<Component> getTooltip(ItemStack itemStack) {
        List<Component> list = super.getTooltip(itemStack);
        SeedPacketComponent seedInfo = itemStack.get(ModDataComponents.SEED_PACKET_COMPONENT);
        if (seedInfo != null) {
            StringBuilder resultBuilder = new StringBuilder();
            for (int i = 0; i < seedInfo.seedComponent().result().size(); i++) {
                if (i > 0) resultBuilder.append(", ");
                resultBuilder.append(getName(seedInfo.seedComponent().result().get(i).value()).getString());
            }
            list.add(Component.translatable("item.harvestheritage.seed_packet.tooltip.result", resultBuilder.toString())
                .withStyle(ChatFormatting.YELLOW));

            list.add(Component.translatable("item.harvestheritage.seed_packet.tooltip.speed", seedInfo.speed())
                .withStyle(ChatFormatting.BLUE));
            list.add(Component.translatable("item.harvestheritage.seed_packet.tooltip.output", seedInfo.output())
                .withStyle(ChatFormatting.GOLD));
            list.add(Component.translatable("item.harvestheritage.seed_packet.tooltip.iterate", seedInfo.iterate())
                .withStyle(ChatFormatting.AQUA));
        }

        return list;
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
    public Holder<@NotNull Item> seed(ItemStack itemStack) {
        return super.seed(itemStack);
    }

    @Override
    public List<Holder<@NotNull Item>> result(ItemStack itemStack) {
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
