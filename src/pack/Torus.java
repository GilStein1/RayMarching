package pack;

import java.awt.*;

public class Torus implements Component{

    Vec2D dimensions;
    Vec3D pos;
    Color c;
    Window w;
    boolean isLightSource;
    Vec3D hit;
    Vec3D rotation;

    public Torus(Vec3D position, Vec3D rotation, Vec2D dimensions, Color color, boolean emitsLight) {
        pos = position;
        this.dimensions = dimensions;
        c = color;
        isLightSource = emitsLight;
        setParams();
        this.rotation = rotation;
    }
    public Torus(double x, double y, double z, double xRotation, double yRotation, double zRotation, Vec2D dimensions, Color color, boolean emitsLight) {
        pos = new Vec3D(x,y,z);
        this.dimensions = dimensions;
        c = color;
        isLightSource = emitsLight;
        setParams();
        rotation = new Vec3D(xRotation,yRotation,zRotation);
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

//        Vec3D cr = new Vec3D(0,0,0);
//
//        cr.x = Math.toRadians(Math.toDegrees(Math.atan2(p.z,p.y)) + rotation.x + 0.01);
//        cr.y = Math.toRadians(Math.toDegrees(Math.atan2(p.z,p.x)) + rotation.y + 0.01);
//        cr.z = Math.toRadians(Math.toDegrees(Math.atan2(p.y,p.x)) + rotation.z + 0.01);
//
//        double distance = Math.sqrt(p.z*p.z + p.y*p.y + p.x*p.x);
//
//        p.z = distance*Math.sin(cr.x);
//        p.y = distance*Math.cos(cr.x);
//
//        cr.x = Math.toRadians(Math.toDegrees(Math.atan2(p.z,p.y)) + rotation.x + 0.01);
//        cr.y = Math.toRadians(Math.toDegrees(Math.atan2(p.z,p.x)) + rotation.y + 0.01);
//        cr.z = Math.toRadians(Math.toDegrees(Math.atan2(p.y,p.x)) + rotation.z + 0.01);
//
//        distance = Math.sqrt(p.z*p.z + p.y*p.y + p.x*p.x);
//
//        p.z = distance*Math.sin(cr.y);
//        p.x = distance*Math.cos(cr.y);
//
//        cr.x = Math.toRadians(Math.toDegrees(Math.atan2(p.z,p.y)) + rotation.x + 0.01);
//        cr.y = Math.toRadians(Math.toDegrees(Math.atan2(p.z,p.x)) + rotation.y + 0.01);
//        cr.z = Math.toRadians(Math.toDegrees(Math.atan2(p.y,p.x)) + rotation.z + 0.01);
//
//        distance = Math.sqrt(p.z*p.z + p.y*p.y + p.x*p.x);
//
//        p.y = distance*Math.sin(cr.z);
//        p.x = distance*Math.cos(cr.z);

//        Vec2D q = new Vec2D(0,0);

        double lengthXZ = (double) Math.sqrt(p.x * p.x + p.z * p.z);
        double qX = lengthXZ - dimensions.x;
        double qY = p.y;
        double lengthQ = (double) Math.sqrt(qX * qX + qY * qY);
        return lengthQ - dimensions.y;

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
        return pos + ", Dimensions = " + dimensions + "Color = " + c + ", is a light source = " + isLightSource;
    }

}
