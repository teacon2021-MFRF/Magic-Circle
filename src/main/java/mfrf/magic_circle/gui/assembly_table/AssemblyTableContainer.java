package mfrf.magic_circle.gui.assembly_table;

import mfrf.magic_circle.gui.ContainerBase;
import mfrf.magic_circle.registry_lists.GuiContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class AssemblyTableContainer extends ContainerBase {
    public AssemblyTableContainer(int id, PlayerInventory playerInventory, BlockPos pos, World world) {
        super(GuiContainers.ASSEMBLY_TABLE_CONTAINER.get(), id);
        //todo finishit
    }

    @Override
    public boolean stillValid(PlayerEntity p_75145_1_) {
        return true;
    }
}
