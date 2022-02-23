package display;

import map.World3D;
import object3D.IntersectPoint;
import object3D.Rectangle3D;
import object3D.Tile3D;
import object3D.Vector;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Camera {
    // the parameter origin is in the absolute coords.
    public Vector origin;
    public Vector normal;
    // In the relative coords of camera, top left corner is origin
    // to the right is x, to the bottom is y.
    public Vector xHat;
    public Vector yHat;
    private double zDist;
    private final double width;
    private double height;
    private double viewAngleDeg;
    private BufferedImage image;

    public Camera(double viewAngleDeg, BufferedImage image) {
        this.viewAngleDeg = viewAngleDeg;
        this.image = image;
        width = 1.0;
        height = width * image.getHeight() / image.getWidth();
        zDist = width / 2 / Math.tan(Math.PI * viewAngleDeg / 2 / 360);
        origin = new Vector(0, 0, 0);
        normal = new Vector(0, 1.0, 0);
        xHat = new Vector(1.0, 0, 0);
        yHat = new Vector(0, 0, -1.0);
    }

    public void render(World3D world) {
        int width = image.getWidth();
        int height = image.getHeight();
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++) {
                double x = mapIntegerToDouble(-0.5 * this.width, 0.5 * this.width, 0, width, i);
                double y = mapIntegerToDouble(-0.5 * this.height, 0.5 * this.height, 0, height, j);
                Vector ray = normal.scale(zDist).add(xHat.scale(x)).add(yHat.scale(y));
//                if (i == 100 && j == 100) {
//                    System.out.println(ray);
//                    System.out.println(this.origin);
//                }
//                System.out.println(x + "," + y + "," + zDist);
//                System.out.println(ray);
                image.setRGB(i, j, getColor(this, ray, world));
            }
    }

    private int getColor(Camera camera, Vector ray, World3D world) {
        double minDist = Double.POSITIVE_INFINITY;
        int color = Color.GREEN.getRGB();
        for (Tile3D t : world.world) {
            IntersectPoint ip = t.getIntersectPoint(camera, ray);
            if (ip.isExist() && ip.getDistance() < minDist) {
                color = ip.getRGB();
                minDist = ip.getDistance();
//              System.out.println(minDist);
            }
        }
        return color;
    }

    private double mapIntegerToDouble(double dMin, double dMax, int iMin, int iMax, int i) {
        return (dMax - dMin) * (i - iMin) / (iMax - iMin) + dMin;
    }


    public void moveUp(double step) {
        origin = origin.add(yHat.scale(-1.0 * step));
    }

    public void moveDown(double step) {
        origin = origin.add(yHat.scale(step));
    }

    public void moveLeft(double step) {
        origin = origin.add(xHat.scale(-1.0 * step));
    }

    public void moveRight(double step) {
        origin = origin.add(xHat.scale(step));
    }

    public void moveForward(double step) {
        origin = origin.add(normal.scale(step));
    }

    public void moveBackward(double step) {
        origin = origin.add(normal.scale(-1.0 * step));
    }

    public static void main(String[] args) {
        BufferedImage bi = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        Camera camera = new Camera(90, bi);
        System.out.println(camera.mapIntegerToDouble(-0.5, 0.5, 100, 199, 199));
    }

}
