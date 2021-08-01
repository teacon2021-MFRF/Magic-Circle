package mfrf.magic_circle.block.magic_assemby_table;

import com.mojang.blaze3d.matrix.MatrixStack;
import mfrf.magic_circle.block.technical_blocks.BlockRune;
import mfrf.magic_circle.registry_lists.Blocks;
import mfrf.magic_circle.registry_lists.Capabilities;
import mfrf.magic_circle.registry_lists.Items;
import mfrf.magic_circle.rendering.MagicCircleComponentBase;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.world.World;
import net.minecraftforge.client.model.data.EmptyModelData;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class TERMagicModelAssemblyTable extends TileEntityRenderer<TileMagicModelAssemblyTable> {
    private static ArrayList<ItemStack> items = new ArrayList<>(); //temp todo delete

    static{
        items.add(new ItemStack(Items.SAPPSIRE.get()));
        items.add(new ItemStack(Items.STARLIGHT_RUBY.get()));
        items.add(new ItemStack(Items.SUNSTONE.get()));
        items.add(new ItemStack(Items.RUBY.get()));
        items.add(new ItemStack(Items.MAGIC_CRYSTAL.get()));
        items.add(new ItemStack(Items.RUBY.get()));
        items.add(new ItemStack(Items.STARTLIGHT_SAPPSIRE.get()));
        items.add(new ItemStack(Items.MAGIC_CRYSTAL.get()));
    }

    public TERMagicModelAssemblyTable(TileEntityRendererDispatcher p_i226006_1_) {
        super(p_i226006_1_);
    }

    @Override
    public void render(TileMagicModelAssemblyTable tileMagicModelAssemblyTable, float p_225616_2_, MatrixStack matrixStack, IRenderTypeBuffer bufferIn, int p_225616_5_, int p_225616_6_) {
//        BlockPos blockPos = tileMagicModelAssemblyTable.getBlockPos();
        World level = tileMagicModelAssemblyTable.getLevel();
//        Random random = level.getRandom();
//        ItemStack item = tileMagicModelAssemblyTable.inventory.getItem(0);
//        if (!item.isEmpty()) {
//            if (item.getCapability(Capabilities.MAGICAL_ITEM).isPresent()) {
//                ArrayList<ItemStack> items = new ArrayList<>(); //temp
//                AtomicInteger magicCount = new AtomicInteger();
//                item.getCapability(Capabilities.MAGICAL_ITEM).ifPresent(iMagicalItem -> {
//                    items.addAll(iMagicalItem.getEffectContainer().slots.stream().map(slot -> slot.itemStack).collect(Collectors.toList()));
//                    magicCount.set(iMagicalItem.magics().size());
//                });
//
                BlockRendererDispatcher blockRenderer = Minecraft.getInstance().getBlockRenderer();
                BlockState blockState = Blocks.RUNE.get().defaultBlockState();
//
//                if (magicCount.get() > 24)
//                    magicCount.set(24);

//                double angle1 = Math.PI * 2 / magicCount.get();
                double angle1 = Math.PI * 2 / 24;
                double angle2 = Math.PI * 2 / items.size();

                matrixStack.translate(0.45, 0.6, 0.45);

                for (int i = 1; i <= items.size(); i++) {
                    BlockState state = blockState.setValue(BlockRune.STATE, 24 + i);

                    matrixStack.pushPose();
//                    matrixStack.translate(Math.cos(angle2 * i + level.getGameTime() / 1.2) * 1.5, Math.sin((level.getGameTime() % 1000) / 1.5 + i) / 10, Math.sin(angle2 * i + level.getGameTime() / 1.2) * 1.5);
                    matrixStack.translate(Math.cos(angle2 * i + level.getGameTime() / 100d) * 0.5, Math.sin((level.getGameTime() % 1000) / 2.7 + i) / 100, Math.sin(angle2 * i + level.getGameTime() / 100d) * 0.5);
                    blockRenderer.renderBlock(state, matrixStack, bufferIn, MagicCircleComponentBase.maxLight, p_225616_6_, EmptyModelData.INSTANCE);

                    matrixStack.translate(.05, 0.3, .05);
                    matrixStack.scale(0.1f, 0.1f, 0.1f);
                    matrixStack.mulPose(new Quaternion(0, -level.getGameTime() / 10f, 0, true));
                    ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
                    ItemStack stack = items.get(i - 1);
                    IBakedModel ibakedmodel = itemRenderer.getModel(stack, level, null);
                    itemRenderer.render(stack, ItemCameraTransforms.TransformType.FIXED, true, matrixStack, bufferIn, MagicCircleComponentBase.maxLight, p_225616_6_, ibakedmodel);
                    matrixStack.popPose();
                }

//                for (int i = 1; i <= magicCount.get(); i++) {
                for (int i = 1; i <= 24; i++) {
                    BlockState state = blockState.setValue(BlockRune.STATE, i);

                    matrixStack.pushPose();
                    matrixStack.translate(Math.cos(angle1 * i + level.getGameTime() / 100d) * 2.5, Math.sin((level.getGameTime() % 1000) / 2.7 + i) / 100 + 0.3, Math.sin(angle1 * i + level.getGameTime() / 100d) * 2.5);
                    blockRenderer.renderBlock(state, matrixStack, bufferIn, MagicCircleComponentBase.maxLight, p_225616_6_, EmptyModelData.INSTANCE);
                    matrixStack.popPose();
                }

//            }

//            matrixStack.pushPose();
//            matrixStack.translate(0.5, 0.9, 0.5);
//            matrixStack.scale(0.3f, 0.3f, 0.3f);
//            matrixStack.mulPose(new Quaternion(0, -level.getGameTime(), 0, true));
//            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
//            IBakedModel ibakedmodel = itemRenderer.getModel(item, level, null);
//            itemRenderer.render(item, ItemCameraTransforms.TransformType.FIXED, true, matrixStack, bufferIn, MagicCircleComponentBase.maxLight, p_225616_6_, ibakedmodel);
//            matrixStack.popPose();

//        }
    }

}
