package mfrf.magic_circle.gui;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;

import javax.annotation.Nullable;

public abstract class ContainerBase extends Container {
    protected ContainerBase(@Nullable ContainerType<?> p_i50105_1_, int p_i50105_2_) {
        super(p_i50105_1_, p_i50105_2_);
    }


    protected void layoutInventory160x75(int x, int y, IInventory inventory) {
        int actualX = x + getOriginX();
        int actualY = y + getOriginY();
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(inventory, i, actualX + i * 18, actualY + 59));
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(inventory, 9 + i * 9 + j, actualX + j * 18, actualY + i * 18));
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
