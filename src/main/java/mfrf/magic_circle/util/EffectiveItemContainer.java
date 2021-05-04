package mfrf.magic_circle.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.Tag;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import java.util.Map;

public class EffectiveItemContainer implements Cloneable, IItemHandler {
    private int slotCount;
    private Slot[] Slots;

    public EffectiveItemContainer(Slot[] Slots) {
        this.slotCount = Slots.length;
        this.Slots = new Slot[slotCount];
        for (int i = 0; i < slotCount; i++) {
            this.Slots[i] = Slots[i].clone();
        }
    }

    public EffectiveItemContainer() {
    }

    //todo caninsert,insert,extract.etc

    public CompoundNBT serializeNBT() {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putInt("slot_count", slotCount);
        for (int i = 0; i < slotCount; i++) {
            Slot slot = Slots[i];
            CompoundNBT slotNBT = new CompoundNBT();
            slotNBT.putInt("max_complexity", slot.getMaxComplexity());
            slotNBT.put("itemstack", slot.itemStack.serializeNBT());
            compoundNBT.put("slot" + i, slotNBT);
        }
        return compoundNBT;
    }

    public void deserializeNBT(CompoundNBT compoundNBT) {
        int slot_count = compoundNBT.getInt("slot_count");
        Slots = new Slot[slot_count];
        for (int i = 0; i < slot_count; i++) {
            CompoundNBT compound = compoundNBT.getCompound("slot" + i);
            int max_complexity = compound.getInt("max_complexity");
            ItemStack itemstack = ItemStack.read(compound.getCompound("itemstack"));
            Slots[i] = new Slot(max_complexity, itemstack);
        }
    }

    @Override
    protected EffectiveItemContainer clone() {
        return new EffectiveItemContainer(Slots);
    }

    @Override
    public int getSlots() {
        return slotCount;
    }

    @Nonnull
    @Override
    public ItemStack getStackInSlot(int slot) {
        return Slots[slot].itemStack;
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if (isItemValid(slot, stack)) {
            if (simulate) {
                ItemStack copy = stack.copy();
                copy.shrink(1);
                return copy;
            }
        }
        return stack;
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (simulate) {
            return Slots[slot].itemStack.copy();
        } else {
            ItemStack copy = Slots[slot].itemStack.copy();
            Slots[slot].itemStack = ItemStack.EMPTY;
            return copy;
        }
    }

    @Override
    public int getSlotLimit(int slot) {
        return 1;
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        Slot slot1 = Slots[slot];
        if (slot1.itemStack.isEmpty()) {
            for (Map.Entry<Tag<Item>, Utils.Properties> entry : Utils.EFFECT_MAP.entrySet()) {
                Tag<Item> tag = entry.getKey();
                Utils.Properties properties = entry.getValue();
                if (tag.contains(stack.getItem()) && properties.complexity <= slot1.maxComplexity) {
                    return true;
                }
            }
        }
        return false;
    }

    public static class Slot implements Cloneable {
        private final int maxComplexity;
        public ItemStack itemStack = ItemStack.EMPTY;

        public Slot(int maxComplexity, ItemStack itemStack) {
            this.maxComplexity = maxComplexity;
            this.itemStack = itemStack;
        }

        public int getMaxComplexity() {
            return maxComplexity;
        }

        @Override
        protected Slot clone() {
            return new Slot(maxComplexity, itemStack.copy());
        }
    }
}
