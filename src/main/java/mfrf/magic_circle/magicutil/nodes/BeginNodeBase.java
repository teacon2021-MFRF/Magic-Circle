package mfrf.magic_circle.magicutil.nodes;

import mfrf.magic_circle.magicutil.*;
import mfrf.magic_circle.magicutil.datastructure.MagicNodePropertyMatrix8By8;
import mfrf.magic_circle.rendering.MagicCircleComponentBase;
import net.minecraft.nbt.CompoundNBT;

public class BeginNodeBase extends MagicNodeBase {
    public Invoker.InvokerType invokerType;
    public Receiver.ReceiverType receiverType;

    public BeginNodeBase(Invoker.InvokerType invokerType, Receiver.ReceiverType reciverType) {
        super(NodeType.BEGIN);
        this.invokerType = invokerType;
        this.receiverType = reciverType;
    }

    public static MagicNodeBase deserializeNBT(CompoundNBT nbt, int layer, MagicNodePropertyMatrix8By8 matrix) {
        Invoker.InvokerType invoker = Invoker.InvokerType.valueOf(nbt.getString("invoker"));
        Receiver.ReceiverType receiver = Receiver.ReceiverType.valueOf(nbt.getString("receiver"));
        BeginNodeBase beginNodeBase = new BeginNodeBase(invoker, receiver);
        beginNodeBase.layer = layer;
        beginNodeBase.eigenMatrix = matrix;
        return beginNodeBase;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT compoundNBT = super.serializeNBT();
        compoundNBT.putString("invoker", invokerType.name());
        compoundNBT.putString("receiver", receiverType.name());
        return compoundNBT;
    }

    @Override
    public DataContainer apply(MagicStream magic) {
        magic.functions.add((magicStream, magicStreamInfo) -> {
            magicStreamInfo.data.receiverType = receiverType;
            magicStreamInfo.data.invokerType = invokerType;
            magic.Matrixtimes(this.eigenMatrix);
            return magic;
        });
        return new DataContainer(magic, true);
    }

    @Override
    public MagicCircleComponentBase<?> getRender() {
        return null;
    }

}
