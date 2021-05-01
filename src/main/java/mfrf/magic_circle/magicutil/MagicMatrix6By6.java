package mfrf.magic_circle.magicutil;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;
import org.ejml.data.BlockMatrix64F;
import org.ejml.data.Matrix64F;
import org.ejml.ops.MatrixComponent;

public class MagicMatrix6By6 extends BlockMatrix64F implements INBTSerializable<CompoundNBT> {

    public MagicMatrix6By6(float[][] cr) {
        super(6, 6);
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 6; c++) {
                unsafe_set(r, c, cr.length > r ? cr[r].length > c ? cr[c][r] : 0 : 0);
            }
        }
    }

    @Override
    public CompoundNBT serializeNBT() {
        return null;
    }

    @Override
    public void deserializeNBT(CompoundNBT compoundNBT) {

    }
}
