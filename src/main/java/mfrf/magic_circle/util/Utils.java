package mfrf.magic_circle.util;

import java.util.HashMap;
import java.util.function.Function;

import mfrf.magic_circle.magicutil.MagicModelBase;
import net.minecraft.item.Item;
import net.minecraft.tags.Tag;

public class Utils {
    public static HashMap<Tag<Item>, Properties> EFFECT_MAP = new HashMap();

    public class Properties {
        public final Function<MagicModelBase, MagicModelBase> function;
        private final Function<Integer, Integer> manaCapacityEffect;
        private final Function<Integer, Integer> manaRecoverEffect;
        public int complexity;

        Properties(Function<MagicModelBase, MagicModelBase> function, Function<Integer, Integer> manaCapacityEffect, Function<Integer, Integer> manaRecoverEffect, int complexity) {
            this.function = function;
            this.manaCapacityEffect = manaCapacityEffect;
            this.manaRecoverEffect = manaRecoverEffect;
            this.complexity = complexity;
        }

    }
}
