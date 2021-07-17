package mfrf.magic_circle.gui.engraver_table;

import com.mojang.blaze3d.matrix.MatrixStack;
import mfrf.magic_circle.MagicCircle;
import mfrf.magic_circle.gui.ScreenBase;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class EngraverTableScreen extends ScreenBase<EngraverTableContainer> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(MagicCircle.MOD_ID, "gui/magic_engraver.png");

    public EngraverTableScreen(EngraverTableContainer p_i51105_1_, PlayerInventory p_i51105_2_, ITextComponent p_i51105_3_, int xSize, int ySize) {
        super(p_i51105_1_, p_i51105_2_, p_i51105_3_, xSize, ySize);
    }

    @Override
    public void render(MatrixStack matrixStack, int x, int y, float partialTick) {
        super.render(matrixStack, x, y, partialTick);

        renderTooltip(matrixStack, x, y);
    }

    @Override
    public void init(Minecraft p_231158_1_, int p_231158_2_, int p_231158_3_) {
        super.init(p_231158_1_, p_231158_2_, p_231158_3_);
    }

    @Override
    protected ResourceLocation getTexture() {
        return TEXTURE;
    }
}
