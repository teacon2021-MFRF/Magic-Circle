package mfrf.magic_circle.rendering;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

public class MagicCircleRenderBase extends MagicCircleComponentBase<MagicCircleRenderBase> {

    public MagicCircleRenderBase(float delay, float xrot, float yrot, float zrot, int renderTime) {
        super(delay, xrot, yrot, zrot, renderTime);
    }

    public MagicCircleRenderBase() {
        super();
    }

    @Override
    protected boolean renderingSelf(float time, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, Vector3d lookVec, Vector3d verticalVec, Vector3f actualPosition, EntityRendererManager renderer) {
        return true;
    }

    @Override
    protected boolean renderingSelf(float time, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, Vector3d lookVec, Vector3d verticalVec, Vector3f actualPosition, TileEntityRendererDispatcher renderer) {
        return true;
    }

    @Override
    public boolean rendering(float time, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, Vector3d lookVec, Vector3d verticalVec, Vector3f actualPosition, EntityRendererManager renderer) {
        if (renderTime == 0) {
            boolean flag = true;

            for (MagicCircleComponentBase<?> nextParallelComponent : nextParallelComponents) {
                boolean rendering = true;
                if (nextParallelComponent != null) {
                    rendering = nextParallelComponent.rendering(time - delay, matrixStackIn, bufferIn, lookVec, verticalVec, actualPosition, renderer);
                }
                flag = flag && rendering;
            }

            return flag;
        } else if (renderTime > 0) {

            if (time <= renderTime) {
                for (MagicCircleComponentBase<?> nextParallelComponent : nextParallelComponents) {
                    if (nextParallelComponent != null) {
                        nextParallelComponent.rendering(time - delay, matrixStackIn, bufferIn, lookVec, verticalVec, actualPosition, renderer);
                    }
                }
            } else {
                return true;
            }


        }

        return false;

    }

    @Override
    public boolean rendering(float time, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, Vector3d lookVec, Vector3d verticalVec, Vector3f actualPosition, TileEntityRendererDispatcher renderer) {
        boolean flag = time - renderTime <= 0 || renderTime == 0;
        if (flag) {
            for (MagicCircleComponentBase<?> nextParallelComponent : nextParallelComponents) {
                boolean rendering = true;
                if (nextParallelComponent != null) {
                    rendering = nextParallelComponent.rendering(time - delay, matrixStackIn, bufferIn, lookVec, verticalVec, actualPosition, renderer);
                }
                flag = flag && rendering;
            }

        }
        return flag;
    }
}
