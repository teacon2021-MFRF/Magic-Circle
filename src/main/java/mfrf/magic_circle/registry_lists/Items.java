package mfrf.magic_circle.registry_lists;

import java.util.HashMap;
import java.util.Map;

import mfrf.magic_circle.Config;
import mfrf.magic_circle.MagicCircle;
import mfrf.magic_circle.item.ItemBase;
import mfrf.magic_circle.item.ItemStaff;
import mfrf.magic_circle.item.armor.MagesBoots;
import mfrf.magic_circle.item.armor.MagesCloak;
import mfrf.magic_circle.item.armor.MagesCrown;
import mfrf.magic_circle.item.armor.MagesGaiters;
import mfrf.magic_circle.item.resources.ItemMagicCrystal;
import mfrf.magic_circle.item.resources.ItemTestPaper;
import mfrf.magic_circle.item.tool.ItemPenAndInk;
import mfrf.magic_circle.util.EffectiveItemContainer;
import mfrf.magic_circle.util.MagicalItemSimpleImplement;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Items {
    public static final DeferredRegister<Item> ITEM_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, MagicCircle.MOD_ID);
    public static final Item.Properties DEFAULT_ITEM_PROPERTY = new Item.Properties().tab(MagicCircle.MAGIC_CIRCLE_RESOURCES);
    public static final Item.Properties STAFF_PROPERTY = new Item.Properties().tab(MagicCircle.MAGIC_CIRCLE_RESOURCES).stacksTo(1).fireResistant();

    public static final Map<String, RegistryObject<Item>> DEFAULT_ITEMS = registerNormalItems("ruby,starlight_ruby,sappsire,starlight_sappsire,sunstone,moonstone,magic_fragment,dusty_gem".split(","));
    public static final RegistryObject<Item> MAGIC_CRYSTAL = registerObject(new ItemMagicCrystal(DEFAULT_ITEM_PROPERTY), "magic_crystal");

    // Mage's
    // Armor===============================================================================================================================================================================================
    public static final RegistryObject<MagesBoots> MAGES_BOOTS = ITEM_DEFERRED_REGISTER.register("mages_boots", () -> new MagesBoots(new Item.Properties().tab(MagicCircle.MAGIC_CIRCLE_RESOURCES).stacksTo(1).fireResistant().durability(Config.DURABILITY_OF_MAGES_ARMOR.get()), EffectiveItemContainer.Slot.createArray(50)));
    public static final RegistryObject<MagesGaiters> MAGES_GAITERS = ITEM_DEFERRED_REGISTER.register("mages_gaiters", () -> new MagesGaiters(new Item.Properties().tab(MagicCircle.MAGIC_CIRCLE_RESOURCES).stacksTo(1).fireResistant().durability(Config.DURABILITY_OF_MAGES_ARMOR.get()), EffectiveItemContainer.Slot.createArray(50)));
    public static final RegistryObject<MagesCloak> MAGES_CLOAK = ITEM_DEFERRED_REGISTER.register("mages_cloak", () -> new MagesCloak(new Item.Properties().tab(MagicCircle.MAGIC_CIRCLE_RESOURCES).stacksTo(1).fireResistant().durability(Config.DURABILITY_OF_MAGES_ARMOR.get()), EffectiveItemContainer.Slot.createArray(50)));
    public static final RegistryObject<MagesCrown> MAGES_CROWN = ITEM_DEFERRED_REGISTER.register("mages_crown", () -> new MagesCrown(new Item.Properties().tab(MagicCircle.MAGIC_CIRCLE_RESOURCES).stacksTo(1).fireResistant().durability(Config.DURABILITY_OF_MAGES_ARMOR.get()), EffectiveItemContainer.Slot.createArray(50)));
    // Equipment===============================================================================================================================================================================================
    public static final RegistryObject<ItemPenAndInk> PEN_AND_INK = ITEM_DEFERRED_REGISTER.register("pen_and_ink", () -> new ItemPenAndInk(new Item.Properties().tab(MagicCircle.MAGIC_CIRCLE_RESOURCES).stacksTo(1).durability(Config.MAX_USE_PEN_AND_INK.get()).fireResistant()));
    public static final RegistryObject<ItemTestPaper> TEST_PAPER = ITEM_DEFERRED_REGISTER.register("test_paper", () -> new ItemTestPaper(new Item.Properties().tab(MagicCircle.MAGIC_CIRCLE_RESOURCES).stacksTo(1).fireResistant()));

    // staff===============================================================================================================================================================================================
    public static final RegistryObject<ItemStaff> CREATIVE_STAFF = ITEM_DEFERRED_REGISTER.register("creative_stuff", () -> new ItemStaff(STAFF_PROPERTY, new MagicalItemSimpleImplement(
            new EffectiveItemContainer(
                    new EffectiveItemContainer.Slot[]{
                            new EffectiveItemContainer.Slot(Integer.MAX_VALUE, ItemStack.EMPTY),
                            new EffectiveItemContainer.Slot(Integer.MAX_VALUE, ItemStack.EMPTY),
                            new EffectiveItemContainer.Slot(Integer.MAX_VALUE, ItemStack.EMPTY),
                            new EffectiveItemContainer.Slot(Integer.MAX_VALUE, ItemStack.EMPTY),
                            new EffectiveItemContainer.Slot(Integer.MAX_VALUE, ItemStack.EMPTY),
                            new EffectiveItemContainer.Slot(Integer.MAX_VALUE, ItemStack.EMPTY),
                            new EffectiveItemContainer.Slot(Integer.MAX_VALUE, ItemStack.EMPTY),
                            new EffectiveItemContainer.Slot(Integer.MAX_VALUE, ItemStack.EMPTY),
                            new EffectiveItemContainer.Slot(Integer.MAX_VALUE, ItemStack.EMPTY),
                            new EffectiveItemContainer.Slot(Integer.MAX_VALUE, ItemStack.EMPTY),
                            new EffectiveItemContainer.Slot(Integer.MAX_VALUE, ItemStack.EMPTY),
                            new EffectiveItemContainer.Slot(Integer.MAX_VALUE, ItemStack.EMPTY)
                    }),
            Integer.MAX_VALUE, 0, Integer.MAX_VALUE, Integer.MAX_VALUE, 0, 12, null)));
    public static final RegistryObject<ItemStaff> STAFF_1 = ITEM_DEFERRED_REGISTER.register("stuff_1", () -> new ItemStaff(STAFF_PROPERTY, new MagicalItemSimpleImplement(
            new EffectiveItemContainer(
                    new EffectiveItemContainer.Slot[]{
                            new EffectiveItemContainer.Slot(2, ItemStack.EMPTY)}),
            100, 0.8, 100, 20, 1, 1, null)));
    public static final RegistryObject<ItemStaff> STAFF_2 = ITEM_DEFERRED_REGISTER.register("stuff_2", () -> new ItemStaff(STAFF_PROPERTY, new MagicalItemSimpleImplement(
            new EffectiveItemContainer(
                    new EffectiveItemContainer.Slot[]{
                            new EffectiveItemContainer.Slot(4, ItemStack.EMPTY),
                            new EffectiveItemContainer.Slot(4, ItemStack.EMPTY),
                            new EffectiveItemContainer.Slot(4, ItemStack.EMPTY)
                    }),
            280, 1.5, 280, 15, 0.7, 3, null)));
    public static final RegistryObject<ItemStaff> STAFF_3 = ITEM_DEFERRED_REGISTER.register("stuff_3", () -> new ItemStaff(STAFF_PROPERTY, new MagicalItemSimpleImplement(
            new EffectiveItemContainer(
                    new EffectiveItemContainer.Slot[]{
                            new EffectiveItemContainer.Slot(5, ItemStack.EMPTY),
                            new EffectiveItemContainer.Slot(5, ItemStack.EMPTY),
                            new EffectiveItemContainer.Slot(5, ItemStack.EMPTY),
                            new EffectiveItemContainer.Slot(5, ItemStack.EMPTY),
                            new EffectiveItemContainer.Slot(5, ItemStack.EMPTY)
                    }),
            400, 1.2, 400, 50, 2, 5, null)));
    // staff===============================================================================================================================================================================================

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
