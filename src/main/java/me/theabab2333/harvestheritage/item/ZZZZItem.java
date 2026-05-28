package me.theabab2333.harvestheritage.item;

import me.theabab2333.harvestheritage.api.item.IHasTooltips;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

import static me.theabab2333.harvestheritage.util.StyleUtil.colorFromRatio;

public class ZZZZItem extends Item implements IHasTooltips {

    public ZZZZItem(Properties properties) {
        super(properties);
    }

    @Override
    public List<Component> getTooltip(ItemStack itemStack) {
        return List.of(Component.translatable("item.harvestheritage.zzzz.tooltip").setStyle(colorFromRatio()));
    }

    @Override
    public Component getName(ItemStack itemStack) {
        return Component.translatable("item.harvestheritage.zzzz").setStyle(colorFromRatio());
    }
}
