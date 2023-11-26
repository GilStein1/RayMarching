package pack;

import java.awt.*;

public class Shape implements Component{

    private Component[] coms;
    private Color c;
    Window w;
    Vec3D pos;
    Vec3D hit;
    Vec3D nHit;
    double reflection = 0.7;
    double mergeParameter;

    public Shape(Component com1, Component com2, Color color,double mergeParameter,double reflection) {

        w = Window.getInstance();
        this.reflection = reflection;
        c = color;
        coms = new Component[]{com1,com2};

        this.mergeParameter = mergeParameter;

        w.remove(com1);
        w.remove(com2);

        w.add(this);

    }
    public static double smoothMin(double d1, double d2, double k) {

        double h = Math.max(k - Math.abs(d1 - d2),0)/k;

        return Math.min(d1,d2) - h*h*h*k*1/6.0;

    }

    @Override
    public double getEstimatedDistance(Vec3D point) {
        //return Math.min(coms[0].getEstimatedDistance(point), 1* coms[1].getEstimatedDistance(point));
        if(mergeParameter == 0) {
            return Math.min(coms[0].getEstimatedDistance(point), 1* coms[1].getEstimatedDistance(point));
        }

        return (smoothMin(coms[0].getEstimatedDistance(point),coms[1].getEstimatedDistance(point),mergeParameter));
//        return Math.max(-coms[0].getEstimatedDistance(point),coms[1].getEstimatedDistance(point));
    }

    @Override
    public Color getColor(Vec3D point) {
        return c;
    }

    @Override
    public boolean isALightSource() {
        return false;
    }

    @Override
    public Vec3D getPosition() {
        return null;
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
    public void setNormalHitPoint(Vec3D point) {
        this.nHit = point;
    }
    @Override
    public Vec3D getNormalHitPoint() {
        return nHit;
    }

    @Override
    public void remove() {
        w.remove(this);
    }

    @Override
    public void setPosition(Vec3D pos) {

    }

    @Override
    public boolean isShape() {
        return true;
    }

    @Override
    public double getReflection() {
        return reflection;
    }
}
