package mfrf.magic_circle.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public abstract class ScreenBase<T extends ContainerBase> extends ContainerScreen<T> {
    private int xSize;
    private int ySize;

    public ScreenBase(T p_i51105_1_, PlayerInventory p_i51105_2_, ITextComponent p_i51105_3_, int xSize, int ySize) {
        super(p_i51105_1_, p_i51105_2_, p_i51105_3_);
        this.xSize = xSize;
        this.imageWidth = xSize;
        this.imageHeight = ySize;
        this.ySize = ySize;
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int x, int y) {
        this.renderBackground(matrixStack);
        ResourceLocation texture = getTexture();

        if (texture != null) {
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1F);
            RenderSystem.enableAlphaTest();
            this.minecraft.getTextureManager().bind(getTexture());
            int relX = (this.width - xSize) / 2;
            int relY = (this.height - ySize) / 2;
            this.blit(matrixStack, relX, relY, 0, 0, xSize, ySize, xSize, ySize);
        }
    }

    @Override
    protected void renderLabels(MatrixStack p_230451_1_, int p_230451_2_, int p_230451_3_) {
//        super.renderLabels(p_230451_1_, p_230451_2_, p_230451_3_);
    }

    @Override
    public void render(MatrixStack matrixStack, int x, int y, float partialTick) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, x, y, partialTick);
    }

    protected abstract ResourceLocation getTexture();

}
