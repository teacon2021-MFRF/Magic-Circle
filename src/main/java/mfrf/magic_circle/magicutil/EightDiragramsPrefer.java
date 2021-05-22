package mfrf.magic_circle.magicutil;

import mfrf.magic_circle.util.Colors;

import java.awt.*;

public class EightDiragramsPrefer {
    public final float drysky;
    public final float sundae;
    public final float kanshui;
    public final float genshan;
    public final float kundi;
    public final float thunder;
    public final float lihuo;
    public final float duize;
    public final float yin;
    public final float yang;
    private Color colorPrefer;

    public EightDiragramsPrefer(float drysky, float sundae, float kanshui, float genshan, float kundi, float thunder, float lihuo, float duize) {
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

    public Color calculateColor() {
        if (colorPrefer == null) {
            float v = drysky + sundae + kanshui + genshan + kundi + thunder + lihuo + duize + yin + yang;
            colorPrefer = Colors.mix(drysky / v, sundae / v, kanshui / v, genshan / v, kundi / v, thunder / v, lihuo / v, duize / v, yin / v, yang / v);
        }
        return colorPrefer;
    }
}

