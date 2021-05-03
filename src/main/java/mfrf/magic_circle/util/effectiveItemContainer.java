package mfrf.magic_circle.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class effectiveItemContainer {
    private int slotCount;
    private slot[] slots;

    public effectiveItemContainer(int slotCount) {
        this.slotCount = slotCount;
        this.slots = new slot[slotCount];
    }

    public effectiveItemContainer() {
    }

    //todo caninsert,insert,extract.etc

    public CompoundNBT serializeNBT() {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putInt("slot_count", slotCount);
        for (int i = 0; i < slotCount; i++) {
            slot slot = slots[i];
            CompoundNBT slotNBT = new CompoundNBT();
            slotNBT.putInt("max_complexity", slot.getMaxComplexity());
            slotNBT.put("itemstack", slot.itemStack.serializeNBT());
            compoundNBT.put("slot" + i, slotNBT);
        }
        return compoundNBT;
    }

    public void deserializeNBT(CompoundNBT compoundNBT) {
        int slot_count = compoundNBT.getInt("slot_count");
        slots = new slot[slot_count];
        for (int i = 0; i < slot_count; i++) {
            CompoundNBT compound = compoundNBT.getCompound("slot" + i);
            int max_complexity = compound.getInt("max_complexity");
            ItemStack itemstack = ItemStack.read(compound.getCompound("itemstack"));
            slots[i] = new slot(max_complexity, itemstack);
        }
    }

    private static class slot {
        private final int maxComplexity;
        public ItemStack itemStack;

        public slot(int maxComplexity, ItemStack itemStack) {
            this.maxComplexity = maxComplexity;
            this.itemStack = itemStack;
        }

        public int getMaxComplexity() {
            return maxComplexity;
        }

    }
}
