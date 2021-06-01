package mfrf.magic_circle.json_recipe_configs.research_test;

import com.google.gson.JsonObject;
import mfrf.magic_circle.registry_lists.JsonConfigs;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class PhysicalResearchTest extends ResearchTestBase {

    public PhysicalResearchTest(ResourceLocation id, AbstractSerializer.DataContainer container) {
        super(id, container);
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return new Serializer();
    }

    @Override
    public IRecipeType<?> getType() {
        return JsonConfigs.Type.PHYSICAL_RESEARCH_TEST_JSONCONFIG_TYPE;
    }

    public static class Serializer extends AbstractSerializer<PhysicalResearchTest> {

        @Override
        public PhysicalResearchTest fromJson(ResourceLocation resourceLocation, JsonObject jsonObject) {
            DataContainer dataContainer = new DataContainer(jsonObject);
            return new PhysicalResearchTest(resourceLocation, dataContainer);
        }

        @Nullable
        @Override
        public PhysicalResearchTest fromNetwork(ResourceLocation resourceLocation, PacketBuffer packetBuffer) {
            DataContainer dataContainer = new DataContainer(packetBuffer);
            return new PhysicalResearchTest(resourceLocation, dataContainer);
        }

        @Override
        public void toNetwork(PacketBuffer packetBuffer, PhysicalResearchTest researchTestBase) {
            super.toNetwork(packetBuffer, researchTestBase);
        }
    }

}
