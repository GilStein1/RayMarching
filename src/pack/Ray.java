package pack;

public class Ray {
    private Vec3D pos;
    private Vec3D d;

    public Ray(Vec3D pos, Vec3D d) {
        this.pos = pos;
        this.d = d;
        d.normalize();
    }
    public Ray(double x, double y, double z, double xd, double yd, double zd) {
        pos = new Vec3D(x,y,z);
        d = new Vec3D(xd, yd, zd);
        d.normalize();
    }
    public Vec3D getPos() {
        return pos;
    }
    public Vec3D getCopyOfPos() {
        return new Vec3D(pos);
    }
    public Vec3D getDirection() {
        return d;
    }
    public Vec3D getCopyOfDirection() {
        return new Vec3D(d);
    }
    public String toString() {
        return "pos = " + pos + "direction = " + d;
    }



}
