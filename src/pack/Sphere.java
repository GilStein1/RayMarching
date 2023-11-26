package pack;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Sphere implements Component{

    double Radius;
    Vec3D pos;
    Color c;
    Window w;
    boolean isLightSource;
    Vec3D hit;
    Vec3D nHit;
    double reflection;
    BufferedImage texture;

    public Sphere(Vec3D position, double radius, Color color, boolean emitsLight) {
        pos = position;
        Radius = radius;
        c = color;
        isLightSource = emitsLight;
        setParams();
    }
    public Sphere(double x, double y, double z, double radius, Color color, boolean emitsLight, double reflection) {
        pos = new Vec3D(x,y,z);
        this.reflection = reflection;
        Radius = radius;
        c = color;
        isLightSource = emitsLight;
        setParams();
        try {
            texture = ImageIO.read(getClass().getResourceAsStream("/01-3.jpg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void setParams() {
        w = Window.getInstance();
        w.add(this);
    }

    @Override
    public double getEstimatedDistance(Vec3D point) {

        //System.out.println("(" + pos.x + "," + pos.y + "," + pos.z + ") " + point);

//        if(point != null) {
//            System.out.println("(" + pos.x + "," + pos.y + "," + pos.z + ") " + point);
//        }

        return Math.sqrt((point.x - pos.x)*(point.x - pos.x) + (point.y - pos.y)*(point.y - pos.y) + (point.z - pos.z)*(point.z - pos.z)) - Radius;
    }

    @Override
    public Color getColor(Vec3D point) {

        double x = -point.x - -pos.x;
        double y = -point.y - -pos.y;
        double z = -point.z - -pos.z;

        double l = Math.sqrt(x*x + y*y + z*z);

        x /= l;
        y /= l;
        z /= l;

        int u = (int)((0.5 + Math.atan2(z,x)/(2.0*Math.PI))*texture.getWidth());
        int v = (int)(((0.5 + Math.asin(y)/Math.PI))*texture.getHeight());

        return new Color((texture.getRGB(u,v) >> 16) & 0xFF,(texture.getRGB(u,v) >> 8) & 0xFF,(texture.getRGB(u,v)) & 0xFF);
    }

    @Override
    public boolean isALightSource() {
        return isLightSource;
    }

    @Override
    public Vec3D getPosition() {
        return pos;
    }

    @Override
    public Vec3D getHitPoint() {
        return hit;
    }
    @Override
    public void setNormalHitPoint(Vec3D point) {
        this.nHit = point;
    }
    @Override
    public Vec3D getNormalHitPoint() {
        return nHit;
    }

    @Override
    public void setHitPoint(Vec3D point) {
        hit = point;
    }

    @Override
    public void remove() {
        w.remove(this);
    }

    @Override
    public void setPosition(Vec3D pos) {
        this.pos = pos;
    }

    @Override
    public boolean isShape() {
        return false;
    }

    @Override
    public double getReflection() {
        return reflection;
    }

    public String toString() {
        return pos + ", Radius = " + Radius + "Color = " + c + ", is a light source = " + isLightSource;
    }

}
