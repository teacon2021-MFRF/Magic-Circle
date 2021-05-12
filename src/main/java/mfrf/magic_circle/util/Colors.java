package mfrf.magic_circle.util;

import java.awt.*;
import java.util.ArrayList;

import static java.awt.Color.WHITE;

public enum Colors {
    BLACK(0, 0, 0), RED(255, 0, 0), YELLOW(255, 255, 0), GREEN(0, 255, 0), LAPS(0, 255, 255), BLUE(0, 0, 255), PURPLE(255, 0, 255), WHITE(255, 255, 255);

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
}
