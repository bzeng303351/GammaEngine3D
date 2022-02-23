package display;

import map.World3D;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Demo extends Frame implements KeyListener {

    public BufferedImage bi;
    public Camera camera;
    public World3D world;
    public double rotateStep = 0.1;
    public int moveStep = 10;

    public Demo(int width, int height, Camera camera, World3D world, BufferedImage image) {
        bi = image;
        this.camera = camera;
        this.world = world;
        this.camera.render(world);
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(bi, 0, 50, this);
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_U) {
            camera.moveUp(moveStep);
            camera.render(world);
            repaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            camera.moveDown(moveStep);
            camera.render(world);
            repaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_L) {
            camera.moveLeft(moveStep);
            camera.render(world);
            repaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_R) {
            camera.moveRight(moveStep);
            camera.render(world);
            repaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_F) {
            camera.moveForward(moveStep);
            camera.render(world);
            repaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_B) {
            camera.moveBackward(moveStep);
            camera.render(world);
            repaint();
        }
    }
    public static void main(String[] args) throws IOException {
        int width = 400;
        int height = 400;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Camera camera = new Camera(120, image);
        World3D world = new World3D();
        Demo demo = new Demo(width, height, camera, world, image);
        demo.setSize(width, height + 50);
        demo.addKeyListener(demo);
        demo.setVisible(true);

    }
}
