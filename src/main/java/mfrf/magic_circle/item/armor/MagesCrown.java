package mfrf.magic_circle.item.armor;

import mfrf.magic_circle.util.MagicalItemContainer;
import mfrf.magic_circle.util.MagicalItemSimpleImplement;
import net.minecraft.inventory.EquipmentSlotType;

public class MagesCrown extends MagesArmorBase {
    public MagesCrown(Properties p_i48534_3_, MagicalItemContainer.Slot[] slots) {
        super(EquipmentSlotType.HEAD, p_i48534_3_, new MagicalItemSimpleImplement(new MagicalItemContainer(slots), MATERIAL.getManaCapacity(), MATERIAL.getScaleCapacityIfPrimed(), (int) MATERIAL.getManaCapacity(), MATERIAL.getManaRecovery(), MATERIAL.getScaleRecoverIfPrimed(),slots.length, null));
    }
}
