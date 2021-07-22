package mfrf.magic_circle.gui.engraver_table;

import mfrf.magic_circle.gui.ContainerBase;
import mfrf.magic_circle.knowledges.PlayerKnowledges;
import mfrf.magic_circle.registry_lists.GuiContainers;
import mfrf.magic_circle.util.CachedEveryThingForClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EngraverTableContainer extends ContainerBase {
    public final PlayerEntity playerEntity;

    public EngraverTableContainer(int id, PlayerInventory playerInventory, BlockPos pos, World world) {
        super(GuiContainers.ENGRAVER_TABLE_CONTAINER.get(), id);
        playerEntity = playerInventory.player;
    }

    @Override
    public boolean stillValid(PlayerEntity p_75145_1_) {
        return true;
    }
}
