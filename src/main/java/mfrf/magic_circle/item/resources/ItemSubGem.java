package mfrf.magic_circle.item.resources;

import mfrf.magic_circle.item.ItemBase;
import mfrf.magic_circle.util.MagicalItemContainer;
import mfrf.magic_circle.util.MagicalItemSimpleImplement;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;

public class ItemSubGem extends ItemBase {
    public static final MagicalItemSimpleImplement DEFAULT_INSTANCE = new MagicalItemSimpleImplement(new MagicalItemContainer(), 200f, 1.1, 0, 1f, 1.1, 3, ItemStack.EMPTY);

    public ItemSubGem(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return DEFAULT_INSTANCE.copy(stack, nbt);
    }
}
