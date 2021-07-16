package mfrf.magic_circle.interfaces;

import mfrf.magic_circle.magicutil.MagicStream;
import mfrf.magic_circle.util.MagicalItemContainer;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.UUID;

public interface IMagicalItem {

    float getManaCapacity();

    void setManaCapacity(float value);

    int getMana();

    void setMana(int value);

    float getManaRecovery();

    void setManaRecovery(float value);

    boolean hasPrimed();

    void setHasPrimed(boolean value);

    MagicalItemContainer getEffectContainer();

    void setEffectContainer(CompoundNBT nbt);

    void setScaleCapacityIfPrimed(double value);

    double getScaleCapacityIfPrimed();

    void setScaleRecoverIfPrimed(double value);

    double getScaleRecoverIfPrimed();

    void onPriming(World world, BlockPos pos);

    ArrayList<String> magics();

    boolean removeMagic(String name);

    int getMaxMagicCapacity();

    void setMaxMagicCapacity(int value);

    boolean addMagic(String magicModelName);

    void onTick();

    String getSelectedMagic();

    void setSelectedMagic(String selectedMagic);

    void executeMagic(boolean executeAll, World world, UUID uuid, MagicStream.MagicStreamInfo streaminfo);
}
