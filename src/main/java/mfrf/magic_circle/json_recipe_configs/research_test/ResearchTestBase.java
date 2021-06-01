package mfrf.magic_circle.json_recipe_configs.research_test;

import com.google.common.base.Charsets;
import com.google.gson.JsonObject;
import mfrf.magic_circle.json_recipe_configs.JsonConfigBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public abstract class ResearchTestBase extends JsonConfigBase {

    protected float difficulty;
    protected String description;//todo resolve image path
    protected String answer;
    protected String research;

    public ResearchTestBase(ResourceLocation id, AbstractSerializer.DataContainer container) {
        super(id);
        difficulty = container.difficulty;
        description = container.description;
        answer = container.answer;
        research = container.research;
    }

    @Override
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    @Override
    public abstract IRecipeSerializer<?> getSerializer();


    @Override
    public abstract IRecipeType<?> getType();


    public static abstract class AbstractSerializer<T extends ResearchTestBase> extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<T> {

        @Override
        public abstract T fromJson(ResourceLocation resourceLocation, JsonObject jsonObject);

        @Nullable
        @Override
        public abstract T fromNetwork(ResourceLocation resourceLocation, PacketBuffer packetBuffer);


        @Override
        public void toNetwork(PacketBuffer packetBuffer, T researchTestBase) {
            packetBuffer.writeFloat(researchTestBase.difficulty);
            packetBuffer.writeInt(researchTestBase.description.length());
            packetBuffer.writeCharSequence(researchTestBase.description, Charsets.UTF_8);
            packetBuffer.writeInt(researchTestBase.answer.length());
            packetBuffer.writeCharSequence(researchTestBase.answer, Charsets.UTF_8);
            packetBuffer.writeInt(researchTestBase.research.length());
            packetBuffer.writeCharSequence(researchTestBase.research, Charsets.UTF_8);
        }

        protected static class DataContainer {
            public final float difficulty;
            public final String description;
            public final String answer;
            public final String research;

            public DataContainer(JsonObject object) {
                difficulty = object.get("difficulty").getAsFloat();
                description = object.get("description").getAsString();
                answer = object.get("answer").getAsString();
                research = object.get("research").getAsString();
            }

            public DataContainer(PacketBuffer buffer) {
                this.difficulty = buffer.readFloat();
                this.description = buffer.readCharSequence(buffer.readInt(), Charsets.UTF_8).toString();
                this.answer = buffer.readCharSequence(buffer.readInt(), Charsets.UTF_8).toString();
                this.research = buffer.readCharSequence(buffer.readInt(), Charsets.UTF_8).toString();
            }

        }
    }
}
