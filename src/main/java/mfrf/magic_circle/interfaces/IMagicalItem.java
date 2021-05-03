package mfrf.magic_circle.interfaces;

import mfrf.magic_circle.util.effectiveItemContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IMagicalItem {

    int getManaCapacity();

    void setManaCapacity(int value);

    int getMana();

    void setMana(int value);

    int getManaRecover();

    void setManaRecover(int value);

    boolean hasPrimed();

    void setHasPrimed(boolean value);

    effectiveItemContainer getEffectContainer();

    void setScaleCapacityIfPrimed(double value);

    double getScaleCapacityIfPrimed();

    void setScaleRecoverIfPrimed(double value);

    double getScaleRecoverIfPrimed();

    void onPriming(World world, BlockPos pos);

}
