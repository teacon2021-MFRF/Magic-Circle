package mfrf.magic_circle.gui.research_table;

import com.mojang.blaze3d.matrix.MatrixStack;
import mfrf.magic_circle.MagicCircle;
import mfrf.magic_circle.gui.ScreenBase;
import mfrf.magic_circle.gui.widgets.FigureBox;
import mfrf.magic_circle.json_recipe_configs.JsonConfigItemResearch;
import mfrf.magic_circle.json_recipe_configs.research_test.ResearchTestBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.io.IOException;
import java.util.Optional;

public class ResearchTableScreen extends ScreenBase<ResearchTableContainer> {
    private TextFieldWidget answer;
    private FigureBox figure;

    public ResearchTableScreen(ResearchTableContainer p_i51105_1_, PlayerInventory p_i51105_2_, ITextComponent p_i51105_3_) {
        super(p_i51105_1_, p_i51105_2_, p_i51105_3_, 300, 250);
    }

    @Override
    public void render(MatrixStack matrixStack, int x, int y, float partialTick) {
        super.render(matrixStack, x, y, partialTick);
        Optional<JsonConfigItemResearch> itemResearchContain = menu.getItemResearchContain();
        Optional<ResearchTestBase.Serializer.DataContainer> testPaperResearchContain = menu.getTestPaperResearchContain();
        if (testPaperResearchContain.isPresent()) {
            ResearchTestBase.Serializer.DataContainer dataContainer = testPaperResearchContain.get();
            try {
                ResourceLocation figurePath = ResourceLocation.tryParse(dataContainer.figure);
                figure.setFigurePath(figurePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (itemResearchContain.isPresent()) {
//            JsonConfigItemResearch jsonConfigItemResearch = itemResearchContain.get();
//            jsonConfigItemResearch.getResearchContain();
            //todo render ItemResearch
        }

        answer.render(matrixStack, x, y, partialTick);
        figure.render(matrixStack, x, y, partialTick);

        renderTooltip(matrixStack, x, y);
    }

    @Override
    public boolean mouseClicked(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
        return super.mouseClicked(p_231044_1_, p_231044_3_, p_231044_5_);
    }

    //where is my drawGuiContainerForegroundLayer???
    //todo editable

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void init(Minecraft p_231158_1_, int p_231158_2_, int p_231158_3_) {
        super.init(p_231158_1_, p_231158_2_, p_231158_3_);
        answer = new TextFieldWidget(p_231158_1_.font, getGuiLeft() + 91, getGuiTop() + 113, 176, 19, new StringTextComponent("hello"));
        answer.setEditable(true);
        answer.setVisible(true);
        this.addWidget(answer);
        try {
            figure = this.addWidget(new FigureBox(getGuiLeft() + 91, getGuiTop() + 28, 177, 69, new StringTextComponent("figure"), null));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //todo render picture,writeable.
    }

    @Override
    protected ResourceLocation getTexture() {
        return new ResourceLocation(MagicCircle.MOD_ID, "textures/gui/research_table.png");
    }

}
