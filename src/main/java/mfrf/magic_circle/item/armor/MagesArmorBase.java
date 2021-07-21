package mfrf.magic_circle.item.armor;

import mfrf.magic_circle.item.resources.ItemMagicCrystal;
import mfrf.magic_circle.registry_lists.Capabilities;
import mfrf.magic_circle.registry_lists.Items;
import mfrf.magic_circle.util.MagicalItemSimpleImplement;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;

public class MagesArmorBase extends ArmorItem{
    private final MagicalItemSimpleImplement implement;
    protected final static ItemMagicCrystal MATERIAL = (ItemMagicCrystal) Items.MAGIC_CRYSTAL.get();

    public MagesArmorBase(EquipmentSlotType p_i48534_2_, Properties p_i48534_3_, MagicalItemSimpleImplement implement) {
        super(MATERIAL, p_i48534_2_, p_i48534_3_);
        this.implement = implement;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return implement.copy(stack, nbt);
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        super.onArmorTick(stack, world, player);
        stack.getCapability(Capabilities.MAGICAL_ITEM).ifPresent(iMagicalItem -> {
            iMagicalItem.onTick();
        });
    }
}
