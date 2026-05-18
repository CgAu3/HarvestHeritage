package me.theabab2333.harvestheritage.item;

import me.theabab2333.harvestheritage.api.item.ITooltipItem;
import me.theabab2333.harvestheritage.component.SeedComponent;
import me.theabab2333.harvestheritage.init.ModDataComponents;
import me.theabab2333.harvestheritage.util.SeedUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class KnownSeedItem extends Item implements ITooltipItem {
    public KnownSeedItem(Properties properties) {
        super(properties);
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
