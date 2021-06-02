package mfrf.magic_circle.magicutil.nodes;

import java.util.function.Predicate;

import mfrf.magic_circle.magicutil.BGMPreferences;
import mfrf.magic_circle.magicutil.BaguaPrefer;
import mfrf.magic_circle.magicutil.MagicNodeBase;
import mfrf.magic_circle.magicutil.MagicStream;

public class FinalNode extends MagicNodeBase {

    public FinalNode(NodeType nodeType, float strengthModify, float rangeModify, float durationModify, float executeSpeedModify, float coolDownModify, float efficientModify, float weaknessModify, float shrinkModify, float brevityModify, float relayModify, float heatupModify, float wasteModify, BaguaPrefer prefer, BGMPreferences bgmPreferences, MagicNodeBase leftNode, MagicNodeBase rightNode, Predicate<MagicStream> condition) {
        super(nodeType, strengthModify, rangeModify, durationModify, executeSpeedModify, coolDownModify, efficientModify, weaknessModify, shrinkModify, brevityModify, relayModify, heatupModify, wasteModify, prefer, bgmPreferences, leftNode, rightNode, condition);
    }

    @Override
    public MagicStream apply(MagicStream magic) {
        return magic;
    }
}
