package mfrf.magic_circle.gui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public abstract class ContainerBase extends Container {
    protected ContainerBase(@Nullable ContainerType<?> p_i50105_1_, int p_i50105_2_) {
        super(p_i50105_1_, p_i50105_2_);
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity p_82846_1_, int p_82846_2_) {
        return ItemStack.EMPTY;
    }

    protected void layoutInventory160x75(int x, int y, IInventory inventory) {
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(inventory, i, x + i * 18, y + 59));
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(inventory, 9 + i * 9 + j, x + j * 18, y + i * 18));
            }
        }
    }

    protected void putInventories(int x, int y, int xOffset, int yOffset, int rowCount, int colCount, int indexBegin, IInventory inventory) {
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                this.addSlot(new Slot(
                        inventory, indexBegin++, x + j * xOffset, y + i * yOffset
                ));
            }
        }
    }

    protected static int getOriginX() {
        return 87;
    }

    protected static int getOriginY() {
        return 82;
    }

}
