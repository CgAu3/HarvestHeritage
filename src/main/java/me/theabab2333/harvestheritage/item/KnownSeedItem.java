package me.theabab2333.harvestheritage.item;

import me.theabab2333.harvestheritage.api.item.ISeedItem;
import me.theabab2333.harvestheritage.component.SeedComponent;
import me.theabab2333.harvestheritage.component.SeedPacketComponent;
import me.theabab2333.harvestheritage.init.ModDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class KnownSeedItem extends Item implements ISeedItem {
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

    @Override
    public Holder<@NotNull Item> seed(ItemStack itemStack) {
        return this.getSeedComponent(itemStack).seed();
    }

    @Override
    public List<Holder<@NotNull Item>> result(ItemStack itemStack) {
        return this.getSeedComponent(itemStack).result();
    }

    @Override
    public int speed(ItemStack itemStack) {
        return 1;
    }

    @Override
    public int output(ItemStack itemStack) {
        return 1;
    }

    protected SeedComponent getSeedComponent(ItemStack itemStack) {
        SeedPacketComponent component = this.getSeedPacketComponent(itemStack);
        if (component != null) {
            return component.seedComponent();
        } else {
            Holder<@NotNull Item> holder = Items.AIR.builtInRegistryHolder();
            return SeedComponent.createSeed(holder, List.of(holder));
        }
    }

    @Nullable
    protected SeedPacketComponent getSeedPacketComponent(ItemStack itemStack) {
        return itemStack.get(ModDataComponents.SEED_PACKET_COMPONENT);
    }
}
