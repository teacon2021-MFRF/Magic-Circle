package mfrf.magic_circle.item.resources;

import mfrf.magic_circle.item.ItemBase;
import mfrf.magic_circle.json_recipe_configs.research_test.ResearchTestBase;
import mfrf.magic_circle.registry_lists.Items;
import mfrf.magic_circle.registry_lists.JsonConfigs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemTestPaper extends ItemBase {

    public ItemTestPaper(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        if (!world.isClientSide()) {
            ItemStack itemInHand = playerEntity.getItemInHand(hand);
            if (getTest(itemInHand) == null) {
                List<ResearchTestBase> recipes = world.getRecipeManager().getAllRecipesFor(JsonConfigs.Type.RESEARCH_TEST_JSONCONFIG_TYPE);
                if (!recipes.isEmpty()) {
                    ResearchTestBase researchTestBase = recipes.get(random.nextInt(recipes.size()));
                    itemInHand.addTagElement("research_contain", ((ResearchTestBase.Serializer) researchTestBase.getSerializer()).toNBT(researchTestBase));
                    return ActionResult.success(itemInHand);
                }
            }
        }
        return super.use(world, playerEntity, hand);
    }

    @Nullable
    public static ResearchTestBase.Serializer.DataContainer getTest(ItemStack stack) {
        if (stack != null && !stack.isEmpty()) {
            CompoundNBT tag = stack.getTag();
            if (tag != null && tag.contains("research_contain")) {
                return ResearchTestBase.Serializer.fromNBT(tag.getCompound("research_contain"));
            }
        }
        return null;

    }

    public static ItemStack randomTestPaper(World world) {
        ItemStack stack = new ItemStack(Items.TEST_PAPER.get());
        List<ResearchTestBase> recipes = world.getRecipeManager().getAllRecipesFor(JsonConfigs.Type.RESEARCH_TEST_JSONCONFIG_TYPE);
        if (!recipes.isEmpty()) {
            ResearchTestBase researchTestBase = recipes.get(random.nextInt(recipes.size()));
            stack.addTagElement("research_contain", ((ResearchTestBase.Serializer) researchTestBase.getSerializer()).toNBT(researchTestBase));
        }
        return stack;
    }

    public static ItemStack createTestPaper(ResearchTestBase researchTestBase) {
        ItemStack itemStack = Items.TEST_PAPER.get().getDefaultInstance();
        itemStack.addTagElement("research_contain", ((ResearchTestBase.Serializer) researchTestBase.getSerializer()).toNBT(researchTestBase));
        return itemStack;
    }
}
