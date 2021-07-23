package mfrf.magic_circle.network.execute_render_data;

import mfrf.magic_circle.util.CachedEveryThingForClient;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class SendPack {
    private UUID playerUUID;
    private String name;
    private int tickBegin;
    private double range;
    private BlockPos pos;

    public SendPack(UUID playerUUID, String modelName, int tickBegin, double range, BlockPos pos) {
        this.playerUUID = playerUUID;
        this.tickBegin = tickBegin;
        this.name = modelName;
        this.range = range;
        this.pos = pos;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeUUID(playerUUID);
        buf.writeInt(tickBegin);
        buf.writeDouble(range);
        buf.writeBlockPos(pos);
        buf.writeUtf(name);
    }

    public SendPack(PacketBuffer buffer) {
        playerUUID = buffer.readUUID();
        tickBegin = buffer.readInt();
        range = buffer.readDouble();
        pos = buffer.readBlockPos();
        name = buffer.readUtf(32767);
    }

    public void handler(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            NetworkDirection direction = ctx.get().getDirection();

            if (direction == NetworkDirection.PLAY_TO_CLIENT) {

                CachedEveryThingForClient.setExecute(playerUUID, name, tickBegin, null, range, pos);

            }

        });
        ctx.get().setPacketHandled(true);
    }
}
