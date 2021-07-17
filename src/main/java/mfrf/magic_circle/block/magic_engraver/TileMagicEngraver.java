package mfrf.magic_circle.block.magic_engraver;

import mfrf.magic_circle.block.TileBase;
import mfrf.magic_circle.registry_lists.Capabilities;
import mfrf.magic_circle.registry_lists.TileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class TileMagicEngraver extends TileBase {
    private static HashMap<Vector3i, BlockState> structureMap = new HashMap<>();

    static {
//        structureMap.put(new BlockPos(0,-1,0),)
    }

    public Inventory inventory = new Inventory(1) {
        @Override
        public boolean canPlaceItem(int p_94041_1_, ItemStack p_94041_2_) {
            return super.canPlaceItem(p_94041_1_, p_94041_2_) && p_94041_2_.getCapability(Capabilities.MAGICAL_ITEM).isPresent();
        }

        @Override
        public void setChanged() {
            markDirty();
            super.setChanged();
        }
    };

    public TileMagicEngraver() {
        super(TileEntities.MAGIC_ENGRAVER_TABLE.get());
    }

    public boolean structureComplete() {
        World level = getLevel();
        BlockPos pos = getBlockPos();
        for (Map.Entry<Vector3i, BlockState> blockPosBlockStateEntry : structureMap.entrySet()) {
            if (level.getBlockState(pos.offset(blockPosBlockStateEntry.getKey())) != blockPosBlockStateEntry.getValue()) {
                return false;
            }
        }
        return true;
    }


}
