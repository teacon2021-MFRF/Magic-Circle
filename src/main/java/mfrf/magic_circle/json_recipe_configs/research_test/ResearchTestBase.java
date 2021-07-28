package mfrf.magic_circle.json_recipe_configs.research_test;

import com.google.gson.JsonObject;
import mfrf.magic_circle.json_recipe_configs.JsonConfigBase;
import mfrf.magic_circle.registry_lists.JsonConfigs;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

public class ResearchTestBase extends JsonConfigBase {

    protected float difficulty;
    protected String figure;
    protected String answer; //regex
    protected String research; //researchName

    public ResearchTestBase(ResourceLocation id, Serializer.DataContainer container) {
        super(id);
        difficulty = container.difficulty;
        figure = container.figure;
        answer = container.answer.pattern();
        research = container.research;
    }


    @Override
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return JsonConfigs.RESEARCH_TEST.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return JsonConfigs.Type.RESEARCH_TEST_JSONCONFIG_TYPE;
    }

    public float getDifficulty() {
        return difficulty;
    }

    public String getFigure() {
        return figure;
    }

    public String getAnswer() {
        return answer;
    }

    public String getResearch() {
        return research;
    }

    public Serializer.DataContainer toDataContainer() {
        return new Serializer.DataContainer(difficulty, figure, answer, research);
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<ResearchTestBase> {

        @Override
        public ResearchTestBase fromJson(ResourceLocation resourceLocation, JsonObject jsonObject) {
            return new ResearchTestBase(resourceLocation, new DataContainer(jsonObject));
        }

        @Nullable
        @Override
        public ResearchTestBase fromNetwork(ResourceLocation resourceLocation, PacketBuffer packetBuffer) {
            return new ResearchTestBase(resourceLocation, new DataContainer(packetBuffer));
        }

        public static DataContainer fromNBT(CompoundNBT compoundNBT) {
            float difficulty = compoundNBT.getFloat("difficulty");
            String figure = compoundNBT.getString("figure");
            String answer = new String(compoundNBT.getByteArray("answer"));
            String research = new String(compoundNBT.getByteArray("research"), StandardCharsets.UTF_8);
            return new DataContainer(difficulty, figure, answer, research);
        }

        public CompoundNBT toNBT(DataContainer researchTestBase) {
            CompoundNBT compoundNBT = new CompoundNBT();
            compoundNBT.putFloat("difficulty", researchTestBase.difficulty);
            compoundNBT.putString("figure", researchTestBase.figure);
            compoundNBT.putByteArray("answer", researchTestBase.answer.pattern().getBytes(StandardCharsets.UTF_8));
            compoundNBT.putByteArray("research", researchTestBase.research.getBytes(StandardCharsets.UTF_8));
            return compoundNBT;
        }


        public CompoundNBT toNBT(ResearchTestBase researchTestBase) {
            CompoundNBT compoundNBT = new CompoundNBT();
            compoundNBT.putFloat("difficulty", researchTestBase.difficulty);
            compoundNBT.putString("figure", researchTestBase.figure);
            compoundNBT.putByteArray("answer", researchTestBase.answer.getBytes(StandardCharsets.UTF_8));
            compoundNBT.putByteArray("research", researchTestBase.research.getBytes(StandardCharsets.UTF_8));
            return compoundNBT;
        }

        @Override
        public void toNetwork(PacketBuffer packetBuffer, ResearchTestBase researchTestBase) {
            packetBuffer.writeFloat(researchTestBase.difficulty);
            packetBuffer.writeUtf(researchTestBase.figure);
            packetBuffer.writeUtf(researchTestBase.answer);
            packetBuffer.writeUtf(researchTestBase.research);
        }

        public static class DataContainer {
            public float difficulty;
            public String figure;
            public Pattern answer;
            public String research;

            public DataContainer(JsonObject object) {
                difficulty = object.get("difficulty").getAsFloat();
                figure = object.get("figure").getAsString();
                answer = Pattern.compile(object.get("answer").getAsString());
                research = object.get("research").getAsString();
            }

            public DataContainer(PacketBuffer buffer) {
                this.difficulty = buffer.readFloat();
                this.figure = buffer.readUtf(32767);
                this.answer = Pattern.compile(buffer.readUtf(32767));
                this.research = buffer.readUtf(32767);
            }

            public DataContainer(float difficulty, String figure, String answer, String research) {
                this.difficulty = difficulty;
                this.figure = figure;
                this.answer = Pattern.compile(answer);
                this.research = research;
            }
        }
    }
}
