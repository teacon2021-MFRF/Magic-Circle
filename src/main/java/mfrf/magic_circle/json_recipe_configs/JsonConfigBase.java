package mfrf.magic_circle.json_recipe_configs;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public abstract class JsonConfigBase implements IRecipe<RecipeWrapper> {
    protected final ResourceLocation id;

    public JsonConfigBase(ResourceLocation id) {
        this.id = id;
    }

    @Override
    public boolean matches(RecipeWrapper p_77569_1_, World p_77569_2_) {
        return false;
    }

    @Override
    public ItemStack assemble(RecipeWrapper p_77572_1_) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int p_194133_1_, int p_194133_2_) {
        return false;
    }

    @Override
    public abstract ItemStack getResultItem();

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public abstract IRecipeSerializer<?> getSerializer();

    @Override
    public abstract IRecipeType<?> getType();

}
