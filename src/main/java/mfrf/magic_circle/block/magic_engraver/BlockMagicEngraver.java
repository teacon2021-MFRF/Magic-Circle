package mfrf.magic_circle.block.magic_engraver;

import mfrf.magic_circle.block.BlockBase;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class BlockMagicEngraver extends BlockBase {
    public BlockMagicEngraver(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }

    @Override
    public float getShadeBrightness(BlockState p_220080_1_, IBlockReader p_220080_2_, BlockPos p_220080_3_) {
        return 0;
    }
}
