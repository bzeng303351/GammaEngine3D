package object3D;

import matrix.Matrix;

public class Vector {
    public final double x;
    public final double y;
    public final double z;
    public final double mod;

    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.mod = Math.sqrt(x * x + y * y + z * z);
    }

    public Vector(double[] m) {
        this(m[0], m[1], m[2]);
    }

    public Vector(double[][] m) {
        this(m[0][0], m[1][0], m[2][0]);
    }

    public Vector shiftOriginTo(Vector newOrigin) {
        return new Vector(x - newOrigin.x, y - newOrigin.y, z - newOrigin.z);
    }

    public Vector add(Vector that) {
        return new Vector(x + that.x, y + that.y, z + that.z);
    }

    public Vector scale(double s) {
        return new Vector(x * s, y * s, z * s);
    }

    public Vector reverse() {
        return new Vector(-x, -y, -z);
    }

    public double dot(Vector that) {
        return Matrix.dotProduct(this, that);
    }

    public Vector cross(Vector that) {
        return Matrix.crossProduct(this, that);
    }

    public Vector unit() {
        return new Vector(x / mod, y / mod, z / mod);
    }

    public double[][] toArray() {
        double[][] ret = new double[3][1];
        ret[0][0] = x;
        ret[1][0] = y;
        ret[2][0] = z;
        return ret;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }

    @Override
    public boolean equals(Object that) {
        Vector v = (Vector) that;
        return x == v.x && y == v.y && z == v.z;
    }
}
