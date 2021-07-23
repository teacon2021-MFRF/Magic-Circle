package mfrf.magic_circle.item;

import mfrf.magic_circle.Config;
import mfrf.magic_circle.item.ItemStaff;
import mfrf.magic_circle.magicutil.BaguaPrefer;
import mfrf.magic_circle.magicutil.Invoker;
import mfrf.magic_circle.magicutil.MagicStream;
import mfrf.magic_circle.magicutil.Receiver;
import mfrf.magic_circle.magicutil.datastructure.MagicNodePropertyMatrix8By8;
import mfrf.magic_circle.registry_lists.Capabilities;
import mfrf.magic_circle.registry_lists.Items;
import mfrf.magic_circle.util.MagicalItemContainer;
import mfrf.magic_circle.util.MagicalItemSimpleImplement;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Random;

public class RevalStaff extends ItemStaff {
    public static ItemStack stack = new ItemStack(Items.MAGIC_CRYSTAL.get());

    static {
        Random RANDOM = new Random();
        CompoundNBT magic_circle = stack.getOrCreateTagElement("magic_circle");
        int size = 2560;
        int purity = 4;
        int v = size / purity;
        magic_circle.putInt("size", 2560);
        magic_circle.putDouble("purity", 4);
        magic_circle.put("bagua_prefer", new BaguaPrefer(RANDOM.nextInt(v), RANDOM.nextInt(v), RANDOM.nextInt(v), RANDOM.nextInt(v), RANDOM.nextInt(v), RANDOM.nextInt(v), RANDOM.nextInt(v), RANDOM.nextInt(v)).serializeNBT());

        stack.getCapability(Capabilities.MAGICAL_ITEM).ifPresent(
                iMagicalItem -> {
                    iMagicalItem.setManaCapacity(size * Config.SIZE_MANA_CAPACITY_SCALE.get());
                    iMagicalItem.setMana(RANDOM.nextInt((int) iMagicalItem.getManaCapacity()));
                    iMagicalItem.setManaRecovery((float) (purity * Config.PURITY_MANA_RECOVERY_SCALE.get()));
                    iMagicalItem.setScaleCapacityIfPrimed(Config.SIZE_MANA_CAPACITY_PRIMED_SCALE.get());
                    iMagicalItem.setScaleRecoverIfPrimed(Config.PURITY_MANA_RECOVERY_PRIMED_SCALE.get());
                    iMagicalItem.setMaxMagicCapacity(Math.round((float) purity));
                    iMagicalItem.addMagic("fireball");
                    iMagicalItem.setSelectedMagic("fireball");

                    iMagicalItem.setEffectContainer(new MagicalItemContainer((new MagicalItemContainer.Slot[]{})).serializeNBT());
                }
        );
    }

    public RevalStaff(Properties p_i48487_1_) {
        super(p_i48487_1_, new MagicalItemSimpleImplement(new MagicalItemContainer(new MagicalItemContainer.Slot[]{}), Integer.MAX_VALUE, 1, Integer.MAX_VALUE, Integer.MAX_VALUE, 1, 1, ItemStack.EMPTY));
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand p_77659_3_) {
//        return super.use(world, player, p_77659_3_);

        ItemStack heldItem = player.getItemInHand(p_77659_3_);
        if (!world.isClientSide()) {
            MagicStream.MagicStreamInfo magicStreamInfo = new MagicStream.MagicStreamInfo(null,
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
                    ));

            magicStreamInfo.data.eigenMatrix.set(MagicNodePropertyMatrix8By8.INDEX.STRENGTH, 2000);
            magicStreamInfo.data.eigenMatrix.set(MagicNodePropertyMatrix8By8.INDEX.DURATION, 200);
            magicStreamInfo.data.eigenMatrix.set(MagicNodePropertyMatrix8By8.INDEX.EFFICIENT,20);
//            magicStreamInfo.data.eigenMatrix.set(MagicNodePropertyMatrix8By8.INDEX.STRENGTH, 1);
//            magicStreamInfo.data.eigenMatrix.set(MagicNodePropertyMatrix8By8.INDEX.DURATION, 1);
//            magicStreamInfo.data.eigenMatrix.set(MagicNodePropertyMatrix8By8.INDEX.EFFICIENT,1);

            stack.getCapability(Capabilities.MAGICAL_ITEM).ifPresent(iMagicalItem -> {
                iMagicalItem.executeMagic(false, world, player.getUUID(), magicStreamInfo);
            });
        }
        return new ActionResult<ItemStack>(ActionResultType.SUCCESS, heldItem);
    }
}
