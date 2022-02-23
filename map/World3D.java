package map;

import object3D.Tile3D;
import object3D.Vector;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class World3D {
    public final List<Tile3D> world = new ArrayList<>();

    public World3D() throws IOException {
        for (double x = -100; x < 0; x += 100)
            for (double y = -100; y < 0; y += 100)
                for (double z = -100; z < 200; z += 100) {
                    Vector origin = new Vector(x, y, z);
                    Tile3D t = new Tile3D(origin, 50, 50, 50);
                    world.add(t);
                }
//        Vector origin = new Vector(-60, 100, 0);
//        Tile3D t = new Tile3D(origin, 50, 50, 50);
//        world.add(t);

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("/Users/zeng/IdeaProjects/GammaEngine3D/src/map/sampleImage2.jpg"));
        } catch (IOException ignored) {
            System.out.println("ioexception");
        }
        for (Tile3D tile : world)
            tile.setImages(img);
    }

}
