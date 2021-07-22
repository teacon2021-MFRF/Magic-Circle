package mfrf.magic_circle.block.projector;

import mfrf.magic_circle.magicutil.Invoker;
import mfrf.magic_circle.magicutil.MagicModelBase;
import mfrf.magic_circle.magicutil.MagicStream;
import mfrf.magic_circle.magicutil.Receiver;
import mfrf.magic_circle.magicutil.datastructure.MagicNodePropertyMatrix8By8;
import mfrf.magic_circle.magicutil.nodes.BeginNodeBase;
import mfrf.magic_circle.magicutil.nodes.behaviornode.ThrowBehaviorNode;
import mfrf.magic_circle.registry_lists.TileEntities;
import mfrf.magic_circle.util.PositionExpression;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.vector.Vector3f;

import javax.annotation.Nullable;

public class TileProjector extends TileEntity implements ITickableTileEntity {
    public float time = 0;
    private float maxTime = 20;
    public MagicModelBase magicCircleComponentBase = new MagicModelBase(null);

    public TileProjector() {
        super(TileEntities.PROJECTOR.get());
    }

    @Override
    public void tick() {
        if (!level.isClientSide()) {
            if (time >= maxTime) {
                time = 0;
                BeginNodeBase beginNodeBase = new BeginNodeBase();
                ThrowBehaviorNode throwBehaviorNode = new ThrowBehaviorNode();
                throwBehaviorNode.setPositionExpression(new PositionExpression("2*(2*math.cos(t)-math.cos(2*t))", "0", "2*(2*math.sin(t)-math.sin(2*t))", null, null, null));
                beginNodeBase.appendNodeL(throwBehaviorNode);

                magicCircleComponentBase = new MagicModelBase(beginNodeBase);

                Invoker invoker = new Invoker(getBlockPos(), level.dimensionType(), ItemStack.EMPTY, null, null, level, Invoker.InvokerType.BLOCK);
                Receiver receiver = new Receiver(new Vector3f(0, 0, 1), getBlockPos().offset(0, 1, 0), new Receiver.WeatherType(level.rainLevel, level.thunderLevel), Receiver.RangeType.CIRCLE, 16, 16, 16, 16, level, Receiver.ReceiverType.BLOCK);
                MagicStream.DataContain dataContain = new MagicStream.DataContain(invoker, receiver);
                dataContain.eigenMatrix.set(MagicNodePropertyMatrix8By8.INDEX.DURATION, 40);
                dataContain.targetVec = new Vector3f(0, 0, 1);
                MagicStream magicStream = new MagicStream(new MagicStream.MagicStreamInfo(null, dataContain));
                MagicStream apply = magicCircleComponentBase.invoke(magicStream);
                apply.apply();
                setChanged();

            }
            time += 1f;
            setChanged();
        }
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        super.deserializeNBT(nbt);
        time = nbt.getFloat("time");
        magicCircleComponentBase = MagicModelBase.deserializeNBT(nbt);
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT compoundNBT = super.serializeNBT();
        compoundNBT.putFloat("time", time);
        compoundNBT.put("model", magicCircleComponentBase.serializeNBT());
        return compoundNBT;
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(worldPosition, 1, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        handleUpdateTag(level.getBlockState(pkt.getPos()), pkt.getTag());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT compoundNBT = super.getUpdateTag();
        compoundNBT.putFloat("time", time);
        return compoundNBT;
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        time = tag.getFloat("time");
    }

}
