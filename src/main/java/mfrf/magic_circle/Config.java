package mfrf.magic_circle;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    public static ForgeConfigSpec CONFIG;
    public static ForgeConfigSpec.ConfigValue<Float> CURVE_PRECISION;
    public static ForgeConfigSpec.ConfigValue<Float> POLYGONS_RENDERING_SPEED;
    public static ForgeConfigSpec.ConfigValue<Integer> DURABILITY_OF_MAGES_ARMOR;
    public static ForgeConfigSpec.ConfigValue<Float> MANA_CAPACITY_OF_MAGES_ARMOR;
    public static ForgeConfigSpec.ConfigValue<Integer> COMPLEXITY_CEILING_OF_MAGES_ARMOR;
    public static ForgeConfigSpec.ConfigValue<Float> MANA_RECOVERY_OF_MAGES_ARMOR;
    public static ForgeConfigSpec.ConfigValue<Float> MANA_CAPACITY_SCALE_OF_PRIMED_MAGES_ARMOR;
    public static ForgeConfigSpec.ConfigValue<Float> MANA_RECOVERY_SCALE_OF_PRIMED_MAGES_ARMOR;
    public static ForgeConfigSpec.ConfigValue<Float> TOUGHNESS_OF_MAGES_ARMOR;
    public static ForgeConfigSpec.ConfigValue<Float> KNOCKBACK_RESISTANCE_OF_MAGES_ARMOR;

    static {
        ForgeConfigSpec.Builder CONFIG_BUILDER = new ForgeConfigSpec.Builder();

        //======================================================================================================================
        CONFIG_BUILDER.comment("Animation settings").push("animation");
        CURVE_PRECISION = CONFIG_BUILDER.comment(
                "This float value is the Precision of the line in the magic circle.",
                "make it too low may cause rendering laggy").define("precision_of_bezier_curve", 0.05f);

        POLYGONS_RENDERING_SPEED = CONFIG_BUILDER.comment(
                "This float value is the \"rendering speed\" of the polygons in the magic circle",
                "it means time use of each line",
                "Polygons include circle.",
                "This is calculus,brother."
        ).define("speed_of_rendering_polygons", 1f);
//        RGB_RED_STEP = CONFIG_BUILDER.comment(
//                "Red color gradients each step.",
//                "R!G!B!"
//        ).defineInRange("red_gradient_step", 0.03f, 0f, 1f);
//        RGB_GREEN_STEP = CONFIG_BUILDER.comment(
//                "Green color gradients each step.",
//                "R!G!B!"
//        ).defineInRange("green_gradient_step", 0.07f, 0f, 1f);
//        RGB_BLUE_STEP = CONFIG_BUILDER.comment(
//                "Blue color gradients each step.",
//                "R!G!B!"
//        ).defineInRange("blue_gradient_step", 0.11f, 0f, 1f);
//        RGB_ALPHA_STEP = CONFIG_BUILDER.comment(
//                "Alpha gradients each step.",
//                "R!G!B!"
//        ).defineInRange("alpha_gradient_step", 0.04f, 0, 1f);
        CONFIG_BUILDER.pop();
        //=======================================================================================================================

        //=======================================================================================================================
        CONFIG_BUILDER.comment("Equipment settings").push("equipment");

        CONFIG_BUILDER.comment("Armor settings").push("armor");

        DURABILITY_OF_MAGES_ARMOR = CONFIG_BUILDER.comment("Durability of Mage 's Armor", "Integer").define("durability_of_mages_armor", 240);
        MANA_CAPACITY_OF_MAGES_ARMOR = CONFIG_BUILDER.comment("Mana capacity of Mage 's Armor", "Rational number").define("mana_capacity_of_mages_armor", 200f);
        COMPLEXITY_CEILING_OF_MAGES_ARMOR = CONFIG_BUILDER.comment("Complexity celling of Mage 's Armor", "Integer").define("complexity_celling_of_mages_armor", 30);
        MANA_RECOVERY_OF_MAGES_ARMOR = CONFIG_BUILDER.comment("Mana recovery of Mage 's Armor", "Rational number").define("mana_recovery_of_mages_armor", 15.0f);
        MANA_CAPACITY_SCALE_OF_PRIMED_MAGES_ARMOR = CONFIG_BUILDER.comment("Mana capacity scale of primed Mage 's Armor", "Rational number").define("mana_capacity_scale_of_primed_mages_armor", 1.8f);
        MANA_RECOVERY_SCALE_OF_PRIMED_MAGES_ARMOR = CONFIG_BUILDER.comment("Mana recovery scale of Mage 's Armor", "Rational number").define("mana_recovery_scale_of_primed_mages_armor", 2.0f);
        TOUGHNESS_OF_MAGES_ARMOR = CONFIG_BUILDER.comment("Toughness of Mage 's Armor", "Rational number").define("Toughness_of_mages_armor", 1.0f);
        KNOCKBACK_RESISTANCE_OF_MAGES_ARMOR = CONFIG_BUILDER.comment("Knockback resistance of Mage 's Armor", "Rational number").define("knockback_resistance_of_mages_armor", 1.0f);

        CONFIG_BUILDER.pop();

        CONFIG_BUILDER.pop();
        //=======================================================================================================================

        CONFIG = CONFIG_BUILDER.build();
    }


}
