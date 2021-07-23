package mfrf.magic_circle.network.execute_render_data;

import mfrf.magic_circle.MagicCircle;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class ExecuteRenderDataSync {
    public static SimpleChannel INSTANCE;
    public static final String VERSION = "1.0";
    private static int ID = 0;

    public static void registerMessage() {
        INSTANCE = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(MagicCircle.MOD_ID, "sync_render"),
                () -> VERSION,
                (version) -> version.equals(VERSION),
                (version) -> version.equals(VERSION));
        INSTANCE.registerMessage(
                nextID(),
                SendPack.class,
                SendPack::toBytes,
                SendPack::new,
                SendPack::handler
        );
    }


    public static int nextID() {
        return ID++;
    }
}
