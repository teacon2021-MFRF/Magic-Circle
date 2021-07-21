package mfrf.magic_circle.gui.engraver_table;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import mfrf.magic_circle.MagicCircle;
import mfrf.magic_circle.gui.ScreenBase;
import mfrf.magic_circle.gui.widgets.Argument;
import mfrf.magic_circle.magicutil.MagicModelBase;
import mfrf.magic_circle.magicutil.MagicNodeBase;
import mfrf.magic_circle.magicutil.datastructure.MagicStreamMatrixNByN;
import mfrf.magic_circle.magicutil.nodes.BeginNodeBase;
import mfrf.magic_circle.magicutil.nodes.behaviornode.ThrowBehaviorNode;
import mfrf.magic_circle.network.gui_model_sync.SendPack;
import mfrf.magic_circle.network.gui_model_sync.SyncModelData;
import mfrf.magic_circle.util.CachedEveryThingForClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.function.Supplier;

public class EngraverTableScreen extends ScreenBase<EngraverTableContainer> {
    public static HashMap<String, Supplier<MagicNodeBase>> nodeBaseHashMap = new HashMap<>();

    static {
        nodeBaseHashMap.put("project_item", ThrowBehaviorNode::new);
        nodeBaseHashMap.put("begin", BeginNodeBase::new);
    }

    private VScrollBar nodeListBar;
    private NodeList nodeList;
    private WScrollBar nodeInstantBar;
    private NodeInstanceList nodeInstanceList;
    private Object currentNodeMatrix;
    private VScrollBar argumentListBar;
    private NodeArgumentList nodeArgumentList;
    private ButtonTemplate submitButton;
    private ButtonTemplate deleteButton;
    private ButtonTemplate leftModel;
    private ButtonTemplate rightModel;
    private ButtonTemplate duplicateModel;
    private ButtonTemplate removeModel;
    private int currentIndex = 0;
    private ArrayList<String> modelNames;

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

        this.nodeListBar = addWidget(new VScrollBar(33, 9, 8, 9, new TranslationTextComponent("magic_circle.gui.v_scroll_bar")));
        this.nodeList = addWidget(new NodeList(nodeListBar, new ArrayList<>(menu.knowledges.getUnlockedResearchs())));

        this.nodeInstantBar = addWidget(new WScrollBar(0, 159, 176, 10, new TranslationTextComponent("magic_circle.gui.w_scroll_bar")));
        this.nodeInstanceList = addWidget(new NodeInstanceList(nodeInstantBar));
        this.currentNodeMatrix = addWidget(new currentNodeMatrix(nodeInstanceList));

        this.argumentListBar = addWidget(new VScrollBar(143, 9, 8, 9, new TranslationTextComponent("magic_circle.gui.v_scroll_bar")));
        this.nodeArgumentList = addWidget(new NodeArgumentList(argumentListBar));

        this.submitButton = this.addButton(new ButtonTemplate(8, 113, 224, 0, 15, 15, p_onPress_1_ -> {
            //todo sendPackage
        }));

        this.deleteButton = this.addButton(new ButtonTemplate(161, 113, 209, 0, 15, 15, p_onPress_1_ -> {
            //todo clear cache;
        }));

        this.leftModel = this.addButton(new ButtonTemplate(45, 4, 196, 74, 19, 8, p_onPress_1_ -> {
            if (currentIndex > 0) {
                currentIndex--;
            }
        }));
        this.rightModel = this.addButton(new ButtonTemplate(64, 4, 196, 66, 19, 8, p_onPress_1_ -> {
            int size = modelNames.size();
            if (currentIndex < size) {
                currentIndex++;
            }
        }));
        this.duplicateModel = this.addButton(new ButtonTemplate(83, 4, 224, 66, 9, 8, p_onPress_1_ -> {
            HashMap<String, MagicModelBase> modelMap = getModelMap();
            if (modelNames.size() - 1 >= currentIndex) {
                String s = modelNames.get(currentIndex);
                SyncModelData.INSTANCE.sendToServer(new SendPack(modelMap.get(s).serializeNBT(), menu.playerEntity.getUUID(), SendPack.State.UPDATE, s + "_copy"));
            }
            if (modelNames.size() == 0) {
                SyncModelData.INSTANCE.sendToServer(new SendPack(new MagicModelBase(null).serializeNBT(), menu.playerEntity.getUUID(), SendPack.State.UPDATE, "created"));
            }
        }));
        this.removeModel = this.addButton(new ButtonTemplate(92, 4, 224, 74, 9, 8, p_onPress_1_ -> {
            if (modelNames.size() != 0 && modelNames.size() - 1 >= currentIndex) {
                SyncModelData.INSTANCE.sendToServer(new SendPack(new CompoundNBT(), menu.playerEntity.getUUID(), SendPack.State.DELETE, modelNames.get(currentIndex)));
            }
        }));


    }

    @Override
    public void tick() {
        super.tick();
        if (currentIndex > modelNames.size() - 1) {
            currentIndex = modelNames.size() - 1;
        }
    }

    private HashMap<String, MagicModelBase> getModelMap() {
        HashMap<String, MagicModelBase> orCreateModels = CachedEveryThingForClient.getOrCreateModels(menu.playerEntity.getUUID());
        modelNames = new ArrayList<>(orCreateModels.keySet());
        return orCreateModels;
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
            LinkedList<NodeInstanceList.NodeInstance<?>> nodeBases = list.nodeBases;
            int size = nodeBases.size();
            connectivityMatrix = new MagicStreamMatrixNByN(size, size);
            for (int i = 0; i < size; i++) {
                MagicNodeBase nodeBase = nodeBases.get(i).nodeBase;
                if (nodeBase.getLeftNode() != null) {
                    int j = nodeBases.indexOf(nodeBase.getLeftNode());
                    if (j != -1) {
                        connectivityMatrix.set(i, j, 1);
                    }
                }
                if (nodeBase.getRightNode() != null) {
                    int j = nodeBases.indexOf(nodeBase.getRightNode());
                    if (j != -1) {
                        connectivityMatrix.set(i, j, 2);
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
        private LinkedList<NodeInstance<?>> nodeBases = new LinkedList<>();
        private WScrollBar scrollBar;

        public NodeInstanceList(WScrollBar scrollBar) {
            super(3, 136, 176, 21, new TranslationTextComponent("magic_circle.ui.node_instance_list"));
            this.scrollBar = scrollBar;
        }

        public void add(MagicNodeBase nodeBase) {
            nodeBases.add(new NodeInstance<>(nodeBase));
        }

        @Override
        public void render(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
            super.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
            scrollBar.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
        }

        public class NodeInstance<T extends MagicNodeBase> extends Widget {
            public MagicNodeBase nodeBase;

            public NodeInstance(MagicNodeBase nodeBase) {
                super(0, 0, 25, 21, new TranslationTextComponent("magic_circle.gui.node_instance"));
                this.nodeBase = nodeBase;
            }

            @Override
            public void render(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
                FontRenderer font = EngraverTableScreen.this.font;
//                blit(p_230430_1_,); //render index
            }

        }


    }

    public class NodeArgumentList extends Widget {
        ArrayList<Argument<?>> argumentArrayList = new ArrayList<>();
        private VScrollBar scrollBar;

        public NodeArgumentList(VScrollBar scrollBar) {
            super(156, 7, 21, 90, new TranslationTextComponent("magic_circle.gui.node_argument_list"));
            this.scrollBar = scrollBar;
        }

        public void setArgumentArrayList(ArrayList<Argument<?>> argumentArrayList) {
            this.argumentArrayList = argumentArrayList;
        }
    }

    private class ButtonTemplate extends Button {

        private final int v;
        private final int u;

        public ButtonTemplate(int x, int y, int u, int v, int w, int h, IPressable p_i232255_6_) {
            super(x, y, w, h, new TranslationTextComponent("submit"), p_i232255_6_);
            this.u = u;
            this.v = v;
        }

        @Override
        public void renderButton(MatrixStack p_230431_1_, int p_230431_2_, int p_230431_3_, float p_230431_4_) {
            FontRenderer fontrenderer = minecraft.font;
            minecraft.getTextureManager().bind(getTexture());
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.enableDepthTest();
            if (isHovered) {
                this.blit(p_230431_1_, this.x, this.y, u, v, 256, 256);
            }
        }
    }

    //todo render it

    public enum ArgumentType {
        STRING, BOOLEAN, INTEGER, FLOAT, DOUBLE;

        ArgumentType() {
        }

        public Object tryParse(String string) {
            try {

                switch (this) {
                    case FLOAT: {
                        return Float.parseFloat(string);
                    }
                    case DOUBLE: {
                        return Double.parseDouble(string);
                    }
                    case STRING: {
                        return string;
                    }
                    case BOOLEAN: {
                        return Boolean.parseBoolean(string);
                    }
                    case INTEGER: {
                        return Integer.parseInt(string);
                    }
                }
            } catch (Exception e) {

            }
            return null;
        }
    }
}
