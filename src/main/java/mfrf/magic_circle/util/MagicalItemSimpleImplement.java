package mfrf.magic_circle.util;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import mfrf.magic_circle.interfaces.IMagicalItem;
import mfrf.magic_circle.registry_lists.Capabilities;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import java.util.ArrayList;

public class MagicalItemSimpleImplement implements IMagicalItem, INBTSerializable<CompoundNBT>, ICapabilityProvider {

    private EffectiveItemContainer effectiveItemContainer = new EffectiveItemContainer();
    private float manaCapacity;
    private double scaleCapacityIFPrimed;
    private int manaCurrent;
    private float manaRecover;
    private double scaleRecoverIfPrimed;
    private boolean hasPrimed = false;
    private ItemStack stack;
    private ArrayList<String> magics = new ArrayList<>();
    private int maxMagicModelCapacity;

    public MagicalItemSimpleImplement(EffectiveItemContainer container, float manaCapacity, double scaleCapacityIFPrimed, int manaCurrent, float manaRecover, double scaleRecoverIfPrimed, int maxMagicModelCapacity, ItemStack stack) {
        this.effectiveItemContainer = container;
        this.manaCapacity = manaCapacity;
        this.scaleCapacityIFPrimed = scaleCapacityIFPrimed;
        this.manaCurrent = manaCurrent;
        this.manaRecover = manaRecover;
        this.scaleRecoverIfPrimed = scaleRecoverIfPrimed;
        this.maxMagicModelCapacity = maxMagicModelCapacity;
        this.stack = stack;
    }

    public MagicalItemSimpleImplement() {
    }

    @Override
    public float getManaCapacity() {
        return manaCapacity;
    }

    @Override
    public void setManaCapacity(float value) {
        manaCapacity = value;
    }

    @Override
    public int getMana() {
        return manaCurrent;
    }

    @Override
    public void setMana(int value) {
        manaCurrent = value;
    }

    @Override
    public float getManaRecovery() {
        return manaRecover;
    }

    @Override
    public void setManaRecovery(float value) {
        manaRecover = value;
    }

    @Override
    public boolean hasPrimed() {
        return hasPrimed;
    }

    @Override
    public void setHasPrimed(boolean value) {
        hasPrimed = value;
    }

    @Override
    public EffectiveItemContainer getEffectContainer() {
        return effectiveItemContainer;
    }

    @Override
    public void setScaleCapacityIfPrimed(double value) {
        scaleCapacityIFPrimed = value;
    }

    @Override
    public double getScaleCapacityIfPrimed() {
        return scaleCapacityIFPrimed;
    }

    @Override
    public void setScaleRecoverIfPrimed(double value) {
        scaleRecoverIfPrimed = value;
    }

    @Override
    public double getScaleRecoverIfPrimed() {
        return scaleRecoverIfPrimed;
    }

    @Override
    public void onPriming(World world, BlockPos pos) {
        setHasPrimed(true);
        setManaCapacity((float) (getManaCapacity() * scaleCapacityIFPrimed));
        setManaRecovery((float) (getManaRecovery() * scaleRecoverIfPrimed));
    }

    @Override
    public ArrayList<String> magics() {
        return magics;
    }

    @Override
    public int getMaxMagicCapacity() {
        return maxMagicModelCapacity;
    }

    @Override
    public boolean addMagic(String magicModelName) {
        if (!(magics.size() >= maxMagicModelCapacity)) {
            magics.add(magicModelName);
        }
        return false;
    }

    public MagicalItemSimpleImplement copy(ItemStack stack, CompoundNBT nbt) {
        MagicalItemSimpleImplement implement = new MagicalItemSimpleImplement(effectiveItemContainer.clone(), manaCapacity, scaleCapacityIFPrimed, manaCurrent, manaRecover, scaleRecoverIfPrimed, maxMagicModelCapacity, stack);
        if (nbt != null && nbt.contains("magical_item_implement")) {
            implement.deserializeNBT(nbt.getCompound("magical_item_implement"));
        }
        return implement;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.put("effect_container", effectiveItemContainer.serializeNBT());
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

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        effectiveItemContainer.deserializeNBT(nbt.getCompound("effect_container"));
        manaCapacity = nbt.getInt("current_capacity");
        manaRecover = nbt.getInt("current_recover");
        hasPrimed = nbt.getBoolean("primed");
        scaleCapacityIFPrimed = nbt.getDouble("scale_capacity_if_primed");
        scaleRecoverIfPrimed = nbt.getDouble("scale_recover_if_primed");
        manaCurrent = nbt.getInt("mana_current");
        maxMagicModelCapacity = nbt.getInt("max_magic_model_capacity");

        ListNBT magics = nbt.getList("magics", Constants.NBT.TAG_COMPOUND);
        for (INBT magic : magics) {
            CompoundNBT compoundNBT = (CompoundNBT) magic;
            this.magics.add(compoundNBT.getString("name"));
        }
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
