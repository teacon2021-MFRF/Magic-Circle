package mfrf.magic_circle.json_recipe_configs.research_test;

import com.google.gson.JsonObject;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class MathResearchTest extends ResearchTestBase {
    protected MathSubtype subtype;

    public MathResearchTest(ResourceLocation id, AbstractSerializer.DataContainer container, MathSubtype subtype) {
        super(id, container);
        this.subtype = subtype;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return null;
    }

    @Override
    public IRecipeType<?> getType() {
        return null;
    }

    public static class Serializer extends AbstractSerializer<MathResearchTest> {

        @Override
        public MathResearchTest fromJson(ResourceLocation resourceLocation, JsonObject jsonObject) {
            DataContainer dataContainer = new DataContainer(jsonObject);
            MathSubtype subtype = MathSubtype.valueOf(jsonObject.get("subtype").getAsString().toUpperCase());
            return new MathResearchTest(resourceLocation, dataContainer, subtype);
        }

        @Nullable
        @Override
        public MathResearchTest fromNetwork(ResourceLocation resourceLocation, PacketBuffer packetBuffer) {
            return null;//todo thonk...
        }

        @Override
        public void toNetwork(PacketBuffer packetBuffer, MathResearchTest researchTestBase) {
            super.toNetwork(packetBuffer, researchTestBase);

        }
    }

    public enum MathSubtype {
        ;
    }
}
