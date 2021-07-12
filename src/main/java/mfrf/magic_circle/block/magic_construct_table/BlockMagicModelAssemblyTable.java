package mfrf.magic_circle.block.magic_construct_table;

import mfrf.magic_circle.block.BlockBase;
import mfrf.magic_circle.block.research_table.TileResearchTable;
import mfrf.magic_circle.world_saved_data.PlayerKnowledge;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class BlockMagicModelAssemblyTable extends BlockBase {
    public BlockMagicModelAssemblyTable(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public ActionResultType use(BlockState blockState, World world, BlockPos pos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        if (!world.isClientSide() && hand == Hand.MAIN_HAND) {
//            openGUIAndContainer(world, pos, playerEntity, hand, buffer -> {
//                buffer.writeNbt(PlayerKnowledge.getOrCreate(world).getOrCreate(playerEntity.getUUID()).serializeNBT());
//            });
            TileMagicModelAssemblyTable tileEntity = (TileMagicModelAssemblyTable) world.getBlockEntity(pos);
            NetworkHooks.openGui((ServerPlayerEntity) playerEntity, tileEntity, (PacketBuffer packerBuffer) -> {
                packerBuffer.writeBlockPos(tileEntity.getBlockPos());
            });
        }
        return super.use(blockState, world, pos, playerEntity, hand, blockRayTraceResult);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileMagicModelAssemblyTable();
    }
}
