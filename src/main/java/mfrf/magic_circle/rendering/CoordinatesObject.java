package mfrf.magic_circle.rendering;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

import javax.annotation.Nullable;

public class CoordinatesObject extends MagicCircleComponentBase<CoordinatesObject> {

    protected Coordinates coordinates = new Coordinates(null, null, null, null);
    protected int renderTick = 0;
    private float actualRenderTick = 0;

    public CoordinatesObject(float delay, float xRotateSpeedRadius, float yRotateSpeedRadius, float zRotateSpeedRadius, int renderTick, @Nullable Coordinates coordinates) {
        super(delay, xRotateSpeedRadius, yRotateSpeedRadius, zRotateSpeedRadius);
        this.renderTick = renderTick;
        this.actualRenderTick = renderTick + delay;
        if (coordinates != null) {
            this.coordinates = coordinates;
        }
    }

    public CoordinatesObject() {
        super();
    }

    @Override
    protected boolean renderingSelf(float time, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, float trueTime, Vector3d lookVec, Vector3f actualPosition) {
        return false;
    }

}
