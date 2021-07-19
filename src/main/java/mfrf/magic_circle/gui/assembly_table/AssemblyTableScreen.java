package mfrf.magic_circle.gui.assembly_table;

import com.mojang.blaze3d.matrix.MatrixStack;
import mfrf.magic_circle.MagicCircle;
import mfrf.magic_circle.gui.ScreenBase;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class AssemblyTableScreen extends ScreenBase<AssemblyTableContainer> {
    public AssemblyTableScreen(AssemblyTableContainer p_i51105_1_, PlayerInventory p_i51105_2_, ITextComponent p_i51105_3_) {
        super(p_i51105_1_, p_i51105_2_, p_i51105_3_, 300, 250, 300, 250);
    }

    @Override
    public void render(MatrixStack matrixStack, int x, int y, float partialTick) {
        super.render(matrixStack, x, y, partialTick);

        renderTooltip(matrixStack, x, y);
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    protected ResourceLocation getTexture() {
        return new ResourceLocation(MagicCircle.MOD_ID, "textures/gui/assembly_table_2.png");
    }
}
