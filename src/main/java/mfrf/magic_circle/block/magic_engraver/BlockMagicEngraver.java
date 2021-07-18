package mfrf.magic_circle.block.magic_engraver;

import mfrf.magic_circle.block.BlockBase;
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
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BlockMagicEngraver extends BlockBase {
    public BlockMagicEngraver(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }

    @Override
    public float getShadeBrightness(BlockState p_220080_1_, IBlockReader p_220080_2_, BlockPos p_220080_3_) {
        return 1;
    }

    @Override
    public ActionResultType use(BlockState blockState, World world, BlockPos pos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        if (!world.isClientSide() && hand == Hand.MAIN_HAND) {
            ItemStack itemInHand = playerEntity.getItemInHand(hand);
            TileMagicEngraver tileEntity = (TileMagicEngraver) world.getBlockEntity(pos);
            if (!itemInHand.isEmpty() && tileEntity.inventory.canPlaceItem(0, itemInHand) && !playerEntity.isShiftKeyDown()) {
                ItemStack copy = itemInHand.copy();
                ItemStack anotherCopy = copy.copy();
                anotherCopy.setCount(1);
                copy.shrink(1);
                playerEntity.setItemInHand(hand, copy);
                tileEntity.inventory.addItem(anotherCopy);
                tileEntity.inventory.setChanged();
            } else if (playerEntity.isShiftKeyDown()) {
                List<ItemStack> itemStacks = tileEntity.inventory.removeAllItems();
                for (ItemStack itemStack : itemStacks) {
                    ItemHandlerHelper.giveItemToPlayer(playerEntity, itemStack);
                }
                tileEntity.inventory.setChanged();
            } else {
                NetworkHooks.openGui((ServerPlayerEntity) playerEntity, tileEntity, (PacketBuffer packerBuffer) -> {
                    packerBuffer.writeBlockPos(tileEntity.getBlockPos());
                });
            }
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileMagicEngraver();
    }
}
