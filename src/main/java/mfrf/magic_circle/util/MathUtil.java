package mfrf.magic_circle.util;

import mfrf.magic_circle.magicutil.Receiver;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3i;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

public class MathUtil {
    public static final Matrix4f IDENTITY_MATRIX = Matrix4f.createScaleMatrix(1, 1, 1);

    public static HashSet<Vector3i> calculateZone(Receiver.RangeType rangeType, float u, float v, float w, float g) {
        HashSet<Vector3i> vector3is = new HashSet<>();
        int x = Math.round(u);
        int y = Math.round(v);
        int z = Math.round(w);
        int r = Math.round(g);
        switch (rangeType) {
            case CIRCLE: {
                for (int xOffset = -r; xOffset <= r; xOffset++) {
                    for (int zOffset = -r; zOffset <= r; zOffset++) {
                        if (Math.pow(xOffset, 2) + Math.pow(zOffset, 2) <= Math.pow(r, 2)) {
                            vector3is.add(new Vector3i(xOffset, 0, zOffset));
                        }
                    }
                }
                break;
            }
            case BOX: {
                ArrayList<Vector3i> temp = new ArrayList<>();
                for (int xOffset = -x; xOffset <= x; xOffset++) {
                    for (int zOffset = -z; zOffset <= z; zOffset++) {
                        temp.add(new Vector3i(xOffset, 0, zOffset));
                    }
                }

                for (int i = -y; i < y; i++) {
                    int finalI = i;
                    vector3is.addAll(temp.stream().map(vector3i -> vector3i.above(finalI)).collect(Collectors.toList()));
                }
                break;
            }
            case BALL: {
                for (int xOffset = -r; xOffset <= r; xOffset++) {
                    for (int zOffset = -r; zOffset <= r; zOffset++) {
                        for (int yOffset = -r; yOffset <= r; yOffset++) {
                            if (Math.pow(xOffset, 2) + Math.pow(zOffset, 2) + Math.pow(yOffset, 2) <= Math.pow(r, 2)) {
                                vector3is.add(new Vector3i(xOffset, yOffset, zOffset));
                            }
                        }
                    }
                }
                break;
            }
            case CYLINDER: {
                ArrayList<Vector3i> temp = new ArrayList<>();
                for (int xOffset = -r; xOffset <= r; xOffset++) {
                    for (int zOffset = -r; zOffset <= r; zOffset++) {
                        if (Math.pow(xOffset, 2) + Math.pow(zOffset, 2) <= Math.pow(r, 2)) {
                            temp.add(new Vector3i(xOffset, 0, zOffset));
                        }
                    }
                }
                for (int i = -y; i < y; i++) {
                    int finalI = i;
                    vector3is.addAll(temp.stream().map(vector3i -> vector3i.above(finalI)).collect(Collectors.toList()));
                }
                break;
            }
        }
        return vector3is;
    }

}
