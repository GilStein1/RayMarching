package pack;

import java.awt.*;

public class Triangle implements Component{


    Color c;
    Window w;
    boolean isLightSource;
    Vec3D hit;
    Vec3D nHit;
    Vec3D p1,p2,p3;

//    public Triangle(Vec3D position, double radius, Color color, boolean emitsLight) {
//        pos = position;
//        Radius = radius;
//        c = color;
//        isLightSource = emitsLight;
//        setParams();
//    }
    public Triangle(Vec3D p1, Vec3D p2, Vec3D p3, Color color, boolean emitsLight) {

        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;

        c = color;
        isLightSource = emitsLight;
        setParams();
    }
    private static float[] cross(float[] a, float[] b) {
        return new float[]{
                a[1] * b[2] - a[2] * b[1],
                a[2] * b[0] - a[0] * b[2],
                a[0] * b[1] - a[1] * b[0]
        };
    }
    private static float crossDot(float[] a, float[] b, float[] c) {
        return dot(cross(a, b), c);
    }

    private static float dot(float[] a, float[] b) {
        float result = 0;
        for (int i = 0; i < a.length; i++) {
            result += a[i] * b[i];
        }
        return result;
    }

    private static float dot2(float[] a) {
        return dot(a, a);
    }

    private static float[] subtract(float[] a, float[] b) {
        float[] result = new float[a.length];
        for (int i = 0; i < a.length; i++) {
            result[i] = a[i] - b[i];
        }
        return result;
    }

    private static float[] multiply(float[] a, float scalar) {
        float[] result = new float[a.length];
        for (int i = 0; i < a.length; i++) {
            result[i] = a[i] * scalar;
        }
        return result;
    }

    private static float clamp(float value, double min, double max) {
        return (float) Math.max(min, Math.min(value, max));
    }
    private void setParams() {
        w = Window.getInstance();
        w.add(this);
    }

    @Override
    public double getEstimatedDistance(Vec3D point) {

        float[] p = {(float) point.x, (float) point.y, (float) point.z};
        float[] a = {(float) p1.x, (float) p1.y, (float) p1.z};
        float[] b = {(float) p2.x, (float) p2.y, (float) p2.z};
        float[] c = {(float) p3.x, (float) p3.y, (float) p3.z};

        float[] ba = subtract(b, a);
        float[] pa = subtract(p, a);
        float[] cb = subtract(c, b);
        float[] pb = subtract(p, b);
        float[] ac = subtract(a, c);
        float[] pc = subtract(p, c);

        float[] nor = cross(ba, ac);

        return (float) Math.sqrt(
                (Math.signum(crossDot(ba, nor, pa)) +
                        Math.signum(crossDot(cb, nor, pb)) +
                        Math.signum(crossDot(ac, nor, pc)) < 2.0)
                        ?
                        Math.min(Math.min(
                                        dot2(subtract(multiply(ba, clamp(dot(ba, pa) / dot2(ba), 0.0, 1.0)), pa)),
                                        dot2(subtract(multiply(cb, clamp(dot(cb, pb) / dot2(cb), 0.0, 1.0)), pb))),
                                dot2(subtract(multiply(ac, clamp(dot(ac, pc) / dot2(ac), 0.0, 1.0)), pc)))

                        :
        dot(nor, pa) * dot(nor, pa) / dot2(nor)
        );

    }

    @Override
    public Color getColor(Vec3D point) {
        return c;
    }

    @Override
    public boolean isALightSource() {
        return isLightSource;
    }

    @Override
    public Vec3D getPosition() {
        return new Vec3D((p1.x + p2.x + p3.x)/3,(p1.y + p2.y + p3.y)/3,(p1.z + p2.z + p3.z)/3);
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
//        this.pos = pos;
    }

    @Override
    public boolean isShape() {
        return false;
    }

    @Override
    public double getReflection() {
        return 0;
    }

    public String toString() {
        return p1 + " " + p2 + " " + p3;
    }

}
