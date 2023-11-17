package pack;

import java.awt.*;

public class Sphere implements Component{

    double Radius;
    Vec3D pos;
    Color c;
    Window w;
    boolean isLightSource;
    Vec3D hit;

    public Sphere(Vec3D position, double radius, Color color, boolean emitsLight) {
        pos = position;
        Radius = radius;
        c = color;
        isLightSource = emitsLight;
        setParams();
    }
    public Sphere(double x, double y, double z, double radius, Color color, boolean emitsLight) {
        pos = new Vec3D(x,y,z);
        Radius = radius;
        c = color;
        isLightSource = emitsLight;
        setParams();
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
    public Color getColor() {
        return c;
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

    public String toString() {
        return pos + ", Radius = " + Radius + "Color = " + c + ", is a light source = " + isLightSource;
    }

}
