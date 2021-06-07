package mfrf.magic_circle.gui.research_table;

import com.mojang.blaze3d.matrix.MatrixStack;
import mfrf.magic_circle.gui.ContainerBase;
import mfrf.magic_circle.gui.ScreenBase;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ResearchTableScreen extends ScreenBase<ResearchTableContainer> {

    public ResearchTableScreen(ResearchTableContainer p_i51105_1_, PlayerInventory p_i51105_2_, ITextComponent p_i51105_3_) {
        super(p_i51105_1_, p_i51105_2_, p_i51105_3_, 114, 123);
    }

    @Override
    protected ResourceLocation getTexture() {
        return null;
    }

}
