package mfrf.magic_circle.item.resources;

import mfrf.magic_circle.Config;
import mfrf.magic_circle.interfaces.ItemMagicArmorMatrial;
import mfrf.magic_circle.item.ItemBase;
import mfrf.magic_circle.registry_lists.Items;
import mfrf.magic_circle.util.MagicalItemContainer;
import mfrf.magic_circle.util.MagicalItemSimpleImplement;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;

public class ItemMagicCrystal extends ItemBase implements ItemMagicArmorMatrial {
    public static final MagicalItemSimpleImplement DEFAULT_INSTANCE = new MagicalItemSimpleImplement(new MagicalItemContainer(), 200f, 1.1, 0, 1f, 1.1, 3, ItemStack.EMPTY);

    public ItemMagicCrystal(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    @Override
    public int getDurabilityForSlot(EquipmentSlotType equipmentSlotType) {
        return Config.DURABILITY_OF_MAGES_ARMOR.get();
    }

    @Override
    public int getDefenseForSlot(EquipmentSlotType equipmentSlotType) {
        int i = 0;
        switch (equipmentSlotType) {
            case FEET:
                i = Config.DEFENSE_OF_MAGES_BOOTS.get();
                break;
            case HEAD:
                i = Config.DEFENSE_OF_MAGES_CROWN.get();
                break;
            case CHEST:
                i = Config.DEFENSE_OF_MAGES_CLOAK.get();
                break;
            case LEGS:
                i = Config.DEFENSE_OF_MAGES_GAITERS.get();
                break;
            default:
                i = 0;
        }
        return i;
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

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return DEFAULT_INSTANCE.copy(stack, nbt);
    }

}
