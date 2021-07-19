package mfrf.magic_circle.gui.engraver_table;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import mfrf.magic_circle.MagicCircle;
import mfrf.magic_circle.gui.ScreenBase;
import mfrf.magic_circle.magicutil.MagicNodeBase;
import mfrf.magic_circle.magicutil.datastructure.MagicStreamMatrixNByN;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.LinkedList;

public class EngraverTableScreen extends ScreenBase<EngraverTableContainer> {

    public EngraverTableScreen(EngraverTableContainer p_i51105_1_, PlayerInventory p_i51105_2_, ITextComponent p_i51105_3_) {
        super(p_i51105_1_, p_i51105_2_, p_i51105_3_, 184, 134, 256, 256);
    }

    @Override
    public void render(MatrixStack matrixStack, int x, int y, float partialTick) {
        super.render(matrixStack, x, y, partialTick);

        renderTooltip(matrixStack, x, y);
    }

    @Override
    public void init(Minecraft p_231158_1_, int p_231158_2_, int p_231158_3_) {
        super.init(p_231158_1_, p_231158_2_, p_231158_3_);

        VScrollBar nodeListBar = addWidget(new VScrollBar(33, 9, 8, 9, new TranslationTextComponent("magic_circle.gui.v_scroll_bar")));
        VScrollBar argumentListBar = addWidget(new VScrollBar(143, 9, 8, 9, new TranslationTextComponent("magic_circle.gui.v_scroll_bar")));
        WScrollBar nodeInstantBar = addWidget(new WScrollBar(0, 159, 176, 10, new TranslationTextComponent("magic_circle.gui.w_scroll_bar")));
        NodeList nodeList = addWidget(new NodeList(nodeListBar, new ArrayList<>(menu.knowledges.getUnlockedResearchs())));
        NodeInstanceList nodeInstanceList = addWidget(new NodeInstanceList(nodeInstantBar));
//        addWidget(new currentNodeMatrix())
    }

    @Override
    protected ResourceLocation getTexture() {
        return new ResourceLocation(MagicCircle.MOD_ID, "textures/gui/magic_engraver.png");
    }

    private class VScrollBar extends Widget {
        int currentPos = 0;

        public VScrollBar(int x, int y, int width, int height, ITextComponent title) {
            super(x, y, width, height, title);
        }

        @Override
        public void render(MatrixStack matrixStack, int mouseX, int mouseY, float p_230430_4_) {
            minecraft.getTextureManager().bind(getTexture());
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.enableDepthTest();

            blit(matrixStack, this.x, this.y + currentPos, 194, 0, 8, 9, 256, 256);
        }

        public float getProgress() {
            return (float) currentPos / (float) height;
        }

        @Override
        protected void onDrag(double mouseX, double mouseY, double dragX, double dragY) {
            double dragPos = mouseY - this.y;
            if (dragPos >= height) {
                currentPos = height;
            } else {
                currentPos = (int) dragPos;
            }
        }
    }


    private class WScrollBar extends Widget {
        int currentPos = 0;

        public WScrollBar(int x, int y, int width, int height, ITextComponent title) {
            super(x, y, width, height, title);
        }

        @Override
        public void render(MatrixStack matrixStack, int mouseX, int mouseY, float p_230430_4_) {
            minecraft.getTextureManager().bind(getTexture());
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.enableDepthTest();

            blit(matrixStack, this.x, this.y + currentPos, 194, 0, 8, 9, 256, 256);
        }

        public float getProgress() {
            return (float) currentPos / (float) height;
        }

        @Override
        protected void onDrag(double mouseX, double mouseY, double dragX, double dragY) {
            double dragPos = mouseX - this.x;
            if (dragPos >= width) {
                currentPos = width;
            } else {
                currentPos = (int) dragPos;
            }
        }
    }

    private class NodeList extends Widget {
        private ArrayList<Node> nodes = new ArrayList<>();
        private VScrollBar scrollBar;

        public NodeList(VScrollBar scrollBar, ArrayList<String> nodes) {
            super(7, 7, 21, 90, new TranslationTextComponent("magic_circle.gui.node_list"));
            this.scrollBar = scrollBar;
            for (int i = 0; i < nodes.size(); i++) {
                this.nodes.add(new Node(7, 7 + 9 * i, new TranslationTextComponent("magic_circle.gui.node_list.node"), nodes.get(i)));
            }
        }

        @Override
        public void render(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
            super.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
        }

        public class Node extends Widget {
            private String nodeName;
            private boolean dragging = false;

            public Node(int p_i232254_1_, int p_i232254_2_, ITextComponent p_i232254_5_, String nodeName) {
                super(p_i232254_1_, p_i232254_2_, 21, 9, p_i232254_5_);
                this.nodeName = nodeName;
            }

            @Override
            public void render(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
                super.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);


                if (this.dragging) {
                    minecraft.textureManager.bind(getTexture());
                    blit(p_230430_1_, p_230430_2_ - 2, p_230430_3_ - 2, 196, 9, 4, 4, 256, 256);
                }
            }

            @Override
            public void onRelease(double p_231000_1_, double p_231000_3_) {
                super.onRelease(p_231000_1_, p_231000_3_);
                dragging = false;

                //todo onPlace
            }

            protected void place() {

            }

            @Override
            protected void onDrag(double p_230983_1_, double p_230983_3_, double p_230983_5_, double p_230983_7_) {
                super.onDrag(p_230983_1_, p_230983_3_, p_230983_5_, p_230983_7_);
                dragging = true;
            }
        }
    }

    private class currentNodeMatrix extends Widget {
        private NodeInstanceList instanceList;
        private MagicStreamMatrixNByN connectivityMatrix;

        public currentNodeMatrix(NodeInstanceList list) {
            super(45, 15, 94, 69, new TranslationTextComponent("magic_circle.gui.node_matrix"));
            this.instanceList = list;
            LinkedList<MagicNodeBase> nodeBases = list.nodeBases;
            int size = nodeBases.size();
            connectivityMatrix = new MagicStreamMatrixNByN(size, size);
            for (int i = 0; i < size; i++) {
                MagicNodeBase nodeBase = nodeBases.get(i);
                if (nodeBase.getLeftNode() != null) {
                    int j = nodeBases.indexOf(nodeBase.getLeftNode());
                    if (j != -1) {
                        connectivityMatrix.set(i, j, j);
                    }
                }
            }
        }

        @Override
        public void render(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
            super.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);

        }

        public void newNode(int x, int y, MagicNodeBase nodeBase) {
            instanceList.add(nodeBase);
            connectivityMatrix.reshape(connectivityMatrix.numRows + 1, connectivityMatrix.numCols + 1, true);
        }
    }

    private class NodeInstanceList extends Widget {
        private LinkedList<MagicNodeBase> nodeBases = new LinkedList<>();
        private WScrollBar scrollBar;

        public NodeInstanceList(WScrollBar scrollBar) {
            super(3, 136, 176, 21, new TranslationTextComponent("magic_circle.ui.node_instance_list"));
            this.scrollBar = scrollBar;
        }

        public void add(MagicNodeBase nodeBase) {
            nodeBases.add(nodeBase);
        }

        @Override
        public void render(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
            super.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
            scrollBar.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
        }

    }


}
