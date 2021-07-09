package mfrf.magic_circle.magicutil.datastructure;

import org.ejml.data.DenseMatrix64F;
import org.ejml.ops.CommonOps;

import net.minecraft.nbt.CompoundNBT;

public class MagicStreamMatrixNByN extends DenseMatrix64F {

    public MagicStreamMatrixNByN(float[][] cr, int row, int col) {
        super(row, col);
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                unsafe_set(r, c, cr.length > r ? cr[r].length > c ? cr[c][r] : 0 : 0);
            }
        }
    }

    protected MagicStreamMatrixNByN(double[] data, int cols, int rows) {
        super(rows, cols);
        this.setData(data);
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

    public static MagicStreamMatrixNByN deserializeNBT(CompoundNBT compoundNBT) {
        int row = compoundNBT.getInt("row");
        int col = compoundNBT.getInt("col");
        MagicStreamMatrixNByN magicStreamMatrixNByN = new MagicStreamMatrixNByN(row, col);
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                magicStreamMatrixNByN.unsafe_set(r, c, compoundNBT.getDouble("M" + r + c));
            }
        }
        return magicStreamMatrixNByN;
    }

    public MagicStreamMatrixNByN leftTimes(MagicStreamMatrixNByN magicStreamMatrixNByN) {

        // int index = this.numRows * magicStreamMatrixNByN.numCols;
        // double[] matrixObjectComponents = new double[index];
        //
        // for (int i = 0; i < index; i++) {
        // Subscript subScript = getSubScript(i, magicStreamMatrixNByN.numCols);
        // double[] row = getRow(subScript.i);
        // double[] col = magicStreamMatrixNByN.getCol(subScript.j);
        //
        // double matrixObjectComponent = row[0] * col[0];
        // if (row.length > col.length) {
        //
        // for (int i1 = 1; i1 < col.length; i1++) {
        // matrixObjectComponent += row[i1] * col[i1];
        // }
        //
        // } else {
        //
        // for (int i1 = 1; i1 < row.length; i1++) {
        // matrixObjectComponent += row[i1] * col[i1];
        // }
        //
        // }
        // matrixObjectComponents[i] = matrixObjectComponent;
        // }
        //
        // return new MagicStreamMatrixNByN(matrixObjectComponents, this.numRows,
        // magicStreamMatrixNByN.numCols);
        MagicStreamMatrixNByN ret = new MagicStreamMatrixNByN(this.numRows, magicStreamMatrixNByN.numCols);
        CommonOps.mult(this, magicStreamMatrixNByN, ret);
        return ret;
    }

    public double[] getRow(int i) {
        int begin = (i - 1) * numCols;
        double[] matrixObjectComponents = new double[numCols];
        if (numRows >= 0)
            System.arraycopy(data, begin, matrixObjectComponents, 0, numRows);
        return matrixObjectComponents;
    }

    public double[] getCol(int j) {
        double[] matrixObjectComponents = new double[numRows];
        for (int i = 0; i < numRows; i++) {
            matrixObjectComponents[i] = data[j + i * numCols];
        }
        return matrixObjectComponents;
    }

    public Subscript getSubScript(int index) {
        int j = index % numCols;
        int i = (index - j) / numCols;
        return new Subscript(i, j);
    }

    public static Subscript getSubScript(int index, int columns) {
        int j = index % columns;
        int i = (index - j) / columns;
        return new Subscript(i, j);
    }

    public double sumAll() {
        return CommonOps.elementSum(this);
    }

    public double absSum() {
        return CommonOps.elementSumAbs(this);
    }

    public MagicStreamMatrixNByN invert() {
        MagicStreamMatrixNByN ret = new MagicStreamMatrixNByN(this.numRows, this.numCols);
        CommonOps.pinv(this, ret);
        return ret;
    }

    public boolean isSingular() {
        if (numRows != numCols) {
            return true;
        } else {
            return CommonOps.det(this) != 0;
        }
    }

    public double det() {
        if (isSingular()) {
            return 0;
        }
        return CommonOps.det(this);
    }

    private static class Subscript {
        public int i;
        public int j;

        public Subscript(int i, int j) {

        }
    }

}
