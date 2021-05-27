package mfrf.magic_circle.item.armor;

import mfrf.magic_circle.util.EffectiveItemContainer;
import mfrf.magic_circle.util.MagicalItemSimpleImplement;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;

public class MagesBoots extends MagesArmorBase {
    public MagesBoots(Properties p_i48534_3_, EffectiveItemContainer.Slot[] slots) {
        super(EquipmentSlotType.FEET, p_i48534_3_, new MagicalItemSimpleImplement(new EffectiveItemContainer(
                slots
        ), MATERIAL.getManaCapacity(), MATERIAL.getScaleCapacityIfPrimed(), (int) MATERIAL.getManaCapacity(), MATERIAL.getManaRecovery(), MATERIAL.getScaleRecoverIfPrimed(), null));
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return super.initCapabilities(stack, nbt);
    }

}
