package mfrf.magic_circle.util;

import mfrf.magic_circle.magicutil.MagicModelBase;
import net.minecraft.item.Item;
import net.minecraft.tags.Tag;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Utils {
    public static HashMap<Tag<Item>, Properties> EFFECT_MAP = new HashMap();

    public class Properties {
        public Function<MagicModelBase, MagicModelBase> function;
        public int complexity;

        Properties(Function<MagicModelBase, MagicModelBase> function, int complexity) {
            this.function = function;
            this.complexity = complexity;
        }

    }
}
