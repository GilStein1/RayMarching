package pack;

public class Vec3D {

    public double x,y,z;

    public Vec3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Vec3D(Vec3D vec) {
        this.x = vec.x;
        this.y = vec.y;
        this.z = vec.z;
    }
    public void normalize() {
        double l = Math.sqrt(x*x + y*y + z*z);
        x /= l;
        y /= l;
        z /= l;
    }
    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }
    public void setZ(double z) {
        this.z = z;
    }
    public void setCoords(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public String toString() {
        return "(" + x + "," + y + "," + z + ")";
    }

}
