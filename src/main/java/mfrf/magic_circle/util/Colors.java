package mfrf.magic_circle.util;

import java.awt.*;

public class Colors {
    public static final Colors YIN = new Colors(255, 255, 255);
    public static final Colors YANG = new Colors(0, 0, 0);
    public static final Colors LIHUO = new Colors(255, 0, 0);
    public static final Colors KANSHUI = new Colors(0, 0, 255);
    public static final Colors DRYSKY = new Colors(0, 255, 255);
    public static final Colors KUNDI = new Colors(0, 255, 0);
    public static final Colors SUNDAE = new Colors(255, 255, 0);
    public static final Colors THUNDER = new Colors(160, 165, 255);
    public static final Colors GENSHAN = new Colors(255, 0, 255);
    public static final Colors DUIZE = new Colors(226, 255, 66);


    private final Color color;

    Colors(int r, int g, int b) {
        this.color = new Color(r, g, b);
    }

    public static Color getGradients(Color color1, Color color2, int points, boolean enableAlpha) {
        int rg = (color2.getRed() - color1.getRed()) / points;
        int gg = (color2.getGreen() - color1.getGreen()) / points;
        int bg = (color2.getBlue() - color1.getBlue()) / points;
        if (enableAlpha) {
            int ag = (color2.getAlpha() - color1.getAlpha()) / points;
            return new Color(rg, gg, bg, ag);
        }
        return new Color(rg, gg, bg);
    }

    public Color gradientsTo(Color color, int points, boolean enableAlpha) {
        return getGradients(this.color, color, points, enableAlpha);
    }

    public Color getColor() {
        return color;
    }

    public static Color mix(final float drysky, final float sundae, final float kanshui, final float genshan, final float kundi, final float thunder, final float lihuo, final float duize, final float yin, final float yang) {
        int red = Math.round(DRYSKY.color.getRed() * drysky + SUNDAE.color.getRed() * sundae + KANSHUI.color.getRed() * kanshui + GENSHAN.color.getRed() * genshan + KUNDI.color.getRed() * kundi + THUNDER.color.getRed() * thunder + LIHUO.color.getRed() * lihuo + DUIZE.color.getRed() * duize + YIN.color.getRed() * yin + YANG.color.getRed() * yang) % 255;
        int green = Math.round(DRYSKY.color.getGreen() * drysky + SUNDAE.color.getGreen() * sundae + KANSHUI.color.getGreen() * kanshui + GENSHAN.color.getGreen() * genshan + KUNDI.color.getGreen() * kundi + THUNDER.color.getGreen() * thunder + LIHUO.color.getGreen() * lihuo + DUIZE.color.getGreen() * duize + YIN.color.getGreen() * yin + YANG.color.getGreen() * yang) % 255;
        int blue = Math.round(DRYSKY.color.getBlue() * drysky + SUNDAE.color.getBlue() * sundae + KANSHUI.color.getBlue() * kanshui + GENSHAN.color.getBlue() * genshan + KUNDI.color.getBlue() * kundi + THUNDER.color.getBlue() * thunder + LIHUO.color.getBlue() * lihuo + DUIZE.color.getBlue() * duize + YIN.color.getBlue() * yin + YANG.color.getBlue() * yang) % 255;
        return new Color(red, green, blue);
    }

    public Colors add(int r, int g, int b) {
        int red = (color.getRed() + r) % 255;
        int green = (color.getGreen() + r) % 255;
        int blue = (color.getBlue() + r) % 255;
        return new Colors(red, green, blue);
    }

    public Color toAWT() {
        return color;
    }
}
