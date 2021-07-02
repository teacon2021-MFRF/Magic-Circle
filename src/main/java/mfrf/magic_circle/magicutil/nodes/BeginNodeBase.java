package mfrf.magic_circle.magicutil.nodes;

import java.util.function.Predicate;

import mfrf.magic_circle.magicutil.MagicModelBase;
import mfrf.magic_circle.magicutil.MagicNodeBase;
import mfrf.magic_circle.magicutil.MagicStream;

public class BeginNodeBase extends MagicNodeBase {
    public MagicModelBase.type invokerType;
    public MagicModelBase.type receiverType;

    public BeginNodeBase(MagicModelBase.type invokerType, MagicModelBase.type reciverType, MagicNodeBase leftNode, MagicNodeBase rightNode, Predicate<MagicStream> condition) {
        super(NodeType.BEGIN, leftNode, rightNode, condition);
        this.invokerType = invokerType;
        this.receiverType = reciverType;
    }

    @Override
    public MagicStream apply(MagicStream magic) {
        return magic;
    }

    @Override
    public MagicStream applyWithRender(MagicStream magicStream) {
        return null;
    }

}
