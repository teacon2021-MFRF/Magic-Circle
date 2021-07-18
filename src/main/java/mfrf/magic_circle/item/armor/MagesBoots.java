package mfrf.magic_circle.item.armor;

import mfrf.magic_circle.util.MagicalItemContainer;
import mfrf.magic_circle.util.MagicalItemSimpleImplement;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;

public class MagesBoots extends MagesArmorBase {
    public MagesBoots(Properties p_i48534_3_, MagicalItemContainer.Slot[] slots) {
        super(EquipmentSlotType.FEET, p_i48534_3_, new MagicalItemSimpleImplement(new MagicalItemContainer(slots), MATERIAL.getManaCapacity(), (double) MATERIAL.getScaleCapacityIfPrimed(), (int) MATERIAL.getManaCapacity(), MATERIAL.getManaRecovery(), (double) MATERIAL.getScaleRecoverIfPrimed(), slots.length, null));
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return super.initCapabilities(stack, nbt);
    }

}
