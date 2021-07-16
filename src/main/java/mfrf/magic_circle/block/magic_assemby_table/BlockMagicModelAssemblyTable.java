package mfrf.magic_circle.block.magic_assemby_table;

import mfrf.magic_circle.block.BlockBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.Nullable;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

import java.util.List;

public class BlockMagicModelAssemblyTable extends BlockBase {
    public BlockMagicModelAssemblyTable(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }
    public static final VoxelShape ASSEMBLY_TABLE_SHAPE1 = Block.box(2, 0, 2, 14, 2, 14);
    public static final VoxelShape ASSEMBLY_TABLE_SHAPE2 = Block.box(4, 2, 4, 12, 3, 12);
    public static final VoxelShape ASSEMBLY_TABLE_SHAPE3 = Block.box(5, 3, 5, 11, 10, 11);
    private static final VoxelShape ASSEMBLY_TABLE_SHAPE = VoxelShapes.or(ASSEMBLY_TABLE_SHAPE1, ASSEMBLY_TABLE_SHAPE2, ASSEMBLY_TABLE_SHAPE3);
    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public ActionResultType use(BlockState blockState, World world, BlockPos pos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        if (!world.isClientSide() && hand == Hand.MAIN_HAND) {
            TileMagicModelAssemblyTable blockEntity = (TileMagicModelAssemblyTable) world.getBlockEntity(pos);
            ItemStack itemInHand = playerEntity.getItemInHand(hand);
            if (!playerEntity.isShiftKeyDown() && blockEntity.inventory.canAddItem(itemInHand)) {
                ItemStack copy = itemInHand.copy();
                ItemStack anotherCopy = copy.copy();
                anotherCopy.setCount(1);
                copy.shrink(1);
                playerEntity.setItemInHand(hand, copy);
                blockEntity.inventory.addItem(anotherCopy);
                blockEntity.setChanged();
                return ActionResultType.SUCCESS;
            } else if (itemInHand.isEmpty() && playerEntity.isShiftKeyDown()) {
                List<ItemStack> itemStacks = blockEntity.inventory.removeAllItems();
                for (ItemStack itemStack : itemStacks) {
                    ItemHandlerHelper.giveItemToPlayer(playerEntity, itemStack);
                }
                blockEntity.setChanged();
                return ActionResultType.SUCCESS;
            } else {
                TileMagicModelAssemblyTable tileEntity = (TileMagicModelAssemblyTable) world.getBlockEntity(pos);
                NetworkHooks.openGui((ServerPlayerEntity) playerEntity, tileEntity, (PacketBuffer packerBuffer) -> {
                    packerBuffer.writeBlockPos(tileEntity.getBlockPos());
                });
                return ActionResultType.SUCCESS;
            }
        }
        return super.use(blockState, world, pos, playerEntity, hand, blockRayTraceResult);
    }

    @Override
    public float getShadeBrightness(BlockState p_220080_1_, IBlockReader p_220080_2_, BlockPos p_220080_3_) {
        return 1;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileMagicModelAssemblyTable();
    }
    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return ASSEMBLY_TABLE_SHAPE;
    }

    @Override
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        return ASSEMBLY_TABLE_SHAPE;
    }
}
