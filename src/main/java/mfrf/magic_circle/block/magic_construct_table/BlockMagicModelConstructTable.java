package mfrf.magic_circle.block.magic_construct_table;

import mfrf.magic_circle.block.BlockBase;
import net.minecraft.block.BlockState;

public class BlockMagicModelConstructTable extends BlockBase {
    public BlockMagicModelConstructTable(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

}
