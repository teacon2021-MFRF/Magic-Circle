package mfrf.magic_circle.magicutil;

import net.minecraft.nbt.CompoundNBT;
import org.ejml.data.BlockMatrix64F;

public class DecimalMagicMatrixNByN extends BlockMatrix64F {

    public DecimalMagicMatrixNByN(float[][] cr, int row, int col) {
        super(row, col);
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                unsafe_set(r, c, cr.length > r ? cr[r].length > c ? cr[c][r] : 0 : 0);
            }
        }
    }

    public DecimalMagicMatrixNByN(int numRows, int numCols) {
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
        DecimalMagicMatrixNByN decimalMagicMatrixNByN = new DecimalMagicMatrixNByN(row, col);
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                decimalMagicMatrixNByN.unsafe_set(r, c, compoundNBT.getDouble("M" + r + c));
            }
        }
    }
}

