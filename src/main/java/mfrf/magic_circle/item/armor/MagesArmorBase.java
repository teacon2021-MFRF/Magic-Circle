package mfrf.magic_circle.item.armor;

import mfrf.magic_circle.item.resources.ItemMagicCrystal;
import mfrf.magic_circle.registry_lists.Items;
import mfrf.magic_circle.util.MagicalItemSimpleImplement;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;

public class MagesArmorBase extends ArmorItem {
    private final MagicalItemSimpleImplement implement;
    protected final static ItemMagicCrystal MATERIAL = (ItemMagicCrystal) Items.MAGIC_CRYSTAL.get();

    public MagesArmorBase(EquipmentSlotType p_i48534_2_, Properties p_i48534_3_, MagicalItemSimpleImplement implement) {
        super(MATERIAL, p_i48534_2_, p_i48534_3_);
        //todo finish me
        //todo study the modernui lib
        this.implement = implement;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return implement.copy(stack, nbt);
    }
}
