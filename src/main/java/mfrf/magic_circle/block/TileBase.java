package mfrf.magic_circle.block;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public abstract class TileBase extends TileEntity {
    public TileBase(TileEntityType<?> p_i48289_1_) {
        super(p_i48289_1_);
    }

    protected void markDirty(){
        setChanged();
    }
}
