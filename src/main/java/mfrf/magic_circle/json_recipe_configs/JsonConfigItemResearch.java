package mfrf.magic_circle.json_recipe_configs;

import com.google.common.base.Charsets;
import com.google.gson.JsonObject;
import com.mojang.realmsclient.util.JsonUtils;
import mfrf.magic_circle.registry_lists.JsonConfigs;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class JsonConfigItemResearch extends JsonConfigBase {

    protected Ingredient ingredient;
    protected int baguaKnowledge;
    protected int mathKnowledge;
    protected int mysteryKnowledge;
    protected int physicalKnowledge;
    protected int requiredBaguaKnowledge;
    protected int requiredMathKnowledge;
    protected int requiredMysteryKnowledge;
    protected int requiredPhysicalKnowledge;
    protected boolean repeatable;
    protected String researchContain;

    public JsonConfigItemResearch(ResourceLocation id, Ingredient ingredient, int baguaKnowledge, int mathKnowledge, int mysteryKnowledge, int physicalKnowledge, int requiredBaguaKnowledge, int requiredMathKnowledge, int requiredMysteryKnowledge, int requiredPhysicalKnowledge, boolean repeatable, String researchContain) {
        super(id);
        this.ingredient = ingredient;
        this.baguaKnowledge = baguaKnowledge;
        this.mathKnowledge = mathKnowledge;
        this.mysteryKnowledge = mysteryKnowledge;
        this.physicalKnowledge = physicalKnowledge;
        this.requiredBaguaKnowledge = requiredBaguaKnowledge;
        this.requiredMathKnowledge = requiredMathKnowledge;
        this.requiredMysteryKnowledge = requiredMysteryKnowledge;
        this.requiredPhysicalKnowledge = requiredPhysicalKnowledge;
        this.repeatable = repeatable;
        this.researchContain = researchContain;
    }

    @Override
    public ItemStack getResultItem() {
        return ingredient.getItems()[0];
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return new Serializer();
    }

    @Override
    public IRecipeType<?> getType() {
        return JsonConfigs.Type.ITEM_RESEARCH_JSONCONFIG_TYPE;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<JsonConfigItemResearch> {

        @Override
        public JsonConfigItemResearch fromJson(ResourceLocation p_199425_1_, JsonObject p_199425_2_) {
            Ingredient ingredient = Ingredient.fromJson(p_199425_2_.get("item_or_tag"));
            int bagua_knowledge = JsonUtils.getIntOr("bagua_knowledge", p_199425_2_, 0);
            int math_knowledge = JsonUtils.getIntOr("math_knowledge", p_199425_2_, 0);
            int mystery_knowledge = JsonUtils.getIntOr("mystery_knowledge", p_199425_2_, 0);
            int physical_knowledge = JsonUtils.getIntOr("physical_knowledge", p_199425_2_, 0);
            int required_bagua_knowledge = JsonUtils.getIntOr("required_bagua_knowledge", p_199425_2_, 0);
            int required_math_knowledge = JsonUtils.getIntOr("required_math_knowledge", p_199425_2_, 0);
            int required_mystery_knowledge = JsonUtils.getIntOr("required_mystery_knowledge", p_199425_2_, 0);
            int required_physical_knowledge = JsonUtils.getIntOr("required_physical_knowledge", p_199425_2_, 0);
            boolean repeatable = JsonUtils.getBooleanOr("repeatable", p_199425_2_, false);
            String research_contain = JsonUtils.getStringOr("research_contain", p_199425_2_, "");
            return new JsonConfigItemResearch(p_199425_1_, ingredient, bagua_knowledge, math_knowledge, mystery_knowledge, physical_knowledge, required_bagua_knowledge, required_math_knowledge, required_mystery_knowledge, required_physical_knowledge, repeatable, research_contain);
        }

        @Override
        public JsonConfigItemResearch fromNetwork(ResourceLocation location, PacketBuffer buffer) {
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            int baguaKnowledge = buffer.readInt();
            int mathKnowledge = buffer.readInt();
            int mysteryKnowledge = buffer.readInt();
            int physicalKnowledge = buffer.readInt();
            int requiredBaguaKnowledge = buffer.readInt();
            int requiredMathKnowledge = buffer.readInt();
            int requiredMysteryKnowledge = buffer.readInt();
            int requiredPhysicalKnowledge = buffer.readInt();
            boolean repeatable = buffer.readBoolean();
            String researchContain = "";
            int length = buffer.readInt();
            if (length > 0) {
                researchContain = buffer.readCharSequence(length, Charsets.UTF_8).toString();
            }
            return new JsonConfigItemResearch(location, ingredient, baguaKnowledge, mathKnowledge, mysteryKnowledge, physicalKnowledge, requiredBaguaKnowledge, requiredMathKnowledge, requiredMysteryKnowledge, requiredPhysicalKnowledge, repeatable, researchContain);
        }

        @Override
        public void toNetwork(PacketBuffer buffer, JsonConfigItemResearch jsonConfigItemResearch) {
            jsonConfigItemResearch.ingredient.toNetwork(buffer);
            buffer.writeInt(jsonConfigItemResearch.baguaKnowledge);
            buffer.writeInt(jsonConfigItemResearch.mathKnowledge);
            buffer.writeInt(jsonConfigItemResearch.mysteryKnowledge);
            buffer.writeInt(jsonConfigItemResearch.physicalKnowledge);
            buffer.writeInt(jsonConfigItemResearch.requiredBaguaKnowledge);
            buffer.writeInt(jsonConfigItemResearch.requiredMathKnowledge);
            buffer.writeInt(jsonConfigItemResearch.requiredMysteryKnowledge);
            buffer.writeInt(jsonConfigItemResearch.requiredPhysicalKnowledge);
            buffer.writeBoolean(jsonConfigItemResearch.repeatable);
            int length = jsonConfigItemResearch.researchContain.length();
            buffer.writeInt(length);
            if (length > 0) buffer.writeCharSequence(jsonConfigItemResearch.researchContain, Charsets.UTF_8);
        }

    }

}
