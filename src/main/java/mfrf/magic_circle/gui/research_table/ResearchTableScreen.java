package mfrf.magic_circle.gui.research_table;

import com.mojang.blaze3d.matrix.MatrixStack;
import mfrf.magic_circle.MagicCircle;
import mfrf.magic_circle.gui.ScreenBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class ResearchTableScreen extends ScreenBase<ResearchTableContainer> {
    private TextFieldWidget answer;

    public ResearchTableScreen(ResearchTableContainer p_i51105_1_, PlayerInventory p_i51105_2_, ITextComponent p_i51105_3_) {
        super(p_i51105_1_, p_i51105_2_, p_i51105_3_, 300, 250);
    }

    @Override
    public void render(MatrixStack matrixStack, int x, int y, float partialTick) {
        super.render(matrixStack, x, y, partialTick);
        answer.render(matrixStack,x,y,partialTick);
        answer.renderButton(matrixStack, x, y, partialTick);
        answer.renderToolTip(matrixStack,x,y);
        answer.setTextColor(0xFF2002);
        answer.setEditable(true);
        answer.setValue("123123");
        renderTooltip(matrixStack, x, y);
    }

    @Override
    public boolean mouseClicked(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
        answer.mouseClicked(p_231044_1_, p_231044_3_, p_231044_5_);
        return super.mouseClicked(p_231044_1_, p_231044_3_, p_231044_5_);
    }

    //where is my drawGuiContainerForegroundLayer???
    //todo editable


    @Override
    public void tick() {
        super.tick();
        answer.tick();
    }

    @Override
    public void init(Minecraft p_231158_1_, int p_231158_2_, int p_231158_3_) {
        super.init(p_231158_1_, p_231158_2_, p_231158_3_);
        answer = new TextFieldWidget(p_231158_1_.font, getGuiLeft()+91, getGuiTop()+113, 176, 19, new StringTextComponent("hello"));
        answer.setVisible(true);
        answer.setEditable(true);
        //todo render picture,writeable.
    }

    @Override
    protected ResourceLocation getTexture() {
        return new ResourceLocation(MagicCircle.MOD_ID, "textures/gui/research_table.png");
    }

}
