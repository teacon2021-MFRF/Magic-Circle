package mfrf.magic_circle.registry_lists;

import mfrf.magic_circle.MagicCircle;
import mfrf.magic_circle.item.ItemBase;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Items {
    public static final DeferredRegister<Item> ITEM_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, MagicCircle.MOD_ID);

    public static final RegistryObject<Item> MAGIC_CRYSTAL = registryObject(new ItemBase(new Item.Properties().group(MagicCircle.MAGIC_CIRCLE_RESOURCES), "magic_crystal"));

    public static RegistryObject<Item> registryObject(Item item) {
        return ITEM_DEFERRED_REGISTER.register(item.getRegistryName().getPath().toString(), () -> item);
    }
}
