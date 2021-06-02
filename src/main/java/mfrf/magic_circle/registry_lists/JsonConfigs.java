package mfrf.magic_circle.registry_lists;

import mfrf.magic_circle.MagicCircle;
import mfrf.magic_circle.json_recipe_configs.JsonConfigGemEffect;
import mfrf.magic_circle.json_recipe_configs.JsonConfigItemResearch;
import mfrf.magic_circle.json_recipe_configs.research_test.*;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class JsonConfigs {
    public static final DeferredRegister<IRecipeSerializer<?>> JSONCONFIG_REGISTER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MagicCircle.MOD_ID);
    public static final RegistryObject<IRecipeSerializer<JsonConfigGemEffect>> GEN_EFFECT = JSONCONFIG_REGISTER.register("gen_effect", JsonConfigGemEffect.Searlizer::new);
    public static final RegistryObject<IRecipeSerializer<PhysicalResearchTest>> PHYSICAL_RESEARCH_TEST = JSONCONFIG_REGISTER.register("physical_research_test", PhysicalResearchTest.Serializer::new);
    public static final RegistryObject<IRecipeSerializer<MathResearchTest>> MATH_RESEARCH_TEST = JSONCONFIG_REGISTER.register("math_research_test", MathResearchTest.Serializer::new);
    public static final RegistryObject<IRecipeSerializer<BaguaResearchTest>> BAGUA_RESEARCH_TEST = JSONCONFIG_REGISTER.register("bagua_research_test", BaguaResearchTest.Serializer::new);
    public static final RegistryObject<IRecipeSerializer<MysteryResearchTest>> MYSTERY_RESEARCH_TEST = JSONCONFIG_REGISTER.register("mystery_research_test", MysteryResearchTest.Serializer::new);
    public static final RegistryObject<IRecipeSerializer<JsonConfigItemResearch>> ITEM_RESEARCH_TEST = JSONCONFIG_REGISTER.register("item_research", JsonConfigItemResearch.Serializer::new);

    public static class Type {
        public static final IRecipeType<JsonConfigGemEffect> GEN_EFFECT_JSONCONFIG_TYPE = IRecipeType.register(MagicCircle.MOD_ID + "gem_effect");
        public static final IRecipeType<PhysicalResearchTest> PHYSICAL_RESEARCH_TEST_JSONCONFIG_TYPE = IRecipeType.register(MagicCircle.MOD_ID + "physical_research_test");
        public static final IRecipeType<MathResearchTest> MATH_RESEARCH_TEST_JSONCONFIG_TYPE = IRecipeType.register(MagicCircle.MOD_ID + "math_research_test");
        public static final IRecipeType<BaguaResearchTest> BAGUA_RESEARCH_TEST_JSONCONFIG_TYPE = IRecipeType.register(MagicCircle.MOD_ID + "bagua_research_test");
        public static final IRecipeType<MysteryResearchTest> MYSTERY_RESEARCH_TEST_JSONCONFIG_TYPE = IRecipeType.register(MagicCircle.MOD_ID + "mystery_research_test");
        public static final IRecipeType<JsonConfigItemResearch> ITEM_RESEARCH_JSONCONFIG_TYPE = IRecipeType.register(MagicCircle.MOD_ID + "item_research");

    }
}
