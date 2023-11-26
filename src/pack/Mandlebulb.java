package pack;

import java.awt.*;

public class Mandlebulb implements Component{

    Color c;
    Vec3D pos;
    Vec3D hit;
    Vec3D nHit;
    Window w;

    public Mandlebulb(Vec3D pos, Color color) {

        c = color;
        this.pos = pos;

        w = Window.getInstance();

        w.add(this);

    }


    @Override
    public double getEstimatedDistance(Vec3D point) {

        double x = point.x + 0.1;
        double y = point.y + 0.1;
        double z = point.z + 0.1;
        double MAX_ITERATIONS = 10;
        double BAILOUT = 16.0;
        double POWER = 8.0;

        double real = x;
        double imag = y;
        double comp = z;

        double dr = 1.0;
        double dz = 1.0;
        double r2 = 0;

        for (int i = 0; i < MAX_ITERATIONS; i++) {
            r2 = real * real + imag * imag + comp * comp;
            if (r2 > BAILOUT)
                break;

            double rpow = Math.pow(r2, POWER - 1.0);
            double theta = Math.atan2(Math.hypot(real, imag), comp) * POWER;
            double phi = Math.atan2(imag, real) * POWER;

            dr = rpow * POWER * dr + 1.0;
            double dTheta = rpow * Math.sin(theta) * POWER * dz;
            double dPhi = rpow * Math.sin(phi) * POWER * dz;

            real = x + Math.cos(phi) * Math.cos(theta) * r2;
            imag = y + Math.cos(phi) * Math.sin(theta) * r2;
            comp = z + Math.sin(phi) * r2;

            dz = dTheta + dPhi;

//            if(point.x >-1000) {
//                System.out.println(point);
//                System.out.println(r2);
//            }

            //System.out.println(i);
        }

        return 0.5 * Math.log(r2) * Math.sqrt(r2) / dr;

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
        this.pos = pos;
    }

    @Override
    public boolean isShape() {
        return false;
    }

    @Override
    public double getReflection() {
        return 0;
    }
}
