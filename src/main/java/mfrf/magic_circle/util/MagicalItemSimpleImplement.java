package mfrf.magic_circle.util;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import mfrf.magic_circle.interfaces.IMagicalItem;
import mfrf.magic_circle.registry_lists.Capabilities;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;

import java.util.ArrayList;

public class MagicalItemSimpleImplement implements IMagicalItem, ICapabilityProvider {

    private MagicalItemContainer magicalItemContainer = new MagicalItemContainer();
    private float manaCapacity;
    private double scaleCapacityIFPrimed;
    private int manaCurrent;
    private float manaRecover;
    private double scaleRecoverIfPrimed;
    private boolean hasPrimed = false;
    private ItemStack stack = ItemStack.EMPTY;
    private ArrayList<String> magics = new ArrayList<>();
    private int maxMagicModelCapacity;

    public MagicalItemSimpleImplement(MagicalItemContainer container, float manaCapacity, double scaleCapacityIFPrimed, int manaCurrent, float manaRecover, double scaleRecoverIfPrimed, int maxMagicModelCapacity, ItemStack stack) {
        this.magicalItemContainer = container;
        this.manaCapacity = manaCapacity;
        this.scaleCapacityIFPrimed = scaleCapacityIFPrimed;
        this.manaCurrent = manaCurrent;
        this.manaRecover = manaRecover;
        this.scaleRecoverIfPrimed = scaleRecoverIfPrimed;
        this.maxMagicModelCapacity = maxMagicModelCapacity;
        if (stack != null) {
            this.stack = stack;
        }
    }

    public MagicalItemSimpleImplement() {
    }


    public CompoundNBT getTag() {
        CompoundNBT magic_circle;
        if (!stack.isEmpty()) {
            magic_circle = stack.getOrCreateTagElement("magic_circle");
        } else {
            magic_circle = new CompoundNBT();
        }
        if (!magic_circle.contains("implement")) {
            magic_circle.put("implement", defaultNBT());
        }
        return magic_circle;
    }

    @Override
    public float getManaCapacity() {
        return getTag().getFloat("current_capacity");
    }

    @Override
    public void setManaCapacity(float value) {
        getTag().putFloat("current_capacity", value);
    }

    @Override
    public int getMana() {
        return getTag().getInt("mana_current");
    }

    @Override
    public void setMana(int value) {
        getTag().putInt("mana_current", value);
    }

    @Override
    public float getManaRecovery() {
        return getTag().getFloat("mana_recovery");
    }

    @Override
    public void setManaRecovery(float value) {
        getTag().putFloat("mana_recovery", value);
    }

    @Override
    public boolean hasPrimed() {
        return getTag().getBoolean("has_primed");
    }

    @Override
    public void setHasPrimed(boolean value) {
        getTag().putBoolean("has_primed", value);
    }

    @Override
    public MagicalItemContainer getEffectContainer() {
        MagicalItemContainer magicalItemContainer = new MagicalItemContainer();
        magicalItemContainer.deserializeNBT(getTag().getCompound("magic_item_container"));
        return magicalItemContainer;
    }

    @Override
    public void setScaleCapacityIfPrimed(double value) {
        getTag().putDouble("scale_capacity_primed", value);
    }

    @Override
    public double getScaleCapacityIfPrimed() {
        return getTag().getDouble("scale_capacity_primed");
    }

    @Override
    public void setScaleRecoverIfPrimed(double value) {
        getTag().putDouble("scale_recovery_primed", value);
    }

    @Override
    public double getScaleRecoverIfPrimed() {
        return getTag().getDouble("scale_recovery_primed");
    }

    @Override
    public void onPriming(World world, BlockPos pos) {
        setHasPrimed(true);
        setManaCapacity((float) (getManaCapacity() * getScaleCapacityIfPrimed()));
        setManaRecovery((float) (getManaRecovery() * getScaleRecoverIfPrimed()));
    }

    @Override
    public ArrayList<String> magics() {
        ArrayList<String> strings = new ArrayList<>();
        for (INBT inbt : getTag().getList("magics", Constants.NBT.TAG_COMPOUND)) {
            CompoundNBT compoundNBT = (CompoundNBT) inbt;
            strings.add(compoundNBT.getString("magic"));
        }
        return strings;
    }

    @Override
    public boolean removeMagic(String name) {
        return getTag().getList("magics", Constants.NBT.TAG_COMPOUND).removeIf(inbt -> {
            CompoundNBT compoundNBT = (CompoundNBT) inbt;
            return compoundNBT.contains(name);
        });
    }

    @Override
    public int getMaxMagicCapacity() {
        return getTag().getInt("max_magic_capacity");
    }

    @Override
    public boolean addMagic(String magicModelName) {
        CompoundNBT tag = getTag();

        ListNBT magics = tag.getList("magics", Constants.NBT.TAG_COMPOUND);
        if (magics.size() < getMaxMagicCapacity()) {
            CompoundNBT compoundNBT = new CompoundNBT();
            compoundNBT.putString("magic", magicModelName);
            magics.add(compoundNBT);
            return true;
        }
        return false;
    }

    @Override
    public void setEffectContainer(CompoundNBT nbt) {
        getTag().put("item_container", nbt);
    }

    public MagicalItemSimpleImplement copy(ItemStack stack, CompoundNBT nbt) {
        MagicalItemSimpleImplement implement = new MagicalItemSimpleImplement(
                getEffectContainer().clone(),
                getManaCapacity(),
                getScaleCapacityIfPrimed(),
                getMana(),
                getManaRecovery(),
                getScaleRecoverIfPrimed(),
                getMaxMagicCapacity(),
                stack);
        return implement;
    }

    public CompoundNBT defaultNBT() {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.put("effect_container", magicalItemContainer.serializeNBT());
        compoundNBT.putFloat("current_capacity", manaCapacity);
        compoundNBT.putFloat("current_recover", manaRecover);
        compoundNBT.putBoolean("primed", hasPrimed);
        compoundNBT.putDouble("scale_capacity_if_primed", scaleCapacityIFPrimed);
        compoundNBT.putDouble("scale_recover_if_primed", scaleRecoverIfPrimed);
        compoundNBT.putInt("mana_current", manaCurrent);
        compoundNBT.putInt("max_magic_model_capacity", maxMagicModelCapacity);

        ListNBT magics = new ListNBT();
        for (String magic : this.magics) {
            CompoundNBT compoundNBT1 = new CompoundNBT();
            compoundNBT1.putString("name", magic);
            magics.add(compoundNBT1);
        }
        compoundNBT.put("magics", magics);

        return compoundNBT;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == Capabilities.MAGICAL_ITEM) {
            return LazyOptional.of(() -> this).cast();
        }
        return LazyOptional.empty();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        return ICapabilityProvider.super.getCapability(cap);
    }
}
