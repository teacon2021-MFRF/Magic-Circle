package mfrf.magic_circle.network.send_answer;

import mfrf.magic_circle.block.research_table.TileResearchTable;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.nio.charset.StandardCharsets;
import java.util.function.Supplier;

public class SendPack {
    private BlockPos pos;
    private final String answer;

    public SendPack(String answer, BlockPos pos) {
        this.answer = answer;
        this.pos = pos;
    }

    public SendPack(PacketBuffer buffer) {
        answer = buffer.readUtf(32767);
        pos = buffer.readBlockPos();
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeUtf(answer);
        buf.writeBlockPos(pos);
    }

    public void handler(Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            ServerPlayerEntity sender = context.getSender();
            World level = sender.level;
            if (level.hasChunkAt(pos)) {
                TileEntity blockEntity = level.getBlockEntity(pos);
                if (blockEntity instanceof TileResearchTable) {
                    TileResearchTable tileResearchTable = (TileResearchTable) blockEntity;
                    boolean flag = tileResearchTable.checkAnswer(answer, sender.getUUID());
                    if (flag) {
                        sender.addEffect(new EffectInstance(Effects.LUCK, 600, 3));
                    } else {
                        sender.addEffect(new EffectInstance(Effects.UNLUCK, 600, 1));
                        sender.addEffect(new EffectInstance(Effects.CONFUSION, 200, 1));

                        if (level.getRandom().nextFloat() < 0.00002) {
                            sender.hurt(new DamageSource(new StringTextComponent("magic_circle.research.death").getString()).setMagic().setScalesWithDifficulty(), sender.getMaxHealth());
                        }
                    }
                }
            }
        });
        context.setPacketHandled(true);
    }
}
