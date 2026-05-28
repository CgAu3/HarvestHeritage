package me.theabab2333.harvestheritage.util;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;

public class StyelUtil {

    public static Style colorFromRatio() {
        return colorFromRatio(getRatio(50), true);
    }

    public static Style colorFromRatio(double speed) {
        return colorFromRatio(getRatio(speed), true);
    }

    private static Style colorFromRatio(double ratio, boolean oneIsGreen) {
        double p = ratio;

        if (!oneIsGreen) {
            p = 1 - p;
        }

        int r = (int) (255d * (Math.clamp(2 - 2 * p, 0, 1)));
        int g = (int) (255d * (Math.clamp(2 * p, 0, 1)));
        int rgb = 0xFF000000 + (r << 16) + (g << 8);

        return Style.EMPTY.withItalic(false).withColor(TextColor.fromRgb(rgb));
    }

    public static Style charByCharStyle(int startRgb, int endRgb) {
        float t = (float) getRatio(50);
        return Style.EMPTY.withColor(TextColor.fromRgb(lerpColor(startRgb, endRgb, t)));
    }

    public static MutableComponent charByCharComponent(Component text, int startRgb, int endRgb) {
        return charByCharComponent(text.getString(), startRgb, endRgb);
    }

    public static MutableComponent charByCharComponent(Component text, int startRgb, int endRgb, double speed) {
        return charByCharComponent(text.getString(), startRgb, endRgb, speed);
    }

    private static MutableComponent charByCharComponent(String text, int startRgb, int endRgb) {
        return charByCharComponent(text, startRgb, endRgb, 20);
    }

    private static MutableComponent charByCharComponent(String text, int startRgb, int endRgb, double speed) {
        double phase = getRatio(speed);
        MutableComponent result = Component.empty();
        for (int i = 0; i < text.length(); i++) {
            float t = text.length() <= 1 ? 0 : (float) ((double) i / (text.length() - 1) + phase) % 1.0f;
            result.append(Component.literal(String.valueOf(text.charAt(i)))
                .withStyle(Style.EMPTY.withColor(TextColor.fromRgb(lerpColor(startRgb, endRgb, t)))));
        }
        return result;
    }

    private static int lerpColor(int start, int end, float t) {
        int r = (int) (lerp((start >> 16) & 0xFF, (end >> 16) & 0xFF, t));
        int g = (int) (lerp((start >> 8) & 0xFF, (end >> 8) & 0xFF, t));
        int b = (int) (lerp(start & 0xFF, end & 0xFF, t));
        return 0xFF000000 | (r << 16) | (g << 8) | b;
    }

    private static float lerp(float a, float b, float t) {
        return a + (b - a) * t;
    }

    private static double getRatio(double speed) {
        return (Math.sin(System.currentTimeMillis() * Math.PI / (speed * 50)) + 1.0) / 2.0;
    }
}
