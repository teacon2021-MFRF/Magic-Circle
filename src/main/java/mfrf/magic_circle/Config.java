package mfrf.magic_circle;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    public static ForgeConfigSpec CONFIG;
    public static ForgeConfigSpec.ConfigValue<Float> CURVE_PRECISION;
    public static ForgeConfigSpec.ConfigValue<Float> POLYGONS_RENDERING_SPEED;

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

        CONFIG_BUILDER.pop();

        CONFIG = CONFIG_BUILDER.build();
    }


}
