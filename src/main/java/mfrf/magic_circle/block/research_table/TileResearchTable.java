package mfrf.magic_circle.block.research_table;

import mfrf.magic_circle.MagicCircle;
import mfrf.magic_circle.block.NamedContainerTileBase;
import mfrf.magic_circle.gui.research_table.ResearchTableContainer;
import mfrf.magic_circle.item.resources.ItemTestPaper;
import mfrf.magic_circle.item.tool.ItemPenAndInk;
import mfrf.magic_circle.registry_lists.TileEntities;
import mfrf.magic_circle.world_saved_data.PlayerKnowledge;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class TileResearchTable extends NamedContainerTileBase {

    public Inventory inventory = new Inventory(3) {
        @Override
        public boolean canPlaceItem(int slot, ItemStack stack) {

            Slot value = Slot.values()[slot];
            switch (value) {
                case TEST_PAPER: {
                    if (stack.getItem() instanceof ItemTestPaper) {
                        if (getItem(slot).isEmpty()) {
                            return true;
                        }
                    }
                    return false;
                }
                case PEN_AND_INK: {
                    if (stack.getItem() instanceof ItemPenAndInk) {
                        if (getItem(slot).isEmpty()) {
                            return true;
                        }
                    }
                    return false;
                }
                default: {
                    return super.canPlaceItem(slot, stack);
                }
            }

        }
    };

    public TileResearchTable() {
        super(TileEntities.RESEARCH_TABLE.get());
    }

    @Override

    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("gui." + MagicCircle.MOD_ID + ".research_table");
    }

    @Nullable
    @Override
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
        World world = getLevel();
        return new ResearchTableContainer(p_createMenu_1_, p_createMenu_2_, getBlockPos(), world, PlayerKnowledge.getOrCreate(world).getOrCreate(p_createMenu_3_.getUUID()));
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT updateTag = super.getUpdateTag();
        updateTag.put("inventory",inventory.createTag());
        return updateTag;
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        super.handleUpdateTag(state, tag);
        inventory.fromTag((ListNBT) tag.get("inventory"));
    }

    public enum Slot {
        TEST_PAPER, ITEM_TO_ANALYSIS, PEN_AND_INK,
        ;
    }
}
