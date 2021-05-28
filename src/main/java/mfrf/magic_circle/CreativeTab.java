package mfrf.magic_circle;

import mfrf.magic_circle.registry_lists.Items;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class CreativeTab extends ItemGroup {
    public CreativeTab() {
        super("magic_circle_resources");
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(Items.CREATIVE_STAFF.get());
    }
}
