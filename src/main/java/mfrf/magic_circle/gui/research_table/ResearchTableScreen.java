package mfrf.magic_circle.gui.research_table;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import mfrf.magic_circle.MagicCircle;
import mfrf.magic_circle.gui.ScreenBase;
import mfrf.magic_circle.gui.widgets.FigureBox;
import mfrf.magic_circle.json_recipe_configs.JsonConfigItemResearch;
import mfrf.magic_circle.json_recipe_configs.research_test.ResearchTestBase;
import mfrf.magic_circle.network.send_answer.SendAnswer;
import mfrf.magic_circle.network.send_answer.SendPack;
import mfrf.magic_circle.registry_lists.JsonConfigs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.io.IOException;
import java.util.Optional;

public class ResearchTableScreen extends ScreenBase<ResearchTableContainer> {
    private TextFieldWidget answer;
    private FigureBox figure;
    private JsonConfigItemResearch item_research;
    private Button button;

    public ResearchTableScreen(ResearchTableContainer p_i51105_1_, PlayerInventory p_i51105_2_, ITextComponent p_i51105_3_) {
        super(p_i51105_1_, p_i51105_2_, p_i51105_3_, 300, 250, 400, 300);
    }

    @Override
    public void render(MatrixStack matrixStack, int x, int y, float partialTick) {
        super.render(matrixStack, x, y, partialTick);


        if (answer != null) {
            answer.render(matrixStack, x, y, partialTick);
        }
        if (figure != null) {
            figure.render(matrixStack, x, y, partialTick);
        }
        if (item_research != null) {
            font.draw(matrixStack, new TranslationTextComponent("gui.research.bagua_require").getString() + ":" + item_research.getRequiredBaguaKnowledge() + "/" + menu.getKnowledge().getBagua(), this.getGuiLeft() - 100, this.getGuiTop(), 0xffffffff);
            font.draw(matrixStack, new TranslationTextComponent("gui.research.math_require").getString() + ":" + item_research.getMathKnowledge() + "/" + menu.getKnowledge().getBagua(), this.getGuiLeft() - 100, this.getGuiTop() + 10, 0xffffffff);
            font.draw(matrixStack, new TranslationTextComponent("gui.research.mystery_require").getString() + ":" + item_research.getRequiredMysteryKnowledge() + "/" + menu.getKnowledge().getBagua(), this.getGuiLeft() - 100, this.getGuiTop() + 20, 0xffffffff);
            font.draw(matrixStack, new TranslationTextComponent("gui.research.physical_require").getString() + ":" + item_research.getRequiredPhysicalKnowledge() + "/" + menu.getKnowledge().getBagua(), this.getGuiLeft() - 100, this.getGuiTop() + 30, 0xffffffff);
        }

        renderTooltip(matrixStack, x, y);
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public boolean mouseDragged(double p_231045_1_, double p_231045_3_, int p_231045_5_, double p_231045_6_, double p_231045_8_) {
        if (figure != null && figure.isMouseOver(p_231045_1_, p_231045_3_)) {
            return figure.mouseDragged(p_231045_1_, p_231045_3_, p_231045_5_, p_231045_6_, p_231045_8_);
        } else {
            return super.mouseDragged(p_231045_1_, p_231045_3_, p_231045_5_, p_231045_6_, p_231045_8_);
        }
    }

    @Override
    public void init(Minecraft minecraft, int x, int y) {
        super.init(minecraft, x, y);
        answer = new TextFieldWidget(minecraft.font, getGuiLeft() + 91, getGuiTop() + 113, 176, 19, new StringTextComponent("hello"));
        answer.setEditable(true);
        answer.setVisible(true);
        this.addWidget(answer);


        Optional<JsonConfigItemResearch> itemResearchContain = menu.getItemResearchContain();
        Optional<ResearchTestBase.Serializer.DataContainer> testPaperResearchContain = menu.getTestPaperResearchContain();
        if (testPaperResearchContain.isPresent()) {
            ResearchTestBase.Serializer.DataContainer dataContainer = testPaperResearchContain.get();
            try {
                ResourceLocation figurePath = ResourceLocation.tryParse(dataContainer.figure);
                figure = this.addWidget(new FigureBox(getGuiLeft() + 91, getGuiTop() + 28, 177, 70, new StringTextComponent("figure"), figurePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (itemResearchContain.isPresent()) {
            item_research = itemResearchContain.get();
            ResourceLocation questionLocation = item_research.getQuestionLocation();
            if (questionLocation != null) {
                Optional<ResearchTestBase> any = menu.getResearch_table().getLevel().getRecipeManager()
                        .getAllRecipesFor(JsonConfigs.Type.RESEARCH_TEST_JSONCONFIG_TYPE)
                        .stream()
                        .filter(researchTestBase -> researchTestBase.getId().equals(questionLocation))
                        .findFirst();
                if (any.isPresent()) {
                    ResearchTestBase researchTestBase = any.get();
                    ResearchTestBase dataContainer = researchTestBase;
                    try {
                        ResourceLocation figurePath = ResourceLocation.tryParse(dataContainer.getFigure());
                        figure = this.addWidget(new FigureBox(getGuiLeft() + 91, getGuiTop() + 28, 177, 70, new StringTextComponent("figure"), figurePath));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        button = this.addButton(new Button(this.getGuiLeft() + 222, this.getGuiTop() + 180, 15, 15, new TranslationTextComponent("magic_circle.gui.submit"), p_onPress_1_ -> {
            SendAnswer.INSTANCE.sendToServer(new SendPack(answer.getValue(), menu.getResearch_table().getBlockPos()));
        }));
        button.visible = true;

        //todo render writeable.
    }

    @Override
    protected ResourceLocation getTexture() {
        return new ResourceLocation(MagicCircle.MOD_ID, "textures/gui/research_table.png");
    }

    private class Button extends net.minecraft.client.gui.widget.button.Button {

        public Button(int p_i232255_1_, int p_i232255_2_, int p_i232255_3_, int p_i232255_4_, ITextComponent p_i232255_5_, IPressable p_i232255_6_) {
            super(p_i232255_1_, p_i232255_2_, p_i232255_3_, p_i232255_4_, p_i232255_5_, p_i232255_6_);
        }

        @Override
        public void renderButton(MatrixStack p_230431_1_, int p_230431_2_, int p_230431_3_, float p_230431_4_) {
            Minecraft minecraft = Minecraft.getInstance();
            minecraft.getTextureManager().bind(getTexture());
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
            int i = this.getYImage(this.isHovered()) - 1;
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.enableDepthTest();
            this.blit(p_230431_1_, this.x, this.y, 385, 15 - 15 * i, this.width, this.height,400,300);
            this.renderBg(p_230431_1_, minecraft, p_230431_2_, p_230431_3_);
            int j = getFGColor();

            if (this.isHovered()) {
                this.renderToolTip(p_230431_1_, p_230431_2_, p_230431_3_);
            }
        }
    }

}
