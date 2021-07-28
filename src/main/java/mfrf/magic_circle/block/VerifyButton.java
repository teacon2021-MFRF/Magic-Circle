package mfrf.magic_circle.block;

import mfrf.magic_circle.item.resources.ItemCompletedTestPaper;
import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class VerifyButton extends AbstractButtonBlock {
    public VerifyButton(Properties p_i48436_2_) {
        super(true, p_i48436_2_);
    }

    @Override
    protected SoundEvent getSound(boolean p_196369_1_) {
        return p_196369_1_ ? SoundEvents.STONE_BUTTON_CLICK_ON : SoundEvents.STONE_BUTTON_CLICK_OFF;
    }

    @Override
    public ActionResultType use(BlockState p_225533_1_, World world, BlockPos p_225533_3_, PlayerEntity playerEntity, Hand inHand, BlockRayTraceResult p_225533_6_) {
        if (playerEntity.getItemInHand(inHand).getItem() instanceof ItemCompletedTestPaper) {
            playerEntity.getItemInHand(inHand).shrink(1);
            return super.use(p_225533_1_, world, p_225533_3_, playerEntity, inHand, p_225533_6_);
        }

        return ActionResultType.SUCCESS;
    }
}
