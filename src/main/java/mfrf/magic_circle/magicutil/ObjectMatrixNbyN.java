package mfrf.magic_circle.magicutil;

import mfrf.magic_circle.interfaces.MatrixObjectComponent;
import org.ejml.EjmlParameters;

public class ObjectMatrixNbyN {
    public MatrixObjectComponent[] data;
    public int numRows;
    public int numCols;

    public ObjectMatrixNbyN(MatrixObjectComponent[] data, int numRows, int numCols) {
        this.data = data;
        this.numRows = numRows;
        this.numCols = numCols;
    }

    public ObjectMatrixNbyN(int numRows, int numCols) {
        this.data = new MatrixObjectComponent[numRows * numCols];
        this.numRows = numRows;
        this.numCols = numCols;
    }

    public ObjectMatrixNbyN() {

    }

    public void reshape(int numRows, int numCols, boolean saveValues) {
        if (numRows * numCols <= data.length) {
            this.numRows = numRows;
            this.numCols = numCols;
        } else {
            MatrixObjectComponent[] data = new MatrixObjectComponent[numRows * numCols];

            if (saveValues) {
                System.arraycopy(this.data, 0, data, 0, getNumElements());
            }

            this.numRows = numRows;
            this.numCols = numCols;
            this.data = data;
        }
    }

    public int getIndex(int row, int col) {
        return (row - 1) * numCols + col;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    public void setNumCols(int numCols) {
        this.numCols = numCols;
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    public void setData(MatrixObjectComponent[] data) {
        this.data = data;
    }

    public MatrixObjectComponent[] getData() {
        return data;
    }

    public void set(ObjectMatrixNbyN A) {
        this.numRows = A.numRows;
        this.numCols = A.numCols;

        int N = numCols * numRows;

        if (data.length < N)
            data = new MatrixObjectComponent[N];

        System.arraycopy(A.data, 0, data, 0, N);
    }

    public MatrixObjectComponent get(int index) {
        return data[index];
    }

    public MatrixObjectComponent set(int index, MatrixObjectComponent val) {
        return data[index] = val;
    }

    public MatrixObjectComponent plus(int index, MatrixObjectComponent val) {
        return data[index] = data[index].plus(val);
    }


    public MatrixObjectComponent plus(int index, double val) {
        return data[index] = data[index].plus(val);
    }

    public MatrixObjectComponent minus(int index, MatrixObjectComponent val) {
        return data[index] = data[index].minus(val);
    }

    public MatrixObjectComponent minus(int index, double val) {
        return data[index] = data[index].minus(val);
    }

    public MatrixObjectComponent times(int index, MatrixObjectComponent val) {
        return data[index] = data[index].times(val);
    }

    public MatrixObjectComponent times(int index, double val) {
        return data[index] = data[index].times(val);
    }

    public MatrixObjectComponent div(int index, MatrixObjectComponent val) {
        return data[index] = data[index].div(val);
    }

    public MatrixObjectComponent div(int index, double val) {
        return data[index] = data[index].div(val);
    }

    public int getNumElements() {
        return numRows * numCols;
    }

    public ObjectMatrixNbyN copy() {
        ObjectMatrixNbyN objectMatrixNbyN = new ObjectMatrixNbyN(numRows, numCols);
        objectMatrixNbyN.set(this);
        return objectMatrixNbyN;
    }
}