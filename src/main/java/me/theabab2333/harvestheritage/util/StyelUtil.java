package me.theabab2333.harvestheritage.util;

import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;

public class StyelUtil {
    public static Style colorFromRatio() {
        var level = net.minecraft.client.Minecraft.getInstance().level;
        double ratio = level == null ? 0.5 : (Math.sin(level.getGameTime() * Math.PI / 50.0) + 1.0) / 2.0;
        return colorFromRatio(ratio, true);
    }

    public static Style colorFromRatio(double ratio, boolean oneIsGreen) {
        double p = ratio;

        if (!oneIsGreen) {
            p = 1 - p;
        }

        int r = (int) (255d * (Math.clamp(2 - 2 * p, 0, 1)));
        int g = (int) (255d * (Math.clamp(2 * p, 0, 1)));
        int rgb = 0xFF000000 + (r << 16) + (g << 8);

        return Style.EMPTY.withItalic(false).withColor(TextColor.fromRgb(rgb));
    }
}
