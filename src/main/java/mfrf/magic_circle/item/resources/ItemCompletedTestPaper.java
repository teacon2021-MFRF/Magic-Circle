package mfrf.magic_circle.item.resources;

import mfrf.magic_circle.item.ItemBase;
import mfrf.magic_circle.registry_lists.Items;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ItemCompletedTestPaper extends ItemBase {

    public ItemCompletedTestPaper(Item.Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    @Override
    public ActionResult<ItemStack> use(World p_77659_1_, PlayerEntity p_77659_2_, Hand p_77659_3_) {
        return super.use(p_77659_1_, p_77659_2_, p_77659_3_);
        //todo ui
    }

    @Nullable
    public static String getTest(ItemStack stack) {
        if (stack != null && !stack.isEmpty()) {
            CompoundNBT tag = stack.getOrCreateTag();
            if (tag != null && tag.contains("research")) {
                return tag.getString("research");
            }
        }
        return null;
    }

    public static ItemStack createCompletedTestPaper(String research) {
        ItemStack itemStack = Items.TEST_PAPER_100.get().getDefaultInstance();
        CompoundNBT orCreateTag = itemStack.getOrCreateTag();

        orCreateTag.putString("research", research);

        return itemStack;
    }
}
