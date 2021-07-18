package mfrf.magic_circle.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class BlockBase extends Block {
    public BlockBase(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }

    @Override
    public ActionResultType use(BlockState blockState, World world, BlockPos pos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        return super.use(blockState, world, pos, playerEntity, hand, blockRayTraceResult);
    }

    protected void openGUIAndContainer(World world, BlockPos blockPos, PlayerEntity serverPlayerEntity, Hand hand, @Nullable Consumer<PacketBuffer> addition) {
            NamedContainerTileBase tileEntity = (NamedContainerTileBase) world.getBlockEntity(blockPos);
            NetworkHooks.openGui(((ServerPlayerEntity) serverPlayerEntity), tileEntity, (PacketBuffer packerBuffer) -> {
                packerBuffer.writeBlockPos(tileEntity.getBlockPos());
                if (addition != null) {
                    addition.accept(packerBuffer);
                }
            });
    }
}
