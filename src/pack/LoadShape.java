package pack;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class LoadShape implements Component{

    private java.util.List<Vec3D> pointList = new java.util.ArrayList<>();
    private Color c;
    Window w;
    Vec3D pos;
    Vec3D hit;
    double reflection = 0.7;
    double mergeParameter;
    Component shape;

    public LoadShape(Vec3D pos, Color color, double mergeParameter, double reflection, String path) {

        w = Window.getInstance();
        this.pos = pos;
        this.reflection = reflection;
        c = color;

        shape = null;

        this.mergeParameter = mergeParameter;

        InputStream inputStream = LoadShape.class.getResourceAsStream(path);

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            ArrayList<Vec3D> currentPolygonVertices = new ArrayList<>();
            int count = 0;
            while ((line = br.readLine()) != null) {
                // Parse the line and extract the relevant data
                if (line.startsWith("v ")) {

                    String[] parts = line.split("\\s+");
                    float x = (float) (Float.parseFloat(parts[1])*10 + pos.x);
                    float y = (float) (Float.parseFloat(parts[2])*10 + pos.y);
                    float z = (float) (Float.parseFloat(parts[3])*10 + pos.z);
                    // Process vertex data
                    if(x == 0f) {
                        x = 0.01f;
                    }
                    if(y == 0f) {
                        y = 0.01f;
                    }
                    if(z == 0f) {
                        z = 0.01f;
                    }

                    pointList.add(new Vec3D(x,y,z));


                } else if (line.startsWith("vn ")) {

                } else if (line.startsWith("f ")) {

                    String[] parts = line.split("\\s+");
                    currentPolygonVertices.clear();

                    // Start from index 1 since the first element is "f"
                    for (int i = 1; i < parts.length; i++) {
                        String[] vertexData = parts[i].split("/");

                        // Parse the vertex index (the first part)
                        int vertexIndex = Integer.parseInt(vertexData[0]) - 1;
                        currentPolygonVertices.add(pointList.get(vertexIndex));
                    }

                    Vec3D[] temp = new Vec3D[currentPolygonVertices.size()];
                    for(int i = 0; i < currentPolygonVertices.size(); i++) {
                        temp[i] = currentPolygonVertices.get(i);
                    }

                    // Create a new Polygon and add it to the ArrayList

                    if(shape == null) {
                        shape = new Triangle(temp[0],temp[1],temp[2],color,false);
                    }
                    else  {
                        shape = new Shape(shape,new Triangle(temp[0],temp[1],temp[2],color,false),color,mergeParameter,reflection);
                    }

//                    facelist.add(new Face(this,temp,c));
                    // Process face data
                } else if (line.startsWith("vt ")) {
                    // Process texture coordinate data (optional)

                    String[] parts = line.split("\\s+");

                    //System.out.println(parts[0]);

                    //pointList.get(count).textureCoords = new double[]{((Double.parseDouble(parts[1]) + 1.0)/2.0)*image.getWidth(),((Double.parseDouble(parts[2]) + 1.0)/2.0)*image.getHeight()};



                    //int c = image.getRGB((int)(Double.parseDouble(parts[0])*image.getWidth()),(int)(Double.parseDouble(parts[1])*image.getWidth()));

                    //colors.add(new Color((c >> 16) & 0xFF,c >> 8 & 0xFF,c & 0xFF));

                    count++;

                }
                else if (line.startsWith("mtllib ")) {
                    // Process material library (optional)
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        w.add(this);

    }
    public double smoothMin(double d1, double d2, double k) {

        double h = Math.max(k - Math.abs(d1 - d2),0)/k;

        return Math.min(d1,d2) - h*h*h*k*1/6.0;

    }

    @Override
    public double getEstimatedDistance(Vec3D point) {
        //return Math.min(coms[0].getEstimatedDistance(point), 1* coms[1].getEstimatedDistance(point));
        //return (smoothMin(coms[0].getEstimatedDistance(point),coms[1].getEstimatedDistance(point),mergeParameter));
        return shape.getEstimatedDistance(point);
    }

    @Override
    public Color getColor() {
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
    public void remove() {
        w.remove(this);
    }

    @Override
    public void setPosition(Vec3D pos) {

    }

    @Override
    public boolean isShape() {
        return false;
    }

    @Override
    public double getReflection() {
        return reflection;
    }
}
