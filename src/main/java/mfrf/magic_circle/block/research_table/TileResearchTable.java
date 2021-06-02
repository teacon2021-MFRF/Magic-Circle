package mfrf.magic_circle.block.research_table;

import mfrf.magic_circle.item.resources.ItemTestPaper;
import mfrf.magic_circle.item.tool.ItemPenAndInk;
import mfrf.magic_circle.registry_lists.TileEntities;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public class TileResearchTable extends TileEntity {
    private ItemStackHandler itemStackHandler = new ItemStackHandler(3) {
        @NotNull
        @Override
        public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            Slot value = Slot.values()[slot];
            switch (value) {
                case TEST_PAPER -> {
                    if (stack.getItem() instanceof ItemTestPaper) {
                        if (getStackInSlot(slot).isEmpty()) {
                            return super.insertItem(slot, stack, simulate);
                        }
                    }
                }
                case PEN_AND_INK -> {
                    if (stack.getItem() instanceof ItemPenAndInk) {
                        if (getStackInSlot(slot).isEmpty()) {
                            return super.insertItem(slot, stack, simulate);
                        }
                    }
                }
                default -> {
                    return super.insertItem(slot, stack, simulate);
                }
            }
            return stack;
        }
    };

    public TileResearchTable() {
        super(TileEntities.RESEARCH_TABLE.get());
    }

    public enum Slot {
        TEST_PAPER, ITEM_TO_ANALYSIS, PEN_AND_INK,
        ;
    }
}
