package mfrf.magic_circle.gui.research_table;

import mfrf.magic_circle.block.research_table.TileResearchTable;
import mfrf.magic_circle.gui.ContainerBase;
import mfrf.magic_circle.item.resources.ItemTestPaper;
import mfrf.magic_circle.json_recipe_configs.JsonConfigItemResearch;
import mfrf.magic_circle.json_recipe_configs.research_test.ResearchTestBase;
import mfrf.magic_circle.knowledges.PlayerKnowledges;
import mfrf.magic_circle.registry_lists.GuiContainers;
import mfrf.magic_circle.registry_lists.JsonConfigs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;

public class ResearchTableContainer extends ContainerBase {

    private final TileResearchTable research_table;
    private final PlayerKnowledges knowledge;
    private final Optional<JsonConfigItemResearch> itemResearchContain;
    private final Optional<ResearchTestBase.Serializer.DataContainer> testPaperResearchContain;

    public ResearchTableContainer(int id, PlayerInventory playerInventory, BlockPos pos, World world, PlayerKnowledges knowledge) {
        super(GuiContainers.RESEARCH_TABLE_CONTAINER.get(), id);

        this.knowledge = knowledge;
        this.research_table = (TileResearchTable) world.getBlockEntity(pos);
        final Inventory inventory = research_table.getInventory();

        this.itemResearchContain = world.getRecipeManager().getAllRecipesFor(JsonConfigs.Type.ITEM_RESEARCH_JSONCONFIG_TYPE).stream().filter(
                jsonConfigItemResearch -> jsonConfigItemResearch.getIngredient().test(inventory.getItem(TileResearchTable.Slot.ITEM_TO_ANALYSIS.ordinal()))
        ).findFirst();
        this.testPaperResearchContain = Optional.ofNullable(ItemTestPaper.getTest(inventory.getItem(TileResearchTable.Slot.TEST_PAPER.ordinal())));

        addSlot(new Slot(inventory, TileResearchTable.Slot.PEN_AND_INK.ordinal(), 43, 42) {
            @Override
            public boolean mayPlace(ItemStack p_75214_1_) {
                return inventory.canPlaceItem(TileResearchTable.Slot.PEN_AND_INK.ordinal(), p_75214_1_);
            }
        });
        addSlot(new Slot(inventory, TileResearchTable.Slot.TEST_PAPER.ordinal(), 43, 76) {
            @Override
            public boolean mayPlace(ItemStack p_75214_1_) {
                return inventory.canPlaceItem(TileResearchTable.Slot.TEST_PAPER.ordinal(), p_75214_1_);
            }
        });
        addSlot(new Slot(inventory, TileResearchTable.Slot.ITEM_TO_ANALYSIS.ordinal(), 43, 110) {
            @Override
            public boolean mayPlace(ItemStack p_75214_1_) {
                return inventory.canPlaceItem(TileResearchTable.Slot.ITEM_TO_ANALYSIS.ordinal(), p_75214_1_);
            }
        });

        layoutInventory160x75(22, 154, playerInventory);
    }


    @Override
    public boolean stillValid(PlayerEntity p_75145_1_) {
        return true;
    }

    public Optional<JsonConfigItemResearch> getItemResearchContain() {
        return itemResearchContain;
    }

    public Optional<ResearchTestBase.Serializer.DataContainer> getTestPaperResearchContain() {
        return testPaperResearchContain;
    }

    public TileEntity getResearch_table() {
        return research_table;
    }

    public PlayerKnowledges getKnowledge() {
        return knowledge;
    }

}
