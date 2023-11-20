package pack;

public class HitPair {

    public Vec3D hit = null;
    public Vec3D nHit = null;
    public Component c = null;
    public HitPair(Component c, Vec3D hit,Vec3D nHit) {
        this.c = c;
        this.hit = hit;
        this.nHit = nHit;
    }
    public Component getC() {
        return c;
    }

}
