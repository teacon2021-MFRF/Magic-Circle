package mfrf.magic_circle.magicutil.nodes.decoratenode;

import mfrf.magic_circle.magicutil.BGMPreferences;
import mfrf.magic_circle.magicutil.BaguaPrefer;
import mfrf.magic_circle.magicutil.MagicNodeBase;
import mfrf.magic_circle.magicutil.MagicStream;

import java.util.function.Predicate;

public abstract class DecorateNodeBase extends MagicNodeBase {
    public final DecorateType decorateType;

    public DecorateNodeBase(float strengthModify, float rangeModify, float durationModify, float executeSpeedModify, float coolDownModify, float efficientModify, float weaknessModify, float shrinkModify, float brevityModify, float relayModify, float heatupModify, float wasteModify, BaguaPrefer prefer, BGMPreferences bgmPreferences, MagicNodeBase leftNode, MagicNodeBase rightNode, Predicate<MagicStream> condition, DecorateType decorateType) {
        super(NodeType.DECORATE, strengthModify, rangeModify, durationModify, executeSpeedModify, coolDownModify, efficientModify, weaknessModify, shrinkModify, brevityModify, relayModify, heatupModify, wasteModify, prefer, bgmPreferences, leftNode, rightNode, condition);
        this.decorateType = decorateType;
    }

    public enum DecorateType{
        STRENGTH,CONTINUOUS,EXPANSION,INVERT,POWER, DIAGONALIZE;
    }
}
