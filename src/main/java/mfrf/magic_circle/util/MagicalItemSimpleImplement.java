package mfrf.magic_circle.util;

import mfrf.magic_circle.interfaces.IMagicalItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MagicalItemSimpleImplement implements IMagicalItem {

    private effectiveItemContainer effectiveItemContainer = new effectiveItemContainer();
    private int manaCapacity;
    private double scaleCapacityIFPrimed;
    private int manaCurrent;
    private int manaRecover;
    private double scaleRecoverIfPrimed;
    boolean hasPrimed = false;

    public MagicalItemSimpleImplement(effectiveItemContainer container, int manaCapacity, int scaleCapacityIFPrimed, int manaCurrent, int manaRecover, double scaleRecoverIfPrimed) {
        this.effectiveItemContainer = container;
        this.manaCapacity = manaCapacity;
        this.scaleCapacityIFPrimed = scaleCapacityIFPrimed;
        this.manaCurrent = manaCurrent;
        this.manaRecover = manaRecover;
        this.scaleRecoverIfPrimed = scaleRecoverIfPrimed;
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
    public effectiveItemContainer getEffectContainer() {
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
}
