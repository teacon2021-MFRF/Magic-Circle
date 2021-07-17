package mfrf.magic_circle.gui.engraver_table;

import mfrf.magic_circle.gui.ContainerBase;
import mfrf.magic_circle.registry_lists.GuiContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class EngraverTableContainer extends ContainerBase {
    public EngraverTableContainer(int id, PlayerInventory playerInventory, BlockPos pos, World world) {
        super(GuiContainers.ENGRAVER_TABLE_CONTAINER.get(),id);
    }

    @Override
    public boolean stillValid(PlayerEntity p_75145_1_) {
        return true;
    }
}
