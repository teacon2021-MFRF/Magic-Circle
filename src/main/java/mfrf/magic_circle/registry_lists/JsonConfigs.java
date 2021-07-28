package mfrf.magic_circle.registry_lists;

import mfrf.magic_circle.MagicCircle;
import mfrf.magic_circle.json_recipe_configs.JsonConfigGemEffect;
import mfrf.magic_circle.json_recipe_configs.JsonConfigItemResearch;
import mfrf.magic_circle.json_recipe_configs.research_test.ResearchTestBase;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class JsonConfigs {
    public static final DeferredRegister<IRecipeSerializer<?>> JSONCONFIG_REGISTER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MagicCircle.MOD_ID);
    
    public static final RegistryObject<IRecipeSerializer<JsonConfigGemEffect>> GEN_EFFECT = JSONCONFIG_REGISTER.register("gem_effect", JsonConfigGemEffect.Searlizer::new);
    public static final RegistryObject<IRecipeSerializer<ResearchTestBase>> RESEARCH_TEST = JSONCONFIG_REGISTER.register("research_test", ResearchTestBase.Serializer::new);
    public static final RegistryObject<IRecipeSerializer<JsonConfigItemResearch>> ITEM_RESEARCH_TEST = JSONCONFIG_REGISTER.register("item_research", JsonConfigItemResearch.Serializer::new);

    public static class Type {
        public static final IRecipeType<JsonConfigGemEffect> GEN_EFFECT_JSONCONFIG_TYPE = IRecipeType.register(MagicCircle.MOD_ID + "gem_effect");
        public static final IRecipeType<ResearchTestBase> RESEARCH_TEST_JSONCONFIG_TYPE = IRecipeType.register(MagicCircle.MOD_ID + "research_test");
        public static final IRecipeType<JsonConfigItemResearch> ITEM_RESEARCH_JSONCONFIG_TYPE = IRecipeType.register(MagicCircle.MOD_ID + "item_research");

    }
}
