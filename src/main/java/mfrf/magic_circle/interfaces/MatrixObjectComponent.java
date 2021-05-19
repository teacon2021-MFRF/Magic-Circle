package mfrf.magic_circle.interfaces;

import mfrf.magic_circle.magicutil.datastructure.MagicNodePropertyMatrix8By8;

public interface MatrixObjectComponent {

//    MatrixObjectComponent times(MatrixObjectComponent matrixObjectComponent);
//
//    DecimalMagicMatrix6By6 times(DecimalMagicMatrix6By6 d);
//
//    MatrixObjectComponent plus(MatrixObjectComponent matrixObjectComponent);
//
//    DecimalMagicMatrix6By6 plus(DecimalMagicMatrix6By6 d);
//
//    MatrixObjectComponent minus(MatrixObjectComponent matrixObjectComponent);
//
//    DecimalMagicMatrix6By6 minus(DecimalMagicMatrix6By6 d);
//
//    MatrixObjectComponent div(MatrixObjectComponent matrixObjectComponent);
//
//    DecimalMagicMatrix6By6 div(DecimalMagicMatrix6By6 d);

    MagicNodePropertyMatrix8By8 getEigenMatrix(MatrixObjectComponent matrixObjectComponent);

}
