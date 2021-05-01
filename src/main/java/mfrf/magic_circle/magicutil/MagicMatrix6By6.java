package mfrf.magic_circle.magicutil;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;
import org.ejml.data.BlockMatrix64F;

public class MagicMatrix6By6 extends MagicMatrixNByN {

    public MagicMatrix6By6(float[][] cr) {
        super(cr, 6, 6);
    }

}
