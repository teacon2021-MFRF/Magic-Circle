package mfrf.magic_circle.json_recipe_configs.research_test;

import com.google.gson.JsonObject;
import mfrf.magic_circle.registry_lists.JsonConfigs;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class BaguaResearchTest extends ResearchTestBase {

    public BaguaResearchTest(ResourceLocation id, AbstractSerializer.DataContainer container) {
        super(id, container);
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return new Serializer();
    }

    @Override
    public IRecipeType<?> getType() {
        return JsonConfigs.Type.BAGUA_RESEARCH_TEST_JSONCONFIG_TYPE;
    }

    public static class Serializer extends AbstractSerializer<BaguaResearchTest> {

        @Override
        public BaguaResearchTest fromJson(ResourceLocation resourceLocation, JsonObject jsonObject) {
            DataContainer dataContainer = new DataContainer(jsonObject);
            return new BaguaResearchTest(resourceLocation, dataContainer);
        }

        @Nullable
        @Override
        public BaguaResearchTest fromNetwork(ResourceLocation resourceLocation, PacketBuffer packetBuffer) {
            DataContainer dataContainer = new DataContainer(packetBuffer);
            return new BaguaResearchTest(resourceLocation, dataContainer);
        }

        @Override
        public void toNetwork(PacketBuffer packetBuffer, BaguaResearchTest researchTestBase) {
            super.toNetwork(packetBuffer, researchTestBase);
        }
    }

}
