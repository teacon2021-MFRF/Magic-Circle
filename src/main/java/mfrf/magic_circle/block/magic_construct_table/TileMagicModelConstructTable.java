package mfrf.magic_circle.block.magic_construct_table;

import mfrf.magic_circle.block.NamedContainerTileBase;
import mfrf.magic_circle.registry_lists.TileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nullable;

public class TileMagicModelConstructTable extends NamedContainerTileBase {
    public Inventory inventory = new Inventory(1) {
        @Override
        public boolean canPlaceItem(int p_94041_1_, ItemStack p_94041_2_) {
            return super.canPlaceItem(p_94041_1_, p_94041_2_);
        }

        @Override
        public void setChanged() {
            markDirty();
            super.setChanged();
        }
    };

    public TileMagicModelConstructTable() {
        super(TileEntities.MAGIC_MODEL_CONSTRUCT_TABLE.get());
    }

    @Override
    public CompoundNBT save(CompoundNBT p_189515_1_) {
        CompoundNBT save = super.save(p_189515_1_);
        save.put("inventory", inventory.createTag());
        return save;
    }

    @Override
    public void load(BlockState p_230337_1_, CompoundNBT p_230337_2_) {
        super.load(p_230337_1_, p_230337_2_);
        inventory.fromTag((ListNBT) p_230337_2_.get("inventory"));
    }

    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent("magic_model_construct_table");
    }

    @Nullable
    @Override
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
        return null;
    }
}
