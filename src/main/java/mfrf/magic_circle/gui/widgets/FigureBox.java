package mfrf.magic_circle.gui.widgets;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.IRenderable;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class FigureBox extends Widget implements IGuiEventListener, IRenderable {

    //todo scale
    private ResourceLocation location;
    private int figureWidth;
    private int figureHeight;
    private int xOffset = 0;
    private int yOffset = 0;
    private float currentScale = 1;
    private float minScale;
    private float maxScale;


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
                maxScale = figureHeight;
            } else {
                minScale = (float) width / (float) figureWidth;
                maxScale = figureWidth;
            }
            currentScale = minScale;
        }

    }

    @Override
    public void render(MatrixStack matrixStack, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
        if (location != null) {
//            matrixStack.pushPose();
//            RenderSystem.color4f(1, 1, 1, 1);
//            matrixStack.scale(currentScale, currentScale, 1);
//            matrixStack.translate(xOffset, yOffset, 0);
//            matrixStack.popPose();
            Minecraft.getInstance().getTextureManager().bind(location);
//            int u = (int) (width / currentScale);
//            int v = (int) (height / currentScale);
            blit(matrixStack, x, y, 0, 0, width, height, width, height);
        }
    }

    @Override
    public boolean mouseDragged(double scaledMouseX, double scaledMouseY, int activeMouseButton, double deltaX, double deltaY) {
        if (location != null) {
            int boundaryX = (int) (figureWidth * currentScale - width);
            int boundaryY = (int) (figureHeight * currentScale - height);
            if (xOffset + scaledMouseX > boundaryX) {
                xOffset = boundaryX;
            } else {
                xOffset += scaledMouseX;
            }
            if (yOffset + scaledMouseY > boundaryY) {
                yOffset = boundaryY;
            } else {
                yOffset += scaledMouseY;
            }

            return true;
        }
        return false;
    }

    @Override
    public boolean mouseScrolled(double p_231043_1_, double p_231043_3_, double p_231043_5_) {
        if (location != null) {
            float scaled = (float) (currentScale + 10 * p_231043_5_);
            if (scaled > maxScale) {
                currentScale = maxScale;
            } else if (scaled < minScale) {
                currentScale = minScale;
            } else {
                currentScale = scaled;
            }
            return true;
        }
        return false;
    }

    public void setFigurePath(ResourceLocation location) throws IOException {

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
                maxScale = figureHeight;
            } else {
                minScale = (float) width / (float) figureWidth;
                maxScale = figureWidth;
            }
            currentScale = minScale;
        }
    }
}
