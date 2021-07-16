package mfrf.magic_circle.block.magic_assemby_table;

import mfrf.magic_circle.block.NamedContainerTileBase;
import mfrf.magic_circle.gui.assembly_table.AssemblyTableContainer;
import mfrf.magic_circle.registry_lists.Capabilities;
import mfrf.magic_circle.registry_lists.TileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nullable;

public class TileMagicModelAssemblyTable extends NamedContainerTileBase {
    public Inventory inventory = new Inventory(1) {
        @Override
        public boolean canPlaceItem(int p_94041_1_, ItemStack itemStack) {
            return itemStack.getCapability(Capabilities.MAGICAL_ITEM).isPresent();
        }

        @Override
        public void setChanged() {
            markDirty();
            super.setChanged();
        }
    };

    public TileMagicModelAssemblyTable() {
        super(TileEntities.MAGIC_MODEL_ASSEMBLY_TABLE.get());
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
        if (p_230337_2_.contains("inventory")) {
            inventory.fromTag((ListNBT) p_230337_2_.get("inventory"));
        }
    }

    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent("magic_model_assembly_table");
    }

    @Nullable
    @Override
    public Container createMenu(int syncID, PlayerInventory inventory, PlayerEntity playerEntity) {
        return new AssemblyTableContainer(syncID, inventory, worldPosition, level);
    }
}
