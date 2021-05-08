package mfrf.magic_circle;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    public static ForgeConfigSpec CONFIG;
    public static ForgeConfigSpec.ConfigValue<Float> CURVE_PRECISION;
    public static ForgeConfigSpec.ConfigValue<Float> POLYGONS_RENDERING_SPEED;
    public static ForgeConfigSpec.ConfigValue<Integer> RGB_RED_STEP;
    public static ForgeConfigSpec.ConfigValue<Integer> RGB_GREEN_STEP;
    public static ForgeConfigSpec.ConfigValue<Integer> RGB_BLUE_STEP;
    public static ForgeConfigSpec.ConfigValue<Integer> RGB_ALPHA_STEP;

    static {
        ForgeConfigSpec.Builder CONFIG_BUILDER = new ForgeConfigSpec.Builder();

        CONFIG_BUILDER.comment("Animation settings").push("animation");
        CURVE_PRECISION = CONFIG_BUILDER.comment(
                "This float value is the Precision of the line in the magic circle.",
                "make it too low may cause rendering laggy").define("precision_of_bezier_curve", 0.05f);

        POLYGONS_RENDERING_SPEED = CONFIG_BUILDER.comment(
                "This float value is the \"rendering speed\" of the polygons in the magic circle",
                "Polygons include circle.",
                "This is calculus,brother."
        ).define("speed_of_rendering_polygons", 0.1f);
        RGB_RED_STEP = CONFIG_BUILDER.comment(
                "Red color gradients each step.",
                "R!G!B!"
        ).defineInRange("red_gradient_step", 3, 0, 255);
        RGB_GREEN_STEP = CONFIG_BUILDER.comment(
                "Green color gradients each step.",
                "R!G!B!"
        ).defineInRange("green_gradient_step", 7, 0, 255);
        RGB_BLUE_STEP = CONFIG_BUILDER.comment(
                "Blue color gradients each step.",
                "R!G!B!"
        ).defineInRange("blue_gradient_step", 11, 0, 255);
        RGB_ALPHA_STEP = CONFIG_BUILDER.comment(
                "Alpha gradients each step.",
                "R!G!B!"
        ).defineInRange("alpha_gradient_step", 4, 0, 255);

        CONFIG_BUILDER.pop();

        CONFIG = CONFIG_BUILDER.build();
    }


}
