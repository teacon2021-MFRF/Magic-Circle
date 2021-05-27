package mfrf.magic_circle.item.resources;

import mfrf.magic_circle.Config;
import mfrf.magic_circle.interfaces.ItemMagicArmorMatrial;
import mfrf.magic_circle.item.ItemBase;
import mfrf.magic_circle.registry_lists.Items;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemMagicCrystal extends ItemBase implements ItemMagicArmorMatrial {

    public ItemMagicCrystal(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    @Override
    public int getDurabilityForSlot(EquipmentSlotType equipmentSlotType) {
        return Config.DURABILITY_OF_MAGES_ARMOR.get();
    }

    @Override
    public int getDefenseForSlot(EquipmentSlotType equipmentSlotType) {
        return switch (equipmentSlotType) {
            case FEET -> Config.DEFENSE_OF_MAGES_BOOTS.get();
            case HEAD -> Config.DEFENSE_OF_MAGES_CROWN.get();
            case CHEST -> Config.DEFENSE_OF_MAGES_CLOAK.get();
            case LEGS -> Config.DEFENSE_OF_MAGES_GAITERS.get();
            default -> 0;
        };
    }

    @Override
    public int getEnchantmentValue() {
        return Config.ENCHANTMENTS_ABILITY_OF_MAGES_ARMOR.get();
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_LEATHER;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.of(new ItemStack(Items.DEFAULT_ITEMS.get("magic_fragment").get()));
    }

    @Override
    public String getName() {
        return this.getRegistryName().toString();
    }

    @Override
    public float getToughness() {
        return Config.TOUGHNESS_OF_MAGES_ARMOR.get();
    }

    @Override
    public float getKnockbackResistance() {
        return Config.KNOCKBACK_RESISTANCE_OF_MAGES_ARMOR.get();
    }

    @Override
    public float getManaCapacity() {
        return Config.MANA_CAPACITY_OF_MAGES_ARMOR.get();
    }

    @Override
    public float getManaRecovery() {
        return Config.MANA_RECOVERY_OF_MAGES_ARMOR.get();
    }

    @Override
    public float getScaleCapacityIfPrimed() {
        return Config.MANA_CAPACITY_SCALE_OF_PRIMED_MAGES_ARMOR.get();
    }

    @Override
    public float getScaleRecoverIfPrimed() {
        return Config.MANA_RECOVERY_SCALE_OF_PRIMED_MAGES_ARMOR.get();
    }

    @Override
    public void onPriming(World world, BlockPos pos) {

    }
}
