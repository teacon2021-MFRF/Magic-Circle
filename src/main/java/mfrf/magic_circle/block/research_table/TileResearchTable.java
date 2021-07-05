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
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;

public class TileResearchTable extends NamedContainerTileBase {

    private Inventory inventory = new Inventory(3) {
        @Override
        public boolean canPlaceItem(int slot, ItemStack stack) {

            Slot value = Slot.values()[slot];
            switch (value) {
                case TEST_PAPER: {
                    if (stack.getItem() instanceof ItemTestPaper) {
                        if (getItem(slot).isEmpty()) {
                            return super.canPlaceItem(slot, stack);
                        }
                    }
                    return false;
                }
                case PEN_AND_INK: {
                    if (stack.getItem() instanceof ItemPenAndInk) {
                        if (getItem(slot).isEmpty()) {
                            return super.canPlaceItem(slot, stack);
                        }
                    }
                    return false;
                }
                default: {
                    return super.canPlaceItem(slot, stack);
                }
            }

        }

        @Override
        public void setChanged() {
            markDirty();
            super.setChanged();
        }
    };

    public TileResearchTable() {
        super(TileEntities.RESEARCH_TABLE.get());
    }

    @Override
    public CompoundNBT save(CompoundNBT p_189515_1_) {
        CompoundNBT save = super.save(p_189515_1_);
        save.put("inventory", getInventory().createTag());
        return save;
    }

    @Override
    public void load(BlockState p_230337_1_, CompoundNBT p_230337_2_) {
        super.load(p_230337_1_, p_230337_2_);
        INBT inventory = p_230337_2_.get("inventory");
        if (inventory != null) {
            this.getInventory().fromTag((ListNBT) inventory);
        }
    }

    @Override

    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("gui." + MagicCircle.MOD_ID + ".research_table");
    }

    @Nullable
    @Override
    public Container createMenu(int syncID, PlayerInventory inventory, PlayerEntity playerEntity) {
        return new ResearchTableContainer(syncID, inventory, this.worldPosition, this.level, PlayerKnowledge.getOrCreate(this.level).getOrCreate(playerEntity.getUUID()));
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
        markDirty();
    }

    public enum Slot {
        TEST_PAPER, ITEM_TO_ANALYSIS, PEN_AND_INK,
        ;
    }
}
