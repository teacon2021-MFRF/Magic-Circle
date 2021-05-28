package mfrf.magic_circle.item;

import javax.annotation.Nullable;

import mfrf.magic_circle.registry_lists.Capabilities;
import mfrf.magic_circle.util.MagicalItemSimpleImplement;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class ItemStaff extends ItemBase {

    private final MagicalItemSimpleImplement implement;

    public ItemStaff(Properties p_i48487_1_, MagicalItemSimpleImplement template) {
        super(p_i48487_1_);
        this.implement = template;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return implement.copy(stack, nbt);
    }

    @Override
    public ActionResult<ItemStack> use(World p_77659_1_, PlayerEntity p_77659_2_, Hand p_77659_3_) {
        ItemStack heldItem = p_77659_2_.getItemInHand(p_77659_3_);
        if (!p_77659_1_.isClientSide()) {
            heldItem.getCapability(Capabilities.MAGICAL_ITEM).ifPresent(iMagicalItem -> {
                p_77659_2_.sendMessage(new StringTextComponent(iMagicalItem.toString()), p_77659_2_.getUUID());
            });
        }
        return new ActionResult<ItemStack>(ActionResultType.SUCCESS, heldItem);
    }
}
