package mfrf.magic_circle.registry_lists;

import mfrf.magic_circle.MagicCircle;
import mfrf.magic_circle.json_recipe_configs.JsonConfigGemEffect;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class JsonConfigs {
    public static final DeferredRegister<IRecipeSerializer<?>> JSONCONFIG_S_REGISTER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MagicCircle.MOD_ID);
    public static final RegistryObject<IRecipeSerializer<JsonConfigGemEffect>> GEN_EFFECT = JSONCONFIG_S_REGISTER.register("gen_effect", JsonConfigGemEffect.Searlizer::new);

    public static class Type {
        public static final IRecipeType<JsonConfigGemEffect> GEN_EFFECT_JSONCONFIG_TYPE = IRecipeType.register(MagicCircle.MOD_ID + "gem_effect");

    }
}
