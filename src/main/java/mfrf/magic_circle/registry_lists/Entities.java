package mfrf.magic_circle.registry_lists;

import mfrf.magic_circle.MagicCircle;
import mfrf.magic_circle.entity.barrage.DanmakuEntity;
import mfrf.magic_circle.entity.magic_circle_base.EntityMagicCircle;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Entities {
    public static final DeferredRegister<EntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.ENTITIES, MagicCircle.MOD_ID);

    public static RegistryObject<EntityType<EntityMagicCircle>> ENTITY_MAGIC_CIRCLE_BASE = REGISTER.register("magic_circle", () -> EntityType.Builder.of(EntityMagicCircle::new, EntityClassification.MISC).fireImmune().noSummon().build("magic_circle"));
    public static RegistryObject<EntityType<DanmakuEntity>> DANMAKU_ENTITY = REGISTER.register("danmaku", () -> EntityType.Builder.of(DanmakuEntity::new, EntityClassification.MISC).fireImmune().noSummon().build("danmaku"));
}
