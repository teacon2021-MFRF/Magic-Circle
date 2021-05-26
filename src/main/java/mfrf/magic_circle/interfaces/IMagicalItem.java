package mfrf.magic_circle.interfaces;

import mfrf.magic_circle.util.EffectiveItemContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IMagicalItem {

    float getManaCapacity();

    void setManaCapacity(float value);

    int getMana();

    void setMana(int value);

    float getManaRecovery();

    void setManaRecovery(float value);

    boolean hasPrimed();

    void setHasPrimed(boolean value);

    EffectiveItemContainer getEffectContainer();

    void setScaleCapacityIfPrimed(double value);

    double getScaleCapacityIfPrimed();

    void setScaleRecoverIfPrimed(double value);

    double getScaleRecoverIfPrimed();

    void onPriming(World world, BlockPos pos);

}
