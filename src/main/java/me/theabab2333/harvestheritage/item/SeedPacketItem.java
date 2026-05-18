package me.theabab2333.harvestheritage.item;

import me.theabab2333.harvestheritage.api.item.ISeedItem;
import me.theabab2333.harvestheritage.component.SeedComponent;
import me.theabab2333.harvestheritage.component.SeedPacketComponent;
import me.theabab2333.harvestheritage.init.ModDataComponents;
import me.theabab2333.harvestheritage.util.SeedUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

public class SeedPacketItem extends KnownSeedItem implements ISeedItem {
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
    public Holder<Item> seed(ItemStack itemStack) {
        if (itemStack.get(ModDataComponents.SEED_PACKET_COMPONENT) instanceof SeedPacketComponent seedPacketComponent) {
            return seedPacketComponent.seedComponent().seed();
        } else if (itemStack.get(ModDataComponents.SEED_COMPONENT) instanceof SeedComponent seedComponent) {
            return seedComponent.seed();
        } else {
            return Items.AIR.builtInRegistryHolder();
        }
    }

    @Override
    public List<Holder<Item>> result(ItemStack itemStack) {
        if (itemStack.get(ModDataComponents.SEED_PACKET_COMPONENT) instanceof SeedPacketComponent seedPacketComponent) {
            return seedPacketComponent.seedComponent().result();
        } else if (itemStack.get(ModDataComponents.SEED_COMPONENT) instanceof SeedComponent seedComponent) {
            return seedComponent.result();
        } else {
            return List.of(Items.AIR.builtInRegistryHolder());
        }
    }

    @Override
    public int speed(ItemStack itemStack) {
        if (itemStack.get(ModDataComponents.SEED_PACKET_COMPONENT) instanceof SeedPacketComponent seedPacketComponent) {
            return seedPacketComponent.speed();
        } else {
            return 0;
        }
    }

    @Override
    public int output(ItemStack itemStack) {
        if (itemStack.get(ModDataComponents.SEED_PACKET_COMPONENT) instanceof SeedPacketComponent seedPacketComponent) {
            return seedPacketComponent.output();
        } else {
            return 0;
        }
    }
}
