package mfrf.magic_circle.json_recipe_configs.research_test;

import com.google.common.base.Charsets;
import com.google.gson.JsonObject;
import mfrf.magic_circle.registry_lists.JsonConfigs;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class MathResearchTest extends ResearchTestBase {

    public MathResearchTest(ResourceLocation id, AbstractSerializer.DataContainer container) {
        super(id, container);
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return new Serializer();
    }

    @Override
    public IRecipeType<?> getType() {
        return JsonConfigs.Type.MATH_RESEARCH_TEST_JSONCONFIG_TYPE;
    }

    public static class Serializer extends AbstractSerializer<MathResearchTest> {

        @Override
        public MathResearchTest fromJson(ResourceLocation resourceLocation, JsonObject jsonObject) {
            DataContainer dataContainer = new DataContainer(jsonObject);
            return new MathResearchTest(resourceLocation, dataContainer);
        }

        @Nullable
        @Override
        public MathResearchTest fromNetwork(ResourceLocation resourceLocation, PacketBuffer packetBuffer) {
            DataContainer dataContainer = new DataContainer(packetBuffer);
            return new MathResearchTest(resourceLocation, dataContainer);
        }

        @Override
        public void toNetwork(PacketBuffer packetBuffer, MathResearchTest researchTestBase) {
            super.toNetwork(packetBuffer, researchTestBase);
        }
    }

}