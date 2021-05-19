package mfrf.magic_circle.magicutil.datastructure;

public class MagicNodePropertyMatrix8By8 extends DecimalMagicMatrixNByN {

    public MagicNodePropertyMatrix8By8(float[][] cr) {
        super(cr, 8, 8);
    }

    public MagicNodePropertyMatrix8By8() {
        super(8, 8);
    }

    public enum INDEX {
        STRENGTH(1, 1), RANGE(2, 2), DURATION(3, 3), EXECUTE_SPEED(6, 6), COOLDOWN(7, 7), EFFICIENT(8, 8),
        DRYSKY(1, 3), KUNDI(8, 6), LIHUO(6, 1), KANSHUI(1, 6), DUIZE(3, 1), GENSHAN(6, 8), SUNDAE(1, 6), THUNDER(8, 3),
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
