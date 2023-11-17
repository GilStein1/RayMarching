package pack;

public class Vec2D {
    public double x,y;
    public Vec2D(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public Vec2D(Vec2D vec) {
        this.x = vec.x;
        this.y = vec.y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setCoords(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
