package mfrf.magic_circle.json_recipe_configs;

import com.google.gson.JsonObject;

import mfrf.magic_circle.magicutil.datastructure.MagicNodePropertyMatrix8By8;
import mfrf.magic_circle.magicutil.datastructure.MagicNodePropertyMatrix8By8.INDEX;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class JsonConfigGemEffect extends JsonConfigBase {

    protected final Ingredient ingredient;
    protected final MagicNodePropertyMatrix8By8 eigenMatrix;
    protected final float manaRecoveryScale;
    protected final float manaCapacityScale;

    public JsonConfigGemEffect(ResourceLocation id, Ingredient ingredient, MagicNodePropertyMatrix8By8 eigenMatrix, float manaRecoveryScale, float manCapacityScale) {
        super(id);
        this.ingredient = ingredient;
        this.eigenMatrix = eigenMatrix;
        this.manaRecoveryScale = manaRecoveryScale;
        this.manaCapacityScale = manCapacityScale;
    }

    @Override
    public ItemStack getResultItem() {
        return ingredient.getItems()[0];
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IRecipeType<?> getType() {
        // TODO Auto-generated method stub
        return null;
    }

    public static class Searlizer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<JsonConfigGemEffect> {

        @Override
        public JsonConfigGemEffect fromJson(ResourceLocation p_199425_1_, JsonObject p_199425_2_) {
            Ingredient ingredient = Ingredient.fromJson(p_199425_2_.get("item_or_tag"));

            MagicNodePropertyMatrix8By8 eigenMatrix = MagicNodePropertyMatrix8By8.IDENTITY.copy();
            INDEX[] values = MagicNodePropertyMatrix8By8.INDEX.values();

            for (INDEX index : values) {
                String name = index.name().toLowerCase();
                if (p_199425_2_.has(name)) {
                    eigenMatrix.set(index, p_199425_2_.get(name).getAsFloat());
                } else {
                    eigenMatrix.set(index, index == INDEX.RED || index == INDEX.GREEN || index == INDEX.BLUE ? 0 : 1);
                }
            }

            float manaRecoveryScale = p_199425_2_.has("mana_recovery_scale") ? p_199425_2_.get("mana_recovery_scale").getAsFloat() : 1;
            float manaCapacityScale = p_199425_2_.has("mana_capacity_scale") ? p_199425_2_.get("mana_capacity_scale").getAsFloat() : 1;

            return new JsonConfigGemEffect(p_199425_1_, ingredient, eigenMatrix, manaRecoveryScale, manaCapacityScale);
        }

        @Override
        public JsonConfigGemEffect fromNetwork(ResourceLocation p_199426_1_, PacketBuffer p_199426_2_) {
            double[] data = new double[64];

            for (int i = 0; i < 64; i++) {
                data[i] = p_199426_2_.readDouble();
            }
            float recoveryScale = p_199426_2_.readFloat();
            float capacityScale = p_199426_2_.readFloat();

            Ingredient ingredient = Ingredient.fromNetwork(p_199426_2_);

            return new JsonConfigGemEffect(p_199426_1_, ingredient, new MagicNodePropertyMatrix8By8(data), recoveryScale, capacityScale);
        }

        @Override
        public void toNetwork(PacketBuffer p_199427_1_, JsonConfigGemEffect p_199427_2_) {
            double[] data = p_199427_2_.eigenMatrix.data;

            for (int i = 0; i < 64; i++) {
                p_199427_1_.writeDouble(data[i]);
            }

            p_199427_1_.writeFloat(p_199427_2_.manaRecoveryScale);
            p_199427_1_.writeFloat(p_199427_2_.manaCapacityScale);

            p_199427_2_.ingredient.toNetwork(p_199427_1_);

        }

    }

}
