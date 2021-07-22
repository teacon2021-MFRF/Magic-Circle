package mfrf.magic_circle.item;

import mfrf.magic_circle.interfaces.IMagicalItem;
import mfrf.magic_circle.magicutil.Invoker;
import mfrf.magic_circle.magicutil.MagicStream;
import mfrf.magic_circle.magicutil.Receiver;
import mfrf.magic_circle.magicutil.datastructure.MagicNodePropertyMatrix8By8;
import mfrf.magic_circle.registry_lists.Capabilities;
import mfrf.magic_circle.util.MagicalItemSimpleImplement;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;
import java.util.HashMap;

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
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand p_77659_3_) {
        ItemStack heldItem = player.getItemInHand(p_77659_3_);
        if (!world.isClientSide()) {
            heldItem.getCapability(Capabilities.MAGICAL_ITEM).ifPresent(iMagicalItem -> {
                iMagicalItem.executeMagic(true, world, player.getUUID(), new MagicStream.MagicStreamInfo(null,
                        new MagicStream.DataContain(
                                MagicNodePropertyMatrix8By8.IDENTITY.copy(),
                                Receiver.ReceiverType.BLOCK,
                                Invoker.InvokerType.ENTITY,
                                new Vector3f(player.getLookAngle()),
                                getPlayerPOVHitResult(world, player, RayTraceContext.FluidMode.ANY).getBlockPos(),
                                new Receiver.WeatherType(world.rainLevel, world.thunderLevel),
                                Receiver.RangeType.CIRCLE,
                                world,
                                player.blockPosition().above(),
                                heldItem,
                                player.getUUID(),
                                player.getUUID(),
                                null,
                                new HashMap<>(),
                                0
                        )
                ));
            });
        }
        return new ActionResult<ItemStack>(ActionResultType.SUCCESS, heldItem);
    }

    @Override
    public void inventoryTick(ItemStack p_77663_1_, World p_77663_2_, Entity p_77663_3_, int p_77663_4_, boolean p_77663_5_) {
        super.inventoryTick(p_77663_1_, p_77663_2_, p_77663_3_, p_77663_4_, p_77663_5_);
        p_77663_1_.getCapability(Capabilities.MAGICAL_ITEM).ifPresent(IMagicalItem::onTick);
    }
}
