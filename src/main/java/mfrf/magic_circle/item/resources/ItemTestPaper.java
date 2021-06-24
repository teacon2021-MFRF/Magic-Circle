package mfrf.magic_circle.item.resources;

import mfrf.magic_circle.item.ItemBase;
import mfrf.magic_circle.json_recipe_configs.JsonConfigGemEffect;
import mfrf.magic_circle.json_recipe_configs.JsonConfigItemResearch;
import mfrf.magic_circle.json_recipe_configs.research_test.ResearchTestBase;
import mfrf.magic_circle.registry_lists.Items;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ItemTestPaper extends ItemBase {

    public ItemTestPaper(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    @Override
    public ActionResult<ItemStack> use(World p_77659_1_, PlayerEntity p_77659_2_, Hand p_77659_3_) {
        return super.use(p_77659_1_, p_77659_2_, p_77659_3_);
        //todo ui
    }

    @Nullable
    public static ResearchTestBase.Serializer.DataContainer getTest(ItemStack stack) {
        if (stack != null) {
            CompoundNBT tag = stack.getTag();
            if (tag.contains("research_contain")) {
                return ResearchTestBase.Serializer.fromNBT(tag.getCompound("research_contain"));
            }
        }
        return null;
    }

    public static ItemStack createTestPaper(ResearchTestBase researchTestBase) {
        ItemStack itemStack = Items.TEST_PAPER.get().getDefaultInstance();
        itemStack.addTagElement("research_contain", ((ResearchTestBase.Serializer) researchTestBase.getSerializer()).toNBT(researchTestBase));
        return itemStack;
    }
}
