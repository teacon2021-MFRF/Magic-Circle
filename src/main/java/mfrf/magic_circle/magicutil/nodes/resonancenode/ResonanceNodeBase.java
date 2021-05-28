package mfrf.magic_circle.magicutil.nodes.resonancenode;

import java.util.function.Predicate;

import mfrf.magic_circle.magicutil.MagicNodeBase;
import mfrf.magic_circle.magicutil.MagicStream;

public abstract class ResonanceNodeBase extends MagicNodeBase {

    public final ResonanceType resonanceType;

    public ResonanceNodeBase(MagicNodeBase leftNode, MagicNodeBase rightNode, Predicate<MagicStream> condition, ResonanceType resonanceType) {
        super(NodeType.RESONANCE, leftNode, rightNode, condition);
        this.resonanceType = resonanceType;
    }

    public enum ResonanceType {
        CHANT, PASSIONATE, MUFFLED, SHOCK, SONOROUS, PIANO, SCALE1, SCALE2, SCALE3, SCALE4, SCALE5, SCALE6, SCALE7, SCALE8, SIN, TIME_DOMAIN, FREQUENCY_DOMAIN, RANGE_DOMAIN;
    }
}
