package mfrf.magic_circle.magicutil;

import mfrf.magic_circle.util.Colors;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

import java.awt.*;

public class BaguaPrefer implements INBTSerializable<CompoundNBT> {
    public float drysky;
    public float sundae;
    public float kanshui;
    public float genshan;
    public float kundi;
    public float thunder;
    public float lihuo;
    public float duize;
    public float yin;
    public float yang;
    private Color colorPrefer;

    public BaguaPrefer(float drysky, float sundae, float kanshui, float genshan, float kundi, float thunder, float lihuo, float duize) {
        this.drysky = drysky;
        this.sundae = sundae;
        this.kanshui = kanshui;
        this.genshan = genshan;
        this.kundi = kundi;
        this.thunder = thunder;
        this.lihuo = lihuo;
        this.duize = duize;
        this.yin = (kundi + genshan + kanshui + sundae) / 4.0f;
        this.yang = (thunder + lihuo + duize + drysky) / 4.0f;
    }

    public BaguaPrefer() {
    }

    public Color calculateColor() {
        if (colorPrefer == null) {
            float v = drysky + sundae + kanshui + genshan + kundi + thunder + lihuo + duize + yin + yang;
            colorPrefer = Colors.mix(drysky / v, sundae / v, kanshui / v, genshan / v, kundi / v, thunder / v, lihuo / v, duize / v, yin / v, yang / v);
        }
        return colorPrefer;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putFloat("drysky",drysky);
        compoundNBT.putFloat("sundae",sundae);
        compoundNBT.putFloat("kanshui",kanshui);
        compoundNBT.putFloat("genshan",genshan);
        compoundNBT.putFloat("kundi",kundi);
        compoundNBT.putFloat("thunder",thunder);
        compoundNBT.putFloat("lihuo",lihuo);
        compoundNBT.putFloat("duize",duize);
        compoundNBT.putFloat("yin",yin);
        compoundNBT.putFloat("yang",yang);
        return compoundNBT;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.drysky = nbt.getFloat("drysky");
        this.sundae = nbt.getFloat("sundae");
        this.kanshui = nbt.getFloat("kanshui");
        this.genshan = nbt.getFloat("genshan");
        this.kundi = nbt.getFloat("kundi");
        this.thunder = nbt.getFloat("thunder");
        this.lihuo = nbt.getFloat("lihuo");
        this.duize = nbt.getFloat("duize");
        this.yin = nbt.getFloat("yin");
        this.yang = nbt.getFloat("yang");
    }
}

