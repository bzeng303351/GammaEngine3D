package object3D;

import display.Camera;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Tile3D {
    public final Vector origin;
    public final int xLength;
    public final int yLength;
    public final int zLength;
    private final Vector xHat;
    private final Vector yHat;
    private final Vector zHat;
    private final Vector v000;
    private final Vector v001;
    private final Vector v010;
    private final Vector v011;
    private final Vector v100;
    private final Vector v101;
    private final Vector v110;
    private final Vector v111;
    private final Rectangle3D top;
    private final Rectangle3D bottom;
    private final Rectangle3D front;
    private final Rectangle3D back;
    private final Rectangle3D left;
    private final Rectangle3D right;
    private final List<Rectangle3D> planes;

    public Tile3D(Vector origin, int xLength, int yLength, int zLength) {
        this.origin = origin;
        this.xLength = xLength;
        this.yLength = yLength;
        this.zLength = zLength;
        xHat = new Vector(1.0, 0, 0);
        yHat = new Vector(0, 1.0, 0);
        zHat = new Vector(0, 0, 1.0);
        v000 = getVertex(0, 0, 0);
        v001 = getVertex(0, 0, 1);
        v010 = getVertex(0, 1, 0);
        v011 = getVertex(0, 1, 1);
        v100 = getVertex(1, 0, 0);
        v101 = getVertex(1, 0, 1);
        v110 = getVertex(1, 1, 0);
        v111 = getVertex(1, 1, 1);
        top = new Rectangle3D(v011, xHat, yHat.reverse(), xLength, yLength);
        bottom = new Rectangle3D(v000, xHat, yHat, xLength, yLength);
        front = new Rectangle3D(v001, xHat, zHat.reverse(), xLength, zLength);
        back = new Rectangle3D(v111, xHat.reverse(), zHat.reverse(), xLength, zLength);
        left = new Rectangle3D(v011, yHat.reverse(), zHat.reverse(), yLength, zLength);
        right = new Rectangle3D(v101, yHat, zHat.reverse(), yLength, zLength);
        planes = new ArrayList<>();
        planes.add(top);
        planes.add(bottom);
        planes.add(front);
        planes.add(back);
        planes.add(left);
        planes.add(right);
    }

    private Vector getVertex(double i, double j, double k) {
        return origin.add(xHat.scale(i * xLength))
                .add(yHat.scale(j * yLength))
                .add(zHat.scale(k * zLength));
    }

    public void setImages(List<BufferedImage> images) {
        for (int i = 0; i < 6; i++) {
            if (i >= images.size())
                planes.get(i).setImage(images.get(images.size() - 1));
            else
                planes.get(i).setImage(images.get(i));
        }
    }

    public void setImages(BufferedImage image) {
        for (Rectangle3D r : planes)
            r.setImage(image);
    }

    public void setImages(int rgb) {
        for (Rectangle3D r : planes)
            r.setImage(rgb);
    }

    public IntersectPoint getIntersectPoint(Camera camera, Vector rayFromCamera) {
        IntersectPoint ret = new IntersectPoint(camera, rayFromCamera, top);
        for (Rectangle3D rect : planes) {
            IntersectPoint ip = rect.getIntersectPoint(camera, rayFromCamera);
            if (ip.isExist()) {
                return ip;
            }
        }
        return ret;
    }

}
