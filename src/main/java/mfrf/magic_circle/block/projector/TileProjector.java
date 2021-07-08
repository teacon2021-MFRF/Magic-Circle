package mfrf.magic_circle.block.projector;

import javax.annotation.Nullable;

import mfrf.magic_circle.magicutil.Invoker;
import mfrf.magic_circle.magicutil.MagicModelBase;
import mfrf.magic_circle.magicutil.MagicStream;
import mfrf.magic_circle.magicutil.Receiver;
import mfrf.magic_circle.magicutil.nodes.BeginNodeBase;
import mfrf.magic_circle.magicutil.nodes.behaviornode.ThrowBehaviorNode;
import mfrf.magic_circle.registry_lists.TileEntities;
import mfrf.magic_circle.rendering.MagicCircleComponentBase;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.vector.Vector3f;

public class TileProjector extends TileEntity implements ITickableTileEntity {
    public float time = 0;
    private float maxTime = 20;
    public MagicModelBase magicCircleComponentBase = new MagicModelBase(null);

    public TileProjector() {
        super(TileEntities.PROJECTOR.get());
    }

    @Override
    public void tick() {
        // if (!world.isRemote()) {
        if (time >= maxTime) {
            time = 0;
            BeginNodeBase beginNodeBase = new BeginNodeBase(Invoker.InvokerType.BLOCK, Receiver.ReceiverType.BLOCK);
            ThrowBehaviorNode throwBehaviorNode = new ThrowBehaviorNode();


            Invoker invoker = new Invoker(getBlockPos(), level.dimensionType(), ItemStack.EMPTY, null, null, level, Invoker.InvokerType.BLOCK);
            Receiver receiver = new Receiver(new Vector3f(0, 1, 0), getBlockPos().offset(0, 1, 0), level.dimensionType(), new Receiver.WeatherType(level.rainLevel, level.thunderLevel), Receiver.RangeType.CIRCLE, 16, 16, 16, 16, level, Receiver.ReceiverType.BLOCK);
            MagicStream magicStream = new MagicStream(new MagicStream.MagicStreamInfo(invoker, null, receiver));
            magicCircleComponentBase.apply(magicStream);

        }
        time += 0.05f;
        setChanged();
        // }
        // getUpdatePacket();
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
