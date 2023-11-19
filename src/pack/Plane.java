package pack;

import java.awt.*;

public class Plane implements Component{

    double pos;
    Color c;
    Window w;
    boolean isLightSource;
    Vec3D hit;
    Vec3D nHit;
    double reflection;

    public Plane(double y, double reflection) {
        pos = y;
        this.reflection = reflection;
        isLightSource = false;
        setParams();
    }

    private void setParams() {
        w = Window.getInstance();
        w.add(this);
    }

    @Override
    public double getEstimatedDistance(Vec3D point) {

        return Math.abs(point.y - pos);
    }

    @Override
    public Color getColor() {
        int a = ((Math.abs((int)hit.x/5)%2 == 0)? 1 : -1);
        int b = ((Math.abs((int)hit.z/5)%2 == 0)? 1 : -1);
        return  (a*b > 0)? Color.GRAY : Color.WHITE;
    }

    @Override
    public boolean isALightSource() {
        return isLightSource;
    }

    @Override
    public Vec3D getPosition() {
        return new Vec3D(0,pos,0);
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
        this.pos = pos.y;
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
        return "y = " + pos + " reflection = " + reflection;
    }

}
