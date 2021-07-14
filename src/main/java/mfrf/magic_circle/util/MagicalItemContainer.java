package mfrf.magic_circle.util;

import mfrf.magic_circle.interfaces.IMagicContainerItem;
import mfrf.magic_circle.interfaces.IMagicalItem;
import mfrf.magic_circle.registry_lists.Capabilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.LazyOptional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MagicalItemContainer implements Cloneable, IInventory {
    public int slotCount;
    public ArrayList<Slot> slots = new ArrayList<>();
    public ItemStack itemStack;
    //todo finishit

    public MagicalItemContainer(Slot[] Slots) {
        this.slotCount = Slots.length;
        Slot[] slots = new Slot[slotCount];
        for (int i = 0; i < slotCount; i++) {
            slots[i] = Slots[i].clone();
        }
        this.slots.addAll(Arrays.asList(slots));
    }

    public MagicalItemContainer(int slotCount) {
        this.slotCount = slotCount;
        slots = new ArrayList<>(slotCount);
    }

    public MagicalItemContainer() {
    }

    //todo caninsert,insert,extract.etc

    public CompoundNBT serializeNBT() {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putInt("slot_count", slotCount);
        for (int i = 0; i < slotCount; i++) {
            Slot slot = slots.get(i);
            CompoundNBT slotNBT = new CompoundNBT();
            slotNBT.putInt("max_complexity", slot.getMaxComplexity());
            slotNBT.put("itemstack", slot.itemStack.serializeNBT());
            compoundNBT.put("slot" + i, slotNBT);
        }
        return compoundNBT;
    }

    public void deserializeNBT(CompoundNBT compoundNBT) {
        this.slotCount = compoundNBT.getInt("slot_count");
        slots = new ArrayList<>();
        for (int i = 0; i < slotCount; i++) {
            CompoundNBT compound = compoundNBT.getCompound("slot" + i);
            int max_complexity = compound.getInt("max_complexity");
            ItemStack itemstack = ItemStack.of(compound.getCompound("itemstack"));
            slots.set(i, new Slot(max_complexity, itemstack));
        }
    }

    @Override
    protected MagicalItemContainer clone() {
        return new MagicalItemContainer(slots.toArray(new Slot[]{}));
    }

    @Override
    public int getContainerSize() {
        return slotCount;
    }

    @Override
    public boolean isEmpty() {
        for (Slot slot : slots) {
            if (!slot.itemStack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getItem(int p_70301_1_) {
        return slots.get(p_70301_1_).itemStack;
    }

    @Override
    public ItemStack removeItem(int p_70298_1_, int p_70298_2_) {
        return ItemStackHelper.removeItem(slots.stream().map(slot -> slot.itemStack).collect(Collectors.toList()), p_70298_1_, p_70298_2_);
    }

    @Override
    public ItemStack removeItemNoUpdate(int p_70304_1_) {
        ItemStack copy = slots.get(p_70304_1_).itemStack.copy();
        slots.get(p_70304_1_).itemStack = ItemStack.EMPTY;
        return copy;
    }

    @Override
    public void setItem(int p_70299_1_, ItemStack p_70299_2_) {
        slots.get(p_70299_1_).itemStack = p_70299_2_;
    }

    @Override
    public void setChanged() {

    }

    @Override
    public boolean stillValid(PlayerEntity p_70300_1_) {
        return true;
    }

    @Override
    public void clearContent() {
        for (Slot slot : slots) {
            slot.itemStack = ItemStack.EMPTY;
        }
    }

    public boolean canInsert(int index, ItemStack itemStack) {
        return slots.get(index).canInsert(itemStack);
    }

    public List<LazyOptional<IMagicalItem>> tryParse() {
        List<LazyOptional<IMagicalItem>> collect = slots.stream().map(Slot::tryParse).filter(LazyOptional::isPresent).collect(Collectors.toList());
        return collect;
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


        public static Slot[] createArray(int... maxComplexity) {
            int count = maxComplexity.length;
            Slot[] slots = new Slot[count];
            for (int i = 0; i < count; i++) {
                slots[i] = new Slot(maxComplexity[i], ItemStack.EMPTY);
            }
            return slots;
        }

        public boolean canInsert(ItemStack itemStack) {
            return itemStack.isEmpty() && itemStack.getCapability(Capabilities.MAGICAL_ITEM).isPresent();
        }

        public LazyOptional<IMagicalItem> tryParse() {
            return itemStack.getCapability(Capabilities.MAGICAL_ITEM);
        }

        @Override
        protected Slot clone() {
            return new Slot(maxComplexity, itemStack.copy());
        }
    }
}
