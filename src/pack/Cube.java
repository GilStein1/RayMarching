package pack;

import java.awt.*;

public class Cube implements Component{

    Vec3D dimensions;
    Vec3D pos;
    Color c;
    Window w;
    boolean isLightSource;
    Vec3D hit;
    double reflection = 0.7;

    public Cube(Vec3D position, Vec3D dimensions, Color color, boolean emitsLight,double reflection) {
        pos = position;
        this.reflection = reflection;
        this.dimensions = dimensions;
        c = color;
        isLightSource = emitsLight;
        setParams();
    }
    public Cube(double x, double y, double z, Vec3D dimensions, Color color, boolean emitsLight, double reflection) {
        pos = new Vec3D(x,y,z);
        this.reflection = reflection;
        this.dimensions = dimensions;
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

        Vec3D p = new Vec3D(point);

        p.x -= pos.x;
        p.y -= pos.y;
        p.z -= pos.z;

        Vec3D q = new Vec3D(0,0,0);
        q.x = Math.abs(p.x) - dimensions.x;
        q.y = Math.abs(p.y) - dimensions.y;
        q.z = Math.abs(p.z) - dimensions.z;

        return Math.sqrt(Math.max(q.x,0)*Math.max(q.x,0) + Math.max(q.y,0)*Math.max(q.y,0) + Math.max(q.z,0)*Math.max(q.z,0)) + Math.min(Math.max(q.x, Math.max(q.y, q.z)), 0.0f);
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

    @Override
    public boolean isShape() {
        return false;
    }

    @Override
    public double getReflection() {
        return reflection;
    }

    public String toString() {
        return pos + ", Dimensions = " + dimensions + "Color = " + c + ", is a light source = " + isLightSource;
    }

}
