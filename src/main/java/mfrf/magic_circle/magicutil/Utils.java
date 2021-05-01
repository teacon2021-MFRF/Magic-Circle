package mfrf.magic_circle.magicutil;

import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Utils {
    public static HashMap<Item, Function<MagicModelBase, MagicModelBase>> EFFECT_MAP = new HashMap();
}
