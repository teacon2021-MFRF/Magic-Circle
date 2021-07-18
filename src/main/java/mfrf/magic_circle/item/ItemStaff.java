package mfrf.magic_circle.item;

import mfrf.magic_circle.interfaces.IMagicalItem;
import mfrf.magic_circle.registry_lists.Capabilities;
import mfrf.magic_circle.util.MagicalItemSimpleImplement;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
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
        return implement.copy(stack, nbt);
    }

    @Override
    public ActionResult<ItemStack> use(World p_77659_1_, PlayerEntity p_77659_2_, Hand p_77659_3_) {
        ItemStack heldItem = p_77659_2_.getItemInHand(p_77659_3_);
        if (!p_77659_1_.isClientSide()) {
            if(p_77659_2_.isShiftKeyDown()){
                heldItem.getCapability(Capabilities.MAGICAL_ITEM).ifPresent(iMagicalItem -> {
                    iMagicalItem.setMana((int) p_77659_1_.getDayTime());
                });
            }else {
            heldItem.getCapability(Capabilities.MAGICAL_ITEM).ifPresent(iMagicalItem -> {
                p_77659_2_.sendMessage(new StringTextComponent(iMagicalItem.getMana()+""), p_77659_2_.getUUID());
            });
            }
        }
        return new ActionResult<ItemStack>(ActionResultType.SUCCESS, heldItem);
    }

    @Override
    public void inventoryTick(ItemStack p_77663_1_, World p_77663_2_, Entity p_77663_3_, int p_77663_4_, boolean p_77663_5_) {
        super.inventoryTick(p_77663_1_, p_77663_2_, p_77663_3_, p_77663_4_, p_77663_5_);
        p_77663_1_.getCapability(Capabilities.MAGICAL_ITEM).ifPresent(IMagicalItem::onTick);
    }
}
