package mfrf.magic_circle.magicutil.nodes;

import mfrf.magic_circle.gui.widgets.Argument;
import mfrf.magic_circle.magicutil.Invoker;
import mfrf.magic_circle.magicutil.MagicNodeBase;
import mfrf.magic_circle.magicutil.MagicStream;
import mfrf.magic_circle.magicutil.Receiver;
import mfrf.magic_circle.magicutil.datastructure.MagicNodePropertyMatrix8By8;
import mfrf.magic_circle.rendering.MagicCircleComponentBase;
import net.minecraft.nbt.CompoundNBT;

import java.util.ArrayList;

public class BeginNodeBase extends MagicNodeBase {
    public Invoker.InvokerType invokerType = Invoker.InvokerType.AUTO;
    public Receiver.ReceiverType receiverType = Receiver.ReceiverType.BLOCK;

    public BeginNodeBase() {
        super(NodeType.BEGIN);
    }

    public static MagicNodeBase deserializeNBT(CompoundNBT nbt, int layer, MagicNodePropertyMatrix8By8 matrix) {
        Invoker.InvokerType invoker = Invoker.InvokerType.valueOf(nbt.getString("invoker"));
        Receiver.ReceiverType receiver = Receiver.ReceiverType.valueOf(nbt.getString("receiver"));
        BeginNodeBase beginNodeBase = new BeginNodeBase();
        beginNodeBase.invokerType = invoker;
        beginNodeBase.receiverType = receiver;
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

    @Override
    public ArrayList<Argument<?>> getArguments() {
        return new ArrayList<>();
    }

}
