package mfrf.magic_circle.block.research_table;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.items.ItemStackHandler;

public class TileResearchTable extends TileEntity {
    private ItemStackHandler itemStackHandler = new ItemStackHandler(4) {

    };

    public TileResearchTable(TileEntityType<?> p_i48289_1_) {
        super(p_i48289_1_);
    }

    public enum Slot {
        TEST_PAPER, ITEM_TO_ANALYSIS, PEN_AND_INK,
        ;
    }
}
