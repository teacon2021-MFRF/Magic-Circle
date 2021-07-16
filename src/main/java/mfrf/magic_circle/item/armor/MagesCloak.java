package mfrf.magic_circle.item.armor;

import mfrf.magic_circle.util.MagicalItemContainer;
import mfrf.magic_circle.util.MagicalItemSimpleImplement;
import net.minecraft.inventory.EquipmentSlotType;

public class MagesCloak extends MagesArmorBase {
    public MagesCloak(Properties p_i48534_3_, MagicalItemContainer.Slot[] slots) {
        super(EquipmentSlotType.CHEST, p_i48534_3_, new MagicalItemSimpleImplement(new MagicalItemContainer(slots), MATERIAL.getManaCapacity(), (double) MATERIAL.getScaleCapacityIfPrimed(), (int) MATERIAL.getManaCapacity(), MATERIAL.getManaRecovery(), (double) MATERIAL.getScaleRecoverIfPrimed(), slots.length, null));
    }
}
