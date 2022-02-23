package object3D;

import display.Camera;

public class IntersectPoint {
    private final Camera camera;
    private final Vector rayFromCamera;
    private final Rectangle3D rectangle3D;
    private double rayScale;
    private boolean isExist;

    public IntersectPoint(Camera camera, Vector rayFromCamera, Rectangle3D rectangle3D) {
        this.camera = camera;
        this.rayFromCamera = rayFromCamera;
        this.rectangle3D = rectangle3D;
        rayScale = 0;
        isExist = false;
    }

    public double getDistance() {
        return rayFromCamera.mod * rayScale;
    }

    public int getRGB() {
        Vector rectangleOriginFromCamera = rectangle3D.origin.shiftOriginTo(camera.origin);
        Vector rayFromCameraToRectangle = rayFromCamera.scale(rayScale);
        Vector vectorInRectangle = rayFromCameraToRectangle.shiftOriginTo(rectangleOriginFromCamera);
        double x = vectorInRectangle.dot(rectangle3D.xHat);
        double y = vectorInRectangle.dot(rectangle3D.yHat);
        return rectangle3D.getRGB(x, y);
    }

    public double getRayScale() {
        return rayScale;
    }

    public void setRayScale(double rayScale) {
        this.rayScale = rayScale;
    }

    public boolean isExist() {
        return isExist;
    }

    public void setExist(boolean isExist) {
        this.isExist = isExist;
    }
}
