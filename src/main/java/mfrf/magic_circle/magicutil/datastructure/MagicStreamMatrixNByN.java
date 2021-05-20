package mfrf.magic_circle.magicutil.datastructure;

import net.minecraft.nbt.CompoundNBT;
import org.ejml.data.BlockMatrix64F;

import java.util.Arrays;

public class MagicStreamMatrixNByN extends BlockMatrix64F {

    public MagicStreamMatrixNByN(float[][] cr, int row, int col) {
        super(row, col);
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                unsafe_set(r, c, cr.length > r ? cr[r].length > c ? cr[c][r] : 0 : 0);
            }
        }
    }

    public MagicStreamMatrixNByN(int numRows, int numCols) {
        super(numRows, numCols);
    }

    public CompoundNBT serializeNBT() {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putInt("row", numRows);
        compoundNBT.putInt("col", numCols);
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                compoundNBT.putDouble("M" + r + c, get(r, c));
            }
        }
        return compoundNBT;
    }

    public static void deserializeNBT(CompoundNBT compoundNBT) {
        int row = compoundNBT.getInt("row");
        int col = compoundNBT.getInt("col");
        MagicStreamMatrixNByN magicStreamMatrixNByN = new MagicStreamMatrixNByN(row, col);
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                magicStreamMatrixNByN.unsafe_set(r, c, compoundNBT.getDouble("M" + r + c));
            }
        }
    }

    public double sumAll() {
        return Arrays.stream(data).sum();
    }
}

