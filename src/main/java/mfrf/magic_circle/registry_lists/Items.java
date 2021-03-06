package mfrf.magic_circle.registry_lists;

import mfrf.magic_circle.Config;
import mfrf.magic_circle.MagicCircle;
import mfrf.magic_circle.item.ItemBase;
import mfrf.magic_circle.item.ItemStaff;
import mfrf.magic_circle.item.RevalStaff;
import mfrf.magic_circle.item.armor.MagesBoots;
import mfrf.magic_circle.item.armor.MagesCloak;
import mfrf.magic_circle.item.armor.MagesCrown;
import mfrf.magic_circle.item.armor.MagesGaiters;
import mfrf.magic_circle.item.resources.ItemCompletedTestPaper;
import mfrf.magic_circle.item.resources.ItemMagicCrystal;
import mfrf.magic_circle.item.resources.ItemTestPaper;
import mfrf.magic_circle.item.tool.ItemPenAndInk;
import mfrf.magic_circle.util.MagicalItemContainer;
import mfrf.magic_circle.util.MagicalItemSimpleImplement;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

public class Items {
    public static final DeferredRegister<Item> ITEM_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, MagicCircle.MOD_ID);
    public static final Item.Properties DEFAULT_ITEM_PROPERTY = new Item.Properties().tab(MagicCircle.MAGIC_CIRCLE_RESOURCES);
    public static final Item.Properties GEM_ITEM_PROPERTY = new Item.Properties().tab(MagicCircle.MAGIC_CIRCLE_RESOURCES).stacksTo(1);
    public static final Item.Properties STAFF_PROPERTY = new Item.Properties().tab(MagicCircle.MAGIC_CIRCLE_RESOURCES).stacksTo(1).fireResistant();

    public static final Map<String, RegistryObject<Item>> DEFAULT_ITEMS = registerNormalItems("magic_fragment,dusty_gem".split(","));
    public static final RegistryObject<Item> MAGIC_CRYSTAL = registerObject(new ItemMagicCrystal(GEM_ITEM_PROPERTY), "magic_crystal");
    public static final RegistryObject<Item> RUBY = registerObject(new ItemMagicCrystal(GEM_ITEM_PROPERTY), "ruby");
    public static final RegistryObject<Item> STARLIGHT_RUBY = registerObject(new ItemMagicCrystal(GEM_ITEM_PROPERTY), "starlight_ruby");
    public static final RegistryObject<Item> SAPPSIRE = registerObject(new ItemMagicCrystal(GEM_ITEM_PROPERTY), "sappsire");
    public static final RegistryObject<Item> STARTLIGHT_SAPPSIRE = registerObject(new ItemMagicCrystal(GEM_ITEM_PROPERTY), "starlight_sappsire");
    public static final RegistryObject<Item> SUNSTONE = registerObject(new ItemMagicCrystal(GEM_ITEM_PROPERTY), "sunstone");
    public static final RegistryObject<Item> MOONSTONE = registerObject(new ItemMagicCrystal(GEM_ITEM_PROPERTY), "moonston");


    // Mage's
    // Armor===============================================================================================================================================================================================
    public static final RegistryObject<MagesBoots> MAGES_BOOTS = ITEM_DEFERRED_REGISTER.register("mages_boots", () -> new MagesBoots(new Item.Properties().tab(MagicCircle.MAGIC_CIRCLE_RESOURCES).stacksTo(1).fireResistant().durability(Config.DURABILITY_OF_MAGES_ARMOR.get()), MagicalItemContainer.Slot.createArray(50)));
    public static final RegistryObject<MagesGaiters> MAGES_GAITERS = ITEM_DEFERRED_REGISTER.register("mages_gaiters", () -> new MagesGaiters(new Item.Properties().tab(MagicCircle.MAGIC_CIRCLE_RESOURCES).stacksTo(1).fireResistant().durability(Config.DURABILITY_OF_MAGES_ARMOR.get()), MagicalItemContainer.Slot.createArray(50)));
    public static final RegistryObject<MagesCloak> MAGES_CLOAK = ITEM_DEFERRED_REGISTER.register("mages_cloak", () -> new MagesCloak(new Item.Properties().tab(MagicCircle.MAGIC_CIRCLE_RESOURCES).stacksTo(1).fireResistant().durability(Config.DURABILITY_OF_MAGES_ARMOR.get()), MagicalItemContainer.Slot.createArray(50)));
    public static final RegistryObject<MagesCrown> MAGES_CROWN = ITEM_DEFERRED_REGISTER.register("mages_crown", () -> new MagesCrown(new Item.Properties().tab(MagicCircle.MAGIC_CIRCLE_RESOURCES).stacksTo(1).fireResistant().durability(Config.DURABILITY_OF_MAGES_ARMOR.get()), MagicalItemContainer.Slot.createArray(50)));
    // Equipment===============================================================================================================================================================================================
    public static final RegistryObject<ItemPenAndInk> PEN_AND_INK = ITEM_DEFERRED_REGISTER.register("pen_and_ink", () -> new ItemPenAndInk(new Item.Properties().tab(MagicCircle.MAGIC_CIRCLE_RESOURCES).stacksTo(1).durability(Config.MAX_USE_PEN_AND_INK.get()).fireResistant()));
    public static final RegistryObject<ItemTestPaper> TEST_PAPER = ITEM_DEFERRED_REGISTER.register("test_paper", () -> new ItemTestPaper(new Item.Properties().tab(MagicCircle.MAGIC_CIRCLE_RESOURCES).stacksTo(1).fireResistant()));
    public static final RegistryObject<ItemCompletedTestPaper> TEST_PAPER_100 = ITEM_DEFERRED_REGISTER.register("test_paper_100", () -> new ItemCompletedTestPaper(new Item.Properties().tab(MagicCircle.MAGIC_CIRCLE_RESOURCES).stacksTo(1).fireResistant()));

    // staff===============================================================================================================================================================================================
    public static final RegistryObject<ItemStaff> CREATIVE_STAFF = ITEM_DEFERRED_REGISTER.register("creative_stuff", () -> new ItemStaff(STAFF_PROPERTY, new MagicalItemSimpleImplement(
            new MagicalItemContainer(
                    new MagicalItemContainer.Slot[]{
                            new MagicalItemContainer.Slot(Integer.MAX_VALUE, ItemStack.EMPTY),
                            new MagicalItemContainer.Slot(Integer.MAX_VALUE, ItemStack.EMPTY),
                            new MagicalItemContainer.Slot(Integer.MAX_VALUE, ItemStack.EMPTY),
                            new MagicalItemContainer.Slot(Integer.MAX_VALUE, ItemStack.EMPTY),
                            new MagicalItemContainer.Slot(Integer.MAX_VALUE, ItemStack.EMPTY),
                            new MagicalItemContainer.Slot(Integer.MAX_VALUE, ItemStack.EMPTY),
                            new MagicalItemContainer.Slot(Integer.MAX_VALUE, ItemStack.EMPTY),
                            new MagicalItemContainer.Slot(Integer.MAX_VALUE, ItemStack.EMPTY),
                    }),
            Integer.MAX_VALUE, 0, Integer.MAX_VALUE, Integer.MAX_VALUE, 0, 12, null)));
    public static final RegistryObject<ItemStaff> STAFF_1 = ITEM_DEFERRED_REGISTER.register("stuff_1", () -> new ItemStaff(STAFF_PROPERTY, new MagicalItemSimpleImplement(
            new MagicalItemContainer(
                    new MagicalItemContainer.Slot[]{
                            new MagicalItemContainer.Slot(2, ItemStack.EMPTY),
                            new MagicalItemContainer.Slot(2, ItemStack.EMPTY),
                            new MagicalItemContainer.Slot(2, ItemStack.EMPTY)}),
            100, 0.8, 100, 20, 1, 1, null)));
    public static final RegistryObject<ItemStaff> STAFF_2 = ITEM_DEFERRED_REGISTER.register("stuff_2", () -> new ItemStaff(STAFF_PROPERTY, new MagicalItemSimpleImplement(
            new MagicalItemContainer(
                    new MagicalItemContainer.Slot[]{
                            new MagicalItemContainer.Slot(4, ItemStack.EMPTY),
                            new MagicalItemContainer.Slot(4, ItemStack.EMPTY),
                            new MagicalItemContainer.Slot(4, ItemStack.EMPTY),
                            new MagicalItemContainer.Slot(4, ItemStack.EMPTY),
                            new MagicalItemContainer.Slot(4, ItemStack.EMPTY)
                    }),
            280, 1.5, 280, 15, 0.7, 3, null)));
    public static final RegistryObject<ItemStaff> STAFF_3 = ITEM_DEFERRED_REGISTER.register("stuff_3", () -> new ItemStaff(STAFF_PROPERTY, new MagicalItemSimpleImplement(
            new MagicalItemContainer(
                    new MagicalItemContainer.Slot[]{
                            new MagicalItemContainer.Slot(5, ItemStack.EMPTY),
                            new MagicalItemContainer.Slot(5, ItemStack.EMPTY),
                            new MagicalItemContainer.Slot(5, ItemStack.EMPTY),
                            new MagicalItemContainer.Slot(5, ItemStack.EMPTY),
                            new MagicalItemContainer.Slot(5, ItemStack.EMPTY),
                            new MagicalItemContainer.Slot(5, ItemStack.EMPTY),
                            new MagicalItemContainer.Slot(5, ItemStack.EMPTY),
                            new MagicalItemContainer.Slot(5, ItemStack.EMPTY)
                    }),
            400, 1.2, 400, 50, 2, 5, null)));
    // staff===============================================================================================================================================================================================

    //temp
    public static final RegistryObject<RevalStaff> REVAL_STAFF = ITEM_DEFERRED_REGISTER.register("reveal_staff", () -> new RevalStaff(STAFF_PROPERTY));

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
