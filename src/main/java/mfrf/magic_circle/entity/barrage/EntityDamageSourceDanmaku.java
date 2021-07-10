package mfrf.magic_circle.entity.barrage;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nullable;

public class EntityDamageSourceDanmaku extends IndirectEntityDamageSource {
    public EntityDamageSourceDanmaku(Entity p_i1568_2_, @Nullable Entity p_i1568_3_) {
        super("magic", p_i1568_2_, p_i1568_3_);
        this.bypassArmor();
        this.setMagic();
        this.setProjectile();
    }

}
