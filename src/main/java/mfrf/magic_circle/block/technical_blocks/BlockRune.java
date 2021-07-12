package mfrf.magic_circle.block.technical_blocks;

import mfrf.magic_circle.block.BlockBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;

public class BlockRune extends BlockBase {
    public static IntegerProperty STATE = IntegerProperty.create("state", 1, 32);

    public BlockRune(Properties p_i48440_1_) {
        super(p_i48440_1_);
        this.registerDefaultState(this.defaultBlockState().getBlockState().setValue(STATE, 1));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(STATE);
        super.createBlockStateDefinition(p_206840_1_);
    }
}
