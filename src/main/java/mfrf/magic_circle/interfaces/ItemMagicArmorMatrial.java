package mfrf.magic_circle.interfaces;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ItemMagicArmorMatrial extends IArmorMaterial {
    @Override
    int getDurabilityForSlot(EquipmentSlotType equipmentSlotType);

    @Override
    int getDefenseForSlot(EquipmentSlotType equipmentSlotType);

    @Override
    SoundEvent getEquipSound();

    @Override
    Ingredient getRepairIngredient();

    @Override
    String getName();

    @Override
    float getToughness();

    @Override
    float getKnockbackResistance();

    float getManaCapacity();

    float getManaRecovery();

    float getScaleCapacityIfPrimed();

    float getScaleRecoverIfPrimed();

    void onPriming(World world, BlockPos pos);
}
