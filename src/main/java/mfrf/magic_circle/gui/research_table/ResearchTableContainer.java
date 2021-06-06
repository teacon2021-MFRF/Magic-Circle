package mfrf.magic_circle.gui.research_table;

import mfrf.magic_circle.gui.ContainerBase;
import mfrf.magic_circle.registry_lists.GuiContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ResearchTableContainer extends ContainerBase {

    public ResearchTableContainer(int id, PlayerInventory playerInventory, BlockPos pos, World world) {
        super(GuiContainers.RESEARCH_TABLE_CONTAINER.get(), id);
    }

    @Override
    public boolean stillValid(PlayerEntity p_75145_1_) {
        return true;
    }

}
