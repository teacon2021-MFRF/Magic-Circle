package mfrf.magic_circle.gui.research_table;

import mfrf.magic_circle.block.research_table.TileResearchTable;
import mfrf.magic_circle.gui.ContainerBase;
import mfrf.magic_circle.knowledges.PlayerKnowledges;
import mfrf.magic_circle.registry_lists.GuiContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ResearchTableContainer extends ContainerBase {

    private final TileResearchTable research_table;
    private final PlayerKnowledges knowledge;

    public ResearchTableContainer(int id, PlayerInventory playerInventory, BlockPos pos, World world, PlayerKnowledges knowledge) {
        super(GuiContainers.RESEARCH_TABLE_CONTAINER.get(), id);
        this.knowledge = knowledge;
        this.research_table = ((TileResearchTable) world.getBlockEntity(pos));

        addSlot(new Slot(research_table.inventory, TileResearchTable.Slot.PEN_AND_INK.ordinal(), 43, 42) {
            @Override
            public boolean mayPlace(ItemStack p_75214_1_) {
                return research_table.inventory.canPlaceItem(TileResearchTable.Slot.PEN_AND_INK.ordinal(), p_75214_1_);
            }
        });
        addSlot(new Slot(research_table.inventory, TileResearchTable.Slot.TEST_PAPER.ordinal(), 43, 76) {
            @Override
            public boolean mayPlace(ItemStack p_75214_1_) {
                return research_table.inventory.canPlaceItem(TileResearchTable.Slot.TEST_PAPER.ordinal(), p_75214_1_);
            }
        });
        addSlot(new Slot(research_table.inventory, TileResearchTable.Slot.ITEM_TO_ANALYSIS.ordinal(), 43, 110) {
            @Override
            public boolean mayPlace(ItemStack p_75214_1_) {
                return research_table.inventory.canPlaceItem(TileResearchTable.Slot.ITEM_TO_ANALYSIS.ordinal(), p_75214_1_);
            }
        });
        layoutInventory160x75(22, 154, playerInventory);
    }


    @Override
    public boolean stillValid(PlayerEntity p_75145_1_) {
        return true;
    }

    public TileEntity getResearch_table() {
        return research_table;
    }

    public PlayerKnowledges getKnowledge() {
        return knowledge;
    }

}
