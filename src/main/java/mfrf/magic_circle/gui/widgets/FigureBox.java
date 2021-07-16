package mfrf.magic_circle.gui.widgets;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import icyllis.modernui.graphics.Canvas;
import mfrf.magic_circle.interfaces.RenderFunc;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.IRenderable;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class FigureBox extends Widget implements IGuiEventListener, IRenderable {

    private ResourceLocation location;
    private int figureWidth;
    private int figureHeight;
    private int xOffset = 0;
    private int yOffset = 0;
    private float currentScale = 1;
    private float minScale;
    private boolean move = false;


    public FigureBox(int x, int y, int width, int height, ITextComponent p_i232254_5_, @Nullable ResourceLocation location) throws IOException {
        super(x, y, width, height, p_i232254_5_);
        this.location = location;
        if (location != null) {
            IResource texture = Minecraft.getInstance().getResourceManager().getResource(location);
            BufferedImage figure = ImageIO.read(texture.getInputStream());
            this.visible = true;
            this.active = true;

            this.figureWidth = figure.getWidth();
            this.figureHeight = figure.getHeight();
            if (figureHeight >= figureWidth) {
                minScale = (float) height / (float) figureHeight;
            } else {
                minScale = (float) width / (float) figureWidth;
            }
        }

    }

    @Override
    public void render(MatrixStack matrixStack, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
        if (location != null) {
            TextureManager textureManager = Minecraft.getInstance().getTextureManager();
            textureManager.bind(location);
            int u = (int) (width * currentScale);
            int v = (int) (height * currentScale);
//MatrixStack matrixStack, int x, int y, int width, int height, float uOffset, float vOffset, int uWidth, int vHeight, int textureWidth, int textureHeight
            blit(matrixStack, x, y, width, height, xOffset, yOffset, u, v, width, height);
        }
    }

    @Override
    public void playDownSound(SoundHandler p_230988_1_) {

    }

    @Override
    public boolean mouseDragged(double scaledMouseX, double scaledMouseY, int activeMouseButton, double deltaX, double deltaY) {
        if (location != null) {
            double movedLUX = xOffset - deltaX * currentScale;
            double movedLUY = yOffset - deltaY * currentScale;
            double leftBoundary = 0;
            double rightBoundary = (width / currentScale - width);
            double upBoundary = 0;
            double downBoundary = (height / (currentScale) - height);

            if (movedLUX < leftBoundary) {
                xOffset = (int) leftBoundary;
            } else if (movedLUX > rightBoundary) {
                xOffset = (int) rightBoundary;
            } else {
                xOffset = (int) movedLUX;
            }

            if (movedLUY < upBoundary) {
                yOffset = (int) upBoundary;
            } else if (movedLUY > downBoundary) {
                yOffset = (int) downBoundary;
            } else {
                yOffset = (int) movedLUY;
            }


            return true;
        }
        return super.mouseDragged(scaledMouseX, scaledMouseY, activeMouseButton, deltaX, deltaY);
    }

    @Override
    public boolean mouseScrolled(double p_231043_1_, double p_231043_3_, double p_231043_5_) {
        if (location != null) {
            float scaled = (float) (currentScale - p_231043_5_ / 10);
            if (scaled > 1) {
                currentScale = 1;
            } else if (scaled < minScale) {
                currentScale = minScale;
            } else {
                currentScale = scaled;
            }
            return true;
        }
        return false;
    }

}
