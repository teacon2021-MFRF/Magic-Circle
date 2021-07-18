package mfrf.magic_circle.json_recipe_configs;

import com.google.common.base.Charsets;
import com.google.gson.JsonObject;
import mfrf.magic_circle.registry_lists.JsonConfigs;
import mfrf.magic_circle.util.Utils;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

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
    protected ResourceLocation questionLocation;
    protected String researchContain;

    public JsonConfigItemResearch(ResourceLocation id, Ingredient ingredient, int baguaKnowledge, int mathKnowledge, int mysteryKnowledge, int physicalKnowledge, int requiredBaguaKnowledge, int requiredMathKnowledge, int requiredMysteryKnowledge, int requiredPhysicalKnowledge, boolean repeatable, String researchContain, @Nullable ResourceLocation questionLocation) {
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
        this.questionLocation = questionLocation;
    }

    @Override
    public ItemStack getResultItem() {
        return ingredient.getItems()[0];
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public int getBaguaKnowledge() {
        return baguaKnowledge;
    }

    public int getMathKnowledge() {
        return mathKnowledge;
    }

    public int getMysteryKnowledge() {
        return mysteryKnowledge;
    }

    public int getPhysicalKnowledge() {
        return physicalKnowledge;
    }

    public int getRequiredBaguaKnowledge() {
        return requiredBaguaKnowledge;
    }

    public int getRequiredMathKnowledge() {
        return requiredMathKnowledge;
    }

    public int getRequiredMysteryKnowledge() {
        return requiredMysteryKnowledge;
    }

    public int getRequiredPhysicalKnowledge() {
        return requiredPhysicalKnowledge;
    }

    public boolean isRepeatable() {
        return repeatable;
    }

    public String getResearchContain() {
        return researchContain;
    }

    public ResourceLocation getQuestionLocation() {
        return questionLocation;
    }

    public boolean hasEnoughKnowledge(int mathKnowledge, int physicalKnowledge, int baguaKnowledge, int mysteryKnowledge) {
        return mathKnowledge >= requiredMathKnowledge && physicalKnowledge >= requiredPhysicalKnowledge && baguaKnowledge >= requiredBaguaKnowledge && mysteryKnowledge >= requiredMysteryKnowledge;
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
            Ingredient ingredient = Ingredient.fromJson(p_199425_2_.get("tag_or_item"));
            int bagua_knowledge = Utils.getIntOr("bagua_knowledge", p_199425_2_, 0);
            int math_knowledge = Utils.getIntOr("math_knowledge", p_199425_2_, 0);
            int mystery_knowledge = Utils.getIntOr("mystery_knowledge", p_199425_2_, 0);
            int physical_knowledge = Utils.getIntOr("physical_knowledge", p_199425_2_, 0);
            int required_bagua_knowledge = Utils.getIntOr("required_bagua_knowledge", p_199425_2_, 0);
            int required_math_knowledge = Utils.getIntOr("required_math_knowledge", p_199425_2_, 0);
            int required_mystery_knowledge = Utils.getIntOr("required_mystery_knowledge", p_199425_2_, 0);
            int required_physical_knowledge = Utils.getIntOr("required_physical_knowledge", p_199425_2_, 0);
            boolean repeatable = Utils.getBooleanOr("repeatable", p_199425_2_, false);
            String research_contain = Utils.getStringOr("research_contain", p_199425_2_, "");
            ResourceLocation questionLocation = null;
            if (p_199425_2_.has("question_location")) {
                questionLocation = ResourceLocation.tryParse(p_199425_2_.get("question_location").getAsString());
            }
            return new JsonConfigItemResearch(p_199425_1_, ingredient, bagua_knowledge, math_knowledge, mystery_knowledge, physical_knowledge, required_bagua_knowledge, required_math_knowledge, required_mystery_knowledge, required_physical_knowledge, repeatable, research_contain, questionLocation);
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
            researchContain = buffer.readUtf();
            ResourceLocation question_location = null;
            if (buffer.readBoolean()) {
                question_location = ResourceLocation.tryParse(buffer.readUtf());
            }
            return new JsonConfigItemResearch(location, ingredient, baguaKnowledge, mathKnowledge, mysteryKnowledge, physicalKnowledge, requiredBaguaKnowledge, requiredMathKnowledge, requiredMysteryKnowledge, requiredPhysicalKnowledge, repeatable, researchContain, question_location);
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
            buffer.writeUtf(jsonConfigItemResearch.researchContain);
            if (jsonConfigItemResearch.questionLocation != null) {
                buffer.writeBoolean(true);
                String s = jsonConfigItemResearch.questionLocation.toString();
                buffer.writeUtf(s);
            } else {
                buffer.writeBoolean(false);
            }
        }

    }

}
