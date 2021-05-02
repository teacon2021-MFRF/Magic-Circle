package mfrf.magic_circle.interfaces;

public interface MatrixObjectComponent {
    MatrixObjectComponent times(MatrixObjectComponent matrixObjectComponent);

    MatrixObjectComponent times(double d);

    MatrixObjectComponent plus(MatrixObjectComponent matrixObjectComponent);

    MatrixObjectComponent plus(double d);

    MatrixObjectComponent minus(MatrixObjectComponent matrixObjectComponent);

    MatrixObjectComponent minus(double d);


    MatrixObjectComponent div(MatrixObjectComponent matrixObjectComponent);

    MatrixObjectComponent div(double d);
}
