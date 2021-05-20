package mfrf.magic_circle.magicutil.datastructure;

import mfrf.magic_circle.magicutil.BGMPreferences;
import mfrf.magic_circle.magicutil.EightDiragramsPrefer;
import mfrf.magic_circle.magicutil.RGBA;

import java.awt.*;

public class MagicNodePropertyMatrix8By8 extends MagicStreamMatrixNByN {
    public static final MagicNodePropertyMatrix8By8 IDENTITY = new MagicNodePropertyMatrix8By8(new float[][]{
            {1, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 1},
    });

    public MagicNodePropertyMatrix8By8(float[][] cr) {
        super(cr, 8, 8);
    }

    public MagicNodePropertyMatrix8By8(double[] data, int cols, int rows) {
        super(data, cols, rows);
    }

    public MagicNodePropertyMatrix8By8() {
        super(8, 8);
    }

    public void set(INDEX index, float value) {
        set(index.i, index.j, value);
    }

    public static void initPauliElements(MagicNodePropertyMatrix8By8 magicNodePropertyMatrix8By8) {
        magicNodePropertyMatrix8By8.set(4, 4, 1);
        magicNodePropertyMatrix8By8.set(4, 5, 0);
        magicNodePropertyMatrix8By8.set(4, 6, 0);
        magicNodePropertyMatrix8By8.set(4, 7, 1);

        magicNodePropertyMatrix8By8.set(5, 4, 0);
        magicNodePropertyMatrix8By8.set(5, 5, -1);
        magicNodePropertyMatrix8By8.set(5, 6, 1);
        magicNodePropertyMatrix8By8.set(5, 7, 0);

        magicNodePropertyMatrix8By8.set(6, 4, 0);
        magicNodePropertyMatrix8By8.set(6, 5, 1);
        magicNodePropertyMatrix8By8.set(7, 4, 1);
        magicNodePropertyMatrix8By8.set(7, 5, 0);
    }

    public void setEightDiragramsPrefer(EightDiragramsPrefer prefer) {
        set(INDEX.DRYSKY, prefer.drysky());
        set(INDEX.KUNDI, prefer.kundi());
        set(INDEX.THUNDER, prefer.thunder());
        set(INDEX.LIHUO, prefer.lihuo());
        set(INDEX.KANSHUI, prefer.kanshui());
        set(INDEX.DUZE, prefer.duze());
        set(INDEX.GENSHAN, prefer.genshan());
        set(INDEX.SUNDAE, prefer.sundae());
    }

    public void setColors(RGBA rgba) {
        set(INDEX.RED, rgba.r());
        set(INDEX.GREEN, rgba.g());
        set(INDEX.BLUE, rgba.b());
        set(INDEX.ALPHA, rgba.a());
    }

    public void setBGMPreference(BGMPreferences bgmPreferences) {
        set(INDEX.FREQUENCY, bgmPreferences.frequency());
        set(INDEX.VALUE_RANGE, bgmPreferences.valueRange());
        set(INDEX.TIME_DOMAIN, bgmPreferences.timeDomain());
        set(INDEX.INTENSITY, bgmPreferences.intensity());
    }

    public MagicNodePropertyMatrix8By8 leftTimes(MagicNodePropertyMatrix8By8 magicNodePropertyMatrix8By8) {
        MagicNodePropertyMatrix8By8 ret = new MagicNodePropertyMatrix8By8();
        ret.setData(leftTimes(magicNodePropertyMatrix8By8).data);
        return ret;
    }

    public MagicNodePropertyMatrix8By8 copy() {
        return new MagicNodePropertyMatrix8By8(this.data, numRows, numCols);
    }

    public enum INDEX {
        STRENGTH(1, 1), RANGE(2, 2), DURATION(3, 3), EXECUTE_SPEED(6, 6), COOLDOWN(7, 7), EFFICIENT(8, 8),
        DRYSKY(1, 3), KUNDI(8, 6), LIHUO(6, 1), KANSHUI(1, 6), DUZE(3, 1), GENSHAN(6, 8), SUNDAE(1, 6), THUNDER(8, 3),
        RED(2, 3), GREEN(2, 4), BLUE(2, 5), ALPHA(2, 6),
        FREQUENCY(3, 2), VALUE_RANGE(4, 2), TIME_DOMAIN(5, 2), INTENSITY(6, 2),
        WEAKNESS(1, 8), SHRINK(2, 7), BREVITY(3, 6), RELAY(6, 3), HEATUP(7, 2), WASTE(1, 8);

        public final int i;
        public final int j;

        INDEX(int i, int j) {
            this.i = i - 1;
            this.j = j - 1;
        }
    }
}
