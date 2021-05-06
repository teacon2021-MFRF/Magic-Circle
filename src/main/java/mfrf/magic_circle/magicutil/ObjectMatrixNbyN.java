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

    public MatrixObjectComponent[] getRow(int i) {
        int begin = (i - 1) * numCols;
        MatrixObjectComponent[] matrixObjectComponents = new MatrixObjectComponent[numCols];
        for (int indexOffset = 0; indexOffset < numRows; indexOffset++) {
            matrixObjectComponents[indexOffset] = data[begin + indexOffset];
        }
        return matrixObjectComponents;
    }

    public MatrixObjectComponent[] getCol(int j) {
        MatrixObjectComponent[] matrixObjectComponents = new MatrixObjectComponent[numRows];
        for (int i = 0; i < numRows; i++) {
            matrixObjectComponents[i] = data[j + i * numCols];
        }
        return matrixObjectComponents;
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

    /**
     * "this" Matrix is A
     * the shape should be checked;
     * if shape not equal,the matrix will get the "best result"
     *
     * @param B AB = C
     * @return result
     */
    public ObjectMatrixNbyN LeftTimes(ObjectMatrixNbyN B) {
        int index = this.numRows * B.numCols;
        MatrixObjectComponent[] matrixObjectComponents = new MatrixObjectComponent[index];

        for (int i = 0; i < index; i++) {
            Subscript subScript = getSubScript(i, B.numCols);
            MatrixObjectComponent[] row = getRow(subScript.i);
            MatrixObjectComponent[] col = getCol(subScript.j);

            MatrixObjectComponent matrixObjectComponent = row[0].times(col[0]);
            if (row.length > col.length) {

                for (int i1 = 1; i1 < col.length; i1++) {
                    matrixObjectComponent.plus(row[i1].times(col[i1]));
                }

            } else {

                for (int i1 = 1; i1 < row.length; i1++) {
                    matrixObjectComponent.plus(row[i1].times(col[i1]));
                }

            }
            matrixObjectComponents[i] = matrixObjectComponent;
        }

        return new ObjectMatrixNbyN(matrixObjectComponents, this.numRows, B.numCols);
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

    private static class Subscript {
        public final int i;
        public final int j;

        public Subscript(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }
}