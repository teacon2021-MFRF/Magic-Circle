package mfrf.magic_circle.item.armor;

import mfrf.magic_circle.util.EffectiveItemContainer;
import mfrf.magic_circle.util.MagicalItemSimpleImplement;
import net.minecraft.inventory.EquipmentSlotType;

public class MagesCloak extends MagesArmorBase {
    public MagesCloak(Properties p_i48534_3_, EffectiveItemContainer.Slot[] slots) {
        super(EquipmentSlotType.CHEST, p_i48534_3_, new MagicalItemSimpleImplement(new EffectiveItemContainer(slots), MATERIAL.getManaCapacity(), MATERIAL.getScaleCapacityIfPrimed(), (int) MATERIAL.getManaCapacity(), MATERIAL.getManaRecovery(), MATERIAL.getScaleRecoverIfPrimed(),slots.length, null));
    }
}
