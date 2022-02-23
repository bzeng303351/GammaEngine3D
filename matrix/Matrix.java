package matrix;

import object3D.Vector;

public class Matrix {

        public static double dotProduct(Vector v1, Vector v2) {
            return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
        }

        public static Vector crossProduct(Vector v1, Vector v2) {
            return new Vector(
                    v1.y * v2.z - v1.z * v2.y,
                    v1.z * v2.x - v1.x * v2.z,
                    v1.x * v2.y - v1.y * v2.x
            );
        }

        public static double[][] multiply(double[][] first, double[][] second) {
            double[][] ret = new double[first.length][second[0].length];
            for (int row = 0; row < first.length; row++)
                for (int col = 0; col < second[0].length; col++) {
                    double tempSum = 0;
                    for (int i = 0; i < first[0].length; i++)
                        tempSum += first[row][i] * second[i][col];
                    ret[row][col] = tempSum;
                }
            return ret;
        }

        public static Vector rotateX(Vector v, double rad) {
            double[][] m = {{1, 0, 0},
                    {0, Math.cos(rad), -1 * Math.sin(rad)},
                    {0, Math.sin(rad), Math.cos(rad)}};
            return new Vector(multiply(m, v.toArray()));
        }

        public static Vector rotateY(Vector v, double rad) {
            double[][] m = {{Math.cos(rad), 0, Math.sin(rad)},
                    {0, 1, 0},
                    {-1 * Math.sin(rad), 0, Math.cos(rad)}};
            return new Vector(multiply(m, v.toArray()));
        }

        public static Vector rotateZ(Vector v, double rad) {
            double[][] m = {{Math.cos(rad), -1 * Math.sin(rad), 0},
                    {Math.sin(rad), Math.cos(rad), 0},
                    {0, 0, 1}};
            return new Vector(multiply(m, v.toArray()));
        }
}
