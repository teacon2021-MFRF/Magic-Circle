package mfrf.magic_circle.magicutil.nodes.beginnode;

import mfrf.magic_circle.magicutil.*;

import java.util.function.Predicate;

public class BeginNodeBase extends MagicNodeBase {
    public MagicModelBase.TYPE invokerType;
    public MagicModelBase.TYPE receiverType;

    public BeginNodeBase(MagicModelBase.TYPE invokerType, MagicModelBase.TYPE reciverType, MagicNodeBase leftNode, MagicNodeBase rightNode, Predicate<MagicStream> condition) {
        super(NodeType.BEGIN, leftNode, rightNode, condition);
        this.invokerType = invokerType;
        this.receiverType = reciverType;
    }

    @Override
    public MagicStream apply(MagicStream magic) {
        return magic;
    }

}
