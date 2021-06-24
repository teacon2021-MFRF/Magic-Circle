package mfrf.magic_circle.block;

import mfrf.magic_circle.item.resources.ItemTestPaper;
import mfrf.magic_circle.json_recipe_configs.research_test.ResearchTestBase;
import mfrf.magic_circle.registry_lists.JsonConfigs;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import java.util.List;

public class TestBlock extends BlockBase {
    public TestBlock(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }

    @Override
    public ActionResultType use(BlockState blockState, World world, BlockPos pos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        if (!world.isClientSide()) {
            ItemStack itemInHand = playerEntity.getItemInHand(hand);
            if (!itemInHand.isEmpty()) {
//                    playerEntity.sendMessage(new StringTextComponent(itemInHand.getTag().toString()), playerEntity.getUUID());

                    if (itemInHand.getItem() instanceof ItemTestPaper) {
                        List<ResearchTestBase> allRecipesFor = world.getRecipeManager().getAllRecipesFor(JsonConfigs.Type.RESEARCH_TEST_JSONCONFIG_TYPE);
                        playerEntity.sendMessage(new StringTextComponent("count:" + allRecipesFor.size()), playerEntity.getUUID());
                        if (!allRecipesFor.isEmpty()) {
                            ItemStack testPaper = ItemTestPaper.createTestPaper(allRecipesFor.get(0));
                            playerEntity.inventory.add(testPaper);
                        }
                    }
            }
        }
        return ActionResultType.SUCCESS;
    }
}
