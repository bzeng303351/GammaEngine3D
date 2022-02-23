package object3D;

import display.Camera;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Rectangle3D {

    public Vector origin;
    public Vector xHat;
    public double xLength;
    public Vector yHat;
    public double yLength;
    private Vector normal;
    // I want to use right-hand axis
    // top left corner is origin
    // to the right is x, to the bottom is y.
    // into the screen is z.
    // a b c d in clockwise
    private Vector a;
    private Vector b;
    private Vector c;
    private Vector d;

    private BufferedImage image;

    public Rectangle3D(Vector origin, Vector xHat, Vector yHat, double xLength, double yLength) {
        this.origin = origin;
        this.xHat = xHat;
        this.xLength = xLength;
        this.yHat = yHat;
        this.yLength = yLength;
        this.normal = xHat.cross(yHat);
        a = origin;
        b = origin.add(xHat.scale(xLength));
        c = b.add(yHat.scale(yLength));
        d = origin.add(yHat.scale(yLength));
        image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        setImage(Color.BLUE.getRGB());
    }

    public void setImage(int rgb) {
        for (int x = 0; x < image.getWidth(); x++)
            for (int y = 0; y < image.getHeight(); y++)
                image.setRGB(x, y, rgb);
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    private boolean rayInRectangle(Camera camera, Vector rayFromCamera) {
        Vector newA = a.shiftOriginTo(camera.origin);
        Vector newB = b.shiftOriginTo(camera.origin);
        Vector newC = c.shiftOriginTo(camera.origin);
        Vector newD = d.shiftOriginTo(camera.origin);
        if (onOrAbovePlane(newA, newB, rayFromCamera)
                && onOrAbovePlane(newB, newC, rayFromCamera)
                && onOrAbovePlane(newC, newD, rayFromCamera)
                && onOrAbovePlane(newD, newA, rayFromCamera)) {
// note, the origin of the rectangle is in the absolute coords
            // when we judge from the camera view, we should use camera's origin
            // so we need to shift the starting point of rectangle's origin to camera's origin
            // otherwise, there will be a bug.
            // some plane cannot be seen forever, no matter where the camera is.
            return origin.shiftOriginTo(camera.origin).dot(normal) >= 0;
        }
        else
            return false;
    }

    public IntersectPoint getIntersectPoint(Camera camera, Vector rayFromCamera) {
        IntersectPoint ret = new IntersectPoint(camera, rayFromCamera, this);
        if (!rayInRectangle(camera, rayFromCamera)) {
            ret.setExist(false);
//            System.out.println(a);
//            System.out.println(b);
//            System.out.println(c);
//            System.out.println(d);
//            System.out.println(camera.origin);
//            System.out.println(rayFromCamera);
//            System.out.println("not in rectangle");
            return ret;
        }
        double nominator = origin.shiftOriginTo(camera.origin).dot(this.normal);
        double denominator = rayFromCamera.dot(this.normal);
        if (denominator == 0 || nominator == 0) {
            // the ray is parallel to the plane, or the camera normal is parallel to the plane
            // intersect point does not exist in this case.
            ret.setExist(false);
        }
        else {
            double rayScale = nominator / denominator;
            if (rayScale > 0) {
                // this is the only case that intersect point exist:
                // ray is in the rectangle, and rectangle is in front of the ray.
                ret.setExist(true);
                ret.setRayScale(rayScale);
            }
            else {
                // with the algebra above, there could be two cases for intersecting:
                // plane is in front of the camera, or on the back of the camera
                // if rayScale < 0, the plane is on the back of the camera
                // so intersect point does not exist.
                ret.setExist(false);
            }
        }
        return ret;
    }

    private boolean onOrAbovePlane(Vector x, Vector y, Vector that) {
//        System.out.print("on or above plane: ");
//        System.out.println(x.cross(y).dot(that) > 0);
        return x.cross(y).dot(that) > 0;
    }

    public int getRGB(double x, double y) {
        int imageX = mapDoubleToInteger(0, image.getWidth() - 1, 0, xLength, x);
        int imageY = mapDoubleToInteger(0, image.getHeight() - 1, 0, yLength, y);
        return image.getRGB(imageX, imageY);
    }

    private int mapDoubleToInteger(int iMin, int iMax, double dMin, double dMax, double d) {
        return (int) ((iMax - iMin) * (d - dMin) / (dMax - dMin) + iMin);
    }

    public static void main(String[] args) {
        BufferedImage bi = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
        Vector cameraOrigin = new Vector(200, 0, 0);
        Camera camera = new Camera(120, bi);
        camera.origin = cameraOrigin;
        Vector origin = new Vector(170, 100, 50);
        Vector xHat = new Vector(0, 1, 0);
        Vector yHat = new Vector(0, 0, -1);
        Rectangle3D r = new Rectangle3D(origin, xHat, yHat, 50, 50);
        Vector ray = new Vector(-0.25, 0.8660254037844387, 0.25);
        IntersectPoint ip = r.getIntersectPoint(camera, ray);
        System.out.print("intersect point exist: ");
        System.out.println(ip.isExist());
    }

}