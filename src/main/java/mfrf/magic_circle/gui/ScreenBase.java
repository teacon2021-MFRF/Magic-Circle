package mfrf.magic_circle.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public abstract class ScreenBase<T extends ContainerBase> extends ContainerScreen<T> {

    public ScreenBase(T p_i51105_1_, PlayerInventory p_i51105_2_, ITextComponent p_i51105_3_, int xSize, int ySize) {
        super(p_i51105_1_, p_i51105_2_, p_i51105_3_);
        this.imageWidth = xSize;
        this.imageHeight = ySize;
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int x, int y) {
        this.renderBackground(matrixStack);

        ResourceLocation texture = getTexture();

        if (texture != null) {
            this.minecraft.getTextureManager().getTexture(texture);
        }
        int i = (this.width - this.getXSize()) / 2;
        int j = (this.height - this.getYSize()) / 2;
        blit(matrixStack, i, j, 0, 0, getXSize(), getYSize(), imageWidth, imageHeight);
    }

    protected abstract ResourceLocation getTexture();

}
