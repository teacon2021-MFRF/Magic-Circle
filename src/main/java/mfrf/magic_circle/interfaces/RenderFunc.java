package mfrf.magic_circle.interfaces;

import com.mojang.blaze3d.matrix.MatrixStack;

@FunctionalInterface
public interface RenderFunc {
    void func(MatrixStack matrixStack, int x1, int x2, int y1, int y2, int blitOffset, int uWidth, int vHeight, float uOffset, float vOffset, int textureWidth, int textureHeight);
}
