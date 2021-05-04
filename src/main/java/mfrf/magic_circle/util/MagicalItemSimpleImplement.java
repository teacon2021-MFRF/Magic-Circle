package mfrf.magic_circle.util;

import mfrf.magic_circle.interfaces.IMagicalItem;
import mfrf.magic_circle.registry_lists.Capabilities;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MagicalItemSimpleImplement implements IMagicalItem, INBTSerializable<CompoundNBT>, ICapabilityProvider {

    private EffectiveItemContainer effectiveItemContainer = new EffectiveItemContainer();
    private int manaCapacity;
    private double scaleCapacityIFPrimed;
    private int manaCurrent;
    private int manaRecover;
    private double scaleRecoverIfPrimed;
    boolean hasPrimed = false;
    private ItemStack stack;

    public MagicalItemSimpleImplement(EffectiveItemContainer container, int manaCapacity, double scaleCapacityIFPrimed, int manaCurrent, int manaRecover, double scaleRecoverIfPrimed, ItemStack stack) {
        this.effectiveItemContainer = container;
        this.manaCapacity = manaCapacity;
        this.scaleCapacityIFPrimed = scaleCapacityIFPrimed;
        this.manaCurrent = manaCurrent;
        this.manaRecover = manaRecover;
        this.scaleRecoverIfPrimed = scaleRecoverIfPrimed;
        this.stack = stack;
    }

    public MagicalItemSimpleImplement() {
    }

    @Override
    public int getManaCapacity() {
        return manaCapacity;
    }

    @Override
    public void setManaCapacity(int value) {
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
    public int getManaRecover() {
        return manaRecover;
    }

    @Override
    public void setManaRecover(int value) {
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
        setManaCapacity((int) Math.round(getManaCapacity() * scaleCapacityIFPrimed));
        setManaRecover((int) Math.round(getManaRecover() * scaleRecoverIfPrimed));
    }

    public MagicalItemSimpleImplement copy(ItemStack stack) {
        return new MagicalItemSimpleImplement(effectiveItemContainer.clone(), manaCapacity, scaleCapacityIFPrimed, manaCurrent, manaRecover, scaleRecoverIfPrimed, stack);
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.put("effect_container", effectiveItemContainer.serializeNBT());
        compoundNBT.putInt("current_capacity", manaCapacity);
        compoundNBT.putInt("current_recover", manaRecover);
        compoundNBT.putBoolean("primed", hasPrimed);
        compoundNBT.putDouble("scale_capacity_if_primed", scaleCapacityIFPrimed);
        compoundNBT.putDouble("scale_recover_if_primed", scaleRecoverIfPrimed);
        compoundNBT.putInt("mana_current", manaCurrent);
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

    @Override
    public String toString() {
        return "MagicalItemSimpleImplement{" +
                "effectiveItemContainer=" + effectiveItemContainer +
                ", manaCapacity=" + manaCapacity +
                ", scaleCapacityIFPrimed=" + scaleCapacityIFPrimed +
                ", manaCurrent=" + manaCurrent +
                ", manaRecover=" + manaRecover +
                ", scaleRecoverIfPrimed=" + scaleRecoverIfPrimed +
                ", hasPrimed=" + hasPrimed +
                ", stack=" + stack +
                '}';
    }
}
