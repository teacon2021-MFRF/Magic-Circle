package mfrf.magic_circle.gui.research_table;

import com.mojang.blaze3d.matrix.MatrixStack;
import icyllis.modernui.graphics.Canvas;
import icyllis.modernui.graphics.Paint;
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
    public void render(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
        super.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setRGBA(0,0,255,128);
        paint.setFeatherRadius(0);
        Canvas.getInstance().drawArc(32, 32, 16,0,1.5f, paint);
        //todo 兄啊这玩意用不了啊
    }

    @Override
    protected ResourceLocation getTexture() {
        return null;
    }

}
