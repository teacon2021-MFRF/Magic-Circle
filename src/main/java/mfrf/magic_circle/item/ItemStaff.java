package mfrf.magic_circle.item;

import mfrf.magic_circle.registry_lists.Capabilities;
import mfrf.magic_circle.util.MagicalItemSimpleImplement;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;

public class ItemStaff extends ItemBase {

    private final MagicalItemSimpleImplement implement;

    public ItemStaff(Properties p_i48487_1_, MagicalItemSimpleImplement template) {
        super(p_i48487_1_);
        this.implement = template;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return implement.copy(stack);
//        return super.initCapabilities(stack, nbt);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World p_77659_1_, PlayerEntity p_77659_2_, Hand p_77659_3_) {
        ItemStack heldItem = p_77659_2_.getHeldItem(p_77659_3_);
        if (!p_77659_1_.isRemote()) {
            heldItem.getCapability(Capabilities.MAGICAL_ITEM).ifPresent(iMagicalItem -> {
                p_77659_2_.sendMessage(new StringTextComponent(iMagicalItem.toString()), p_77659_2_.getUniqueID());
            });
        }
        return new ActionResult<ItemStack>(ActionResultType.SUCCESS, heldItem);
    }
}
