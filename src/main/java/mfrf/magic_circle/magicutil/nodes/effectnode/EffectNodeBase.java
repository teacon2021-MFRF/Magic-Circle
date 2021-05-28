package mfrf.magic_circle.magicutil.nodes.effectnode;

import java.util.function.Predicate;

import mfrf.magic_circle.magicutil.BGMPreferences;
import mfrf.magic_circle.magicutil.EightDiragramsPrefer;
import mfrf.magic_circle.magicutil.MagicNodeBase;
import mfrf.magic_circle.magicutil.MagicStream;

public abstract class EffectNodeBase extends MagicNodeBase {

    public final EffectType effectType;

    public EffectNodeBase(float strengthModify, float rangeModify, float durationModify, float executeSpeedModify, float coolDownModify, float efficientModify, float weaknessModify, float shrinkModify, float brevityModify, float relayModify, float heatupModify, float wasteModify, EightDiragramsPrefer prefer, BGMPreferences bgmPreferences, MagicNodeBase leftNode, MagicNodeBase rightNode, Predicate<MagicStream> condition, EffectType effectType) {
        super(NodeType.EFFECT, strengthModify, rangeModify, durationModify, executeSpeedModify, coolDownModify, efficientModify, weaknessModify, shrinkModify, brevityModify, relayModify, heatupModify, wasteModify, prefer, bgmPreferences, leftNode, rightNode, condition);
        this.effectType = effectType;
    }

    public enum EffectType {
        REDSTONE, LIHUO, KANSHUI, KUNDI, GENSHAN, THUNDER, DUIZE, DRYSKY, SUNDAE, TAICHI;
    }
}
