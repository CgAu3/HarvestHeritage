package me.theabab2333.harvestheritage.item;

import me.theabab2333.harvestheritage.api.item.ISeedItem;
import me.theabab2333.harvestheritage.api.item.ITooltipItem;
import me.theabab2333.harvestheritage.component.SeedComponent;
import me.theabab2333.harvestheritage.component.SeedPacketComponent;
import me.theabab2333.harvestheritage.init.ModDataComponents;
import me.theabab2333.harvestheritage.util.SeedUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class KnownSeedItem extends Item implements ISeedItem, ITooltipItem {
    public KnownSeedItem(Properties properties) {
        super(properties);
    }

    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack itemStack) {
        return super.getTooltipImage(itemStack);
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
        return 0;
    }

    @Override
    public int output(ItemStack itemStack) {
        return 0;
    }

    protected SeedComponent getSeedComponent(ItemStack itemStack) {
        SeedPacketComponent component = this.getSeedPacketComponent(itemStack);
        if (component != null) {
            return component.seedComponent();
        } else if (itemStack.get(ModDataComponents.SEED_COMPONENT) instanceof SeedComponent seedComponent) {
            return seedComponent;
        } else {
            Holder<Item> holder = Items.AIR.builtInRegistryHolder();
            return SeedComponent.createSeed(holder, List.of(holder), 0);
        }
    }

    @Nullable
    protected SeedPacketComponent getSeedPacketComponent(ItemStack itemStack) {
        return itemStack.get(ModDataComponents.SEED_PACKET_COMPONENT);
    }

    @Override
    public List<Component> getTooltip(ItemStack itemStack) {
        List<Component> list = new ArrayList<>();
        SeedComponent seedInfo = itemStack.get(ModDataComponents.SEED_COMPONENT);
        if (seedInfo == null) {
            Component component = Component.translatable("item.harvestheritage.seed.tooltip.fail").withStyle(ChatFormatting.DARK_RED);
            list.add(component);
        } else {
            list.add(Component.translatable("item.harvestheritage.seed.tooltip.seed", SeedUtil.getSeedName(seedInfo.seed().value()))
                .withStyle(ChatFormatting.GREEN));
            list.add(Component.translatable("item.harvestheritage.seed.tooltip.stage", seedInfo.stage())
                .withStyle(ChatFormatting.LIGHT_PURPLE));
        }
        return list;
    }
}
