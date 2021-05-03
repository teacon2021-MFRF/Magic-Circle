package mfrf.magic_circle.registry_lists;

import mfrf.magic_circle.MagicCircle;
import mfrf.magic_circle.item.ItemBase;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

public class Items {
    public static final DeferredRegister<Item> ITEM_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, MagicCircle.MOD_ID);
    public static final Item.Properties DEFAULT_ITEM_PROPERTY = new Item.Properties().group(MagicCircle.MAGIC_CIRCLE_RESOURCES);

    public static final Map<String, RegistryObject<Item>> DEFAULT_ITEMS = registerNormalItems("ruby,starlight_ruby,sappsire,starlight_sappsire,sunstone,moonstone".split(","));

    public static RegistryObject<Item> registerObject(Item item, String name) {
        return ITEM_DEFERRED_REGISTER.register(name, () -> item);
    }

    public static Map<String, RegistryObject<Item>> registerNormalItems(String... strings) {
        HashMap<String, RegistryObject<Item>> stringRegistryObjectHashMap = new HashMap<>();
        for (String string : strings) {
            stringRegistryObjectHashMap.put(string, registerObject(new ItemBase(DEFAULT_ITEM_PROPERTY), string));
        }
        return stringRegistryObjectHashMap;
    }
}
