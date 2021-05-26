package mfrf.magic_circle.item.armor;

import mfrf.magic_circle.registry_lists.Items;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;

import net.minecraft.item.Item.Properties;

public class MagesArmorBase extends ArmorItem {
    public MagesArmorBase(EquipmentSlotType p_i48534_2_, Properties p_i48534_3_) {
        super((IArmorMaterial) Items.MAGIC_CRYSTAL.get(), p_i48534_2_, p_i48534_3_);
    }
}
