package mfrf.magic_circle.magicutil.nodes.behaviornode;

import mfrf.magic_circle.magicutil.BGMPreferences;
import mfrf.magic_circle.magicutil.EightDiragramsPrefer;
import mfrf.magic_circle.magicutil.MagicNodeBase;
import mfrf.magic_circle.magicutil.MagicStream;

import java.util.function.Predicate;

public class BehaviorNodeBase extends MagicNodeBase {


    public BehaviorNodeBase(MagicNodeBase leftNode, MagicNodeBase rightNode, Predicate<MagicStream> condition) {
        super(NodeType.BEHAVIOR, leftNode, rightNode, condition);
    }

    @Override
    public MagicStream apply(MagicStream magic) {
        return null;
    }
}
