//package mfrf.magic_circle.events;
//
//import mfrf.magic_circle.entity.barrage.DanmakuEntity;
//import net.minecraft.entity.Entity;
//import net.minecraft.util.math.BlockRayTraceResult;
//import net.minecraftforge.event.entity.living.LivingDamageEvent;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod;
//
//import java.util.LinkedHashMap;
//import java.util.UUID;
//import java.util.function.BiConsumer;
//import java.util.function.Consumer;
//
//@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
//public class InGameCaches {
//    public static LinkedHashMap<UUID, Consumer<LivingDamageEvent>> onDanmakuAttack = new LinkedHashMap<>();
//    public static LinkedHashMap<UUID, BiConsumer<BlockRayTraceResult,DanmakuEntity>> onDanmakuCrash = new LinkedHashMap<>();
//    public static LinkedHashMap<UUID, Consumer<DanmakuEntity>> onDanmakuTick = new LinkedHashMap<>();
//
//    @SubscribeEvent
//    public static void onAttack(LivingDamageEvent event) {
//        Entity entity = event.getSource().getEntity();
//        if (entity instanceof DanmakuEntity) {
//            DanmakuEntity danmakuEntity = (DanmakuEntity) entity;
//            UUID uuid = danmakuEntity.getUUID();
//            if (onDanmakuAttack.containsValue(uuid)) {
//                onDanmakuAttack.get(uuid).accept(event);
//                onDanmakuAttack.remove(uuid);
//            }
//        }
//    }
//}
