package mfrf.magic_circle.block;

import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public abstract class NamedContainerTileBase extends TileEntity implements INamedContainerProvider {
    public NamedContainerTileBase(TileEntityType<?> p_i48289_1_) {
        super(p_i48289_1_);
    }
}
