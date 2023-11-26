package pack;

import java.awt.*;

public interface Component {

    public double getEstimatedDistance(Vec3D point);

    public Color getColor(Vec3D point);

    public boolean isALightSource();

    public Vec3D getPosition();

    public Vec3D getHitPoint();

    public void setHitPoint(Vec3D point);
    public void setNormalHitPoint(Vec3D point);
    public Vec3D getNormalHitPoint();
    public void remove();
    public void setPosition(Vec3D pos);
    public boolean isShape();

    double getReflection();
}
