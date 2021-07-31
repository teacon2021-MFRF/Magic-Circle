package mfrf.magic_circle.block;

import mfrf.magic_circle.item.resources.ItemTestPaper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.Random;

public class TestSender extends BlockBase {

    public TestSender(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }

    @Override
    public ActionResultType use(BlockState blockState, World world, BlockPos pos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        ItemHandlerHelper.giveItemToPlayer(playerEntity, ItemTestPaper.randomTestPaper(world));
        return ActionResultType.SUCCESS;
    }

}
