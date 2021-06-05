//package mfrf.magic_circle.block.research_table;
//
//import mfrf.magic_circle.MagicCircle;
//import mfrf.magic_circle.item.resources.ItemTestPaper;
//import mfrf.magic_circle.item.tool.ItemPenAndInk;
//import mfrf.magic_circle.registry_lists.TileEntities;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.entity.player.PlayerInventory;
//import net.minecraft.inventory.container.Container;
//import net.minecraft.inventory.container.INamedContainerProvider;
//import net.minecraft.item.ItemStack;
//import net.minecraft.tileentity.TileEntity;
//import net.minecraft.tileentity.TileEntityType;
//import net.minecraft.util.text.ITextComponent;
//import net.minecraft.util.text.TranslationTextComponent;
//import net.minecraftforge.items.ItemStackHandler;
//import org.jetbrains.annotations.NotNull;
//
//import javax.annotation.Nullable;
//
//public class TileResearchTable extends TileEntity implements INamedContainerProvider {
//    private ItemStackHandler itemStackHandler = new ItemStackHandler(3) {
//        @NotNull
//        @Override
//        public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
//            Slot value = Slot.values()[slot];
//            switch (value) {
//                case TEST_PAPER -> {
//                    if (stack.getItem() instanceof ItemTestPaper) {
//                        if (getStackInSlot(slot).isEmpty()) {
//                            return super.insertItem(slot, stack, simulate);
//                        }
//                    }
//                }
//                case PEN_AND_INK -> {
//                    if (stack.getItem() instanceof ItemPenAndInk) {
//                        if (getStackInSlot(slot).isEmpty()) {
//                            return super.insertItem(slot, stack, simulate);
//                        }
//                    }
//                }
//                default -> {
//                    return super.insertItem(slot, stack, simulate);
//                }
//            }
//            return stack;
//        }
//    };
//
//    public TileResearchTable() {
//        super(TileEntities.RESEARCH_TABLE.get());
//    }
//
//    @Override
//    public ITextComponent getDisplayName() {
//        return new TranslationTextComponent("gui." + MagicCircle.MOD_ID + ".research_table");
//    }
//
//    @Nullable
//    @Override
//    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
//
//    }
//
//    public enum Slot {
//        TEST_PAPER, ITEM_TO_ANALYSIS, PEN_AND_INK,
//        ;
//    }
//}
