package mfrf.magic_circle.magicutil.nodes.beginnode;

import mfrf.magic_circle.interfaces.MatrixObjectComponent;
import mfrf.magic_circle.magicutil.*;
import mfrf.magic_circle.magicutil.datastructure.MagicNodePropertyMatrix8By8;

import java.util.function.Predicate;

public class BeginNodeBase extends MagicNodeBase {
    protected Receiver receiver = null;
    protected Invoker invoker = null;


    public BeginNodeBase(NodeType nodeType, float strengthModify, float rangeModify, float durationModify, float executeSpeedModify, float coolDownModify, float efficientModify, float weaknessModify, float shrinkModify, float brevityModify, float relayModify, float heatupModify, float wasteModify, EightDiragramsPrefer prefer, BGMPreferences bgmPreferences, MagicNodeBase leftNode, MagicNodeBase rightNode, Predicate<MagicStream> condition) {
        super(nodeType, strengthModify, rangeModify, durationModify, executeSpeedModify, coolDownModify, efficientModify, weaknessModify, shrinkModify, brevityModify, relayModify, heatupModify, wasteModify, prefer, bgmPreferences, leftNode, rightNode, condition);
    }

    @Override
    public MagicStream apply(MagicStream magic) {
        return null;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public Invoker getInvoker() {
        return invoker;
    }
}
