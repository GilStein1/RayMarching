package pack;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;

public class Window extends JPanel {

    private static Window instance;
    private JFrame frame;
    private BufferedImage screen;
    private java.util.List<Component> shapeList;
    private Vec3D cameraPos;
    private Vec3D cameraRotation;
    private final int defaultScreenWidth = 900;
    private final int defaultScreenHeight = 600;
    private final String defaultTitle = "RayMarching";
    private Color backgroundColor;
    private double fov;
    private Color[][] colorArray;
    private boolean constructed = false;
    private Component lightSource;
    private Vec3D light;
    private Thread[] threads;
    private BufferedImage sky;
    boolean tostart = false;

    boolean d1 = true;
    boolean d2 = true;
    boolean d3 = true;
    boolean d4 = true;

    private Window() {



        try {
            sky = ImageIO.read(getClass().getResourceAsStream("/sky sphere.jpg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        shapeList = new java.util.ArrayList<>();
        cameraPos = new Vec3D(0,0,0);
        cameraRotation = new Vec3D(0,0,0);
        frame = new JFrame(defaultTitle);
        frame.setSize(defaultScreenWidth,defaultScreenHeight);
        frame.setBackground(backgroundColor);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(this);
        fov = 90.1;
        colorArray = new Color[defaultScreenWidth][defaultScreenHeight];
        for(int i = 0; i < colorArray.length; i++) {
            for(int j = 0; j < colorArray[i].length; j++) {
                colorArray[i][j] = Color.BLACK;
            }
        }
        constructed = true;
        light = new Vec3D(1,0,0);
        threads = new Thread[4];

        threads[0] = new Thread(() -> {

            while (true) {
                if(shapeList != null && tostart) {
                    render(0,225);
                    d1 = true;
                }

            }

        });
        threads[1] = new Thread(() -> {

            while (true) {
                if(shapeList != null && tostart) {
                    render(225,450);
                    d2 = true;
                }

            }

        });
        threads[2] = new Thread(() -> {

            while (true) {
                if(shapeList != null && tostart) {
                    render(450,675);
                    d3 = true;
                }

            }

        });
        threads[3] = new Thread(() -> {

            while (true) {
                if(shapeList != null && tostart) {
                    render(675,900);
                    d4 = true;
                }

            }

        });



        frame.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_W) {
                    cameraPos.z -= 1;
                }
                if (keyCode == KeyEvent.VK_S) {
                    cameraPos.z += 1;
                }
                if (keyCode == KeyEvent.VK_D) {
                    cameraPos.x -= 1;
                }
                if (keyCode == KeyEvent.VK_A) {
                    cameraPos.x += 1;
                }
                if (keyCode == KeyEvent.VK_SPACE) {
                    cameraPos.y += 1;
                }
                if (keyCode == KeyEvent.VK_SHIFT) {
                    cameraPos.y -= 1;
                }
//                if (keyCode == KeyEvent.VK_LEFT) {
//                    cameraRotation.y += 1;
//                }
//                if (keyCode == KeyEvent.VK_RIGHT) {
//                    cameraRotation.y -= 1;
//                }

            }
        });


    }
    public static Window getInstance() {
        if(instance == null) {
            instance = new Window();
        }
        return instance;
    }
    public Component rayMarch(Ray ray) {

//        System.out.println(ray);

        double minDistance = 0;

        Component closest = null;

        Vec3D pos = ray.getCopyOfPos();

        boolean stop = true;
        double temp;

        Vec3D d = ray.getCopyOfDirection();

        double x,y,z;
        x = 0;
        y = 0;
        z = 0;

//        System.out.println("_______");

//        System.out.println(Math.toDegrees(Math.atan2(d.z,d.x)));
//        System.out.println(Math.toDegrees(Math.atan2(d.y,Math.sqrt(d.x*d.x + d.z*d.z))));
//        System.out.println(d.y);
//        if(d.z > 0.5) {
//            System.out.println(d.z);
//        }

        while (stop) {

//            System.out.println(pos);

            minDistance = shapeList.get(0).getEstimatedDistance(pos);
            closest = shapeList.get(0);

            for(Component c : shapeList) {

                temp = c.getEstimatedDistance(pos);

                if(temp < minDistance) {
                    minDistance = temp;
                    closest = c;
                }

            }


//            x = (xz < 0)? 1 : -1;

//            System.out.println(minDistance);


            x = d.x * minDistance;
            y = d.y * minDistance;
            z = d.z * minDistance;


            pos.x = pos.x - x;
            pos.y = pos.y - y;
            pos.z = pos.z - z;

            stop = !((minDistance < 0.0002) || (minDistance > 100));
        }

        if(minDistance > 100) {
            closest = null;
        }
        else {
            closest.setHitPoint(pos);
            //System.out.println(closest.getEstimatedDistance(pos));
            //System.out.println("(" + x + "," + y + "," + z + ")");
        }





        return closest;

    }
    public Component rayMarch(double xz, double xy, Vec3D position) {

//        xz = xz%360;
//        xy = xy%360;
//
//        xz = (xz > 180)? xz - 360 : xz;
//
//        xy = (xy > 180)? xy - 360 : xy;

        xz = Math.toRadians(xz + 0.01);
        xy = Math.toRadians(xy + 0.01);

        Vec3D pos = new Vec3D(position);
        //System.out.println(pos);

        boolean stop = true;
        double temp;

        double minDistance = 0;

        Component closest = null;

        double x,y,z;
        x = 0;
        y = 0;
        z = 0;

        while (stop) {
            //System.out.println("step");

//            if(pos != null) {
//                System.out.println(pos);
//            }

            minDistance = shapeList.get(0).getEstimatedDistance(pos);
            closest = shapeList.get(0);

            for(Component c : shapeList) {

                //minDistance = Math.min(minDistance,c.getEstimatedDistance(pos));

                temp = c.getEstimatedDistance(pos);

                if(temp < minDistance) {
                    minDistance = temp;
                    closest = c;
                }

            }
            //System.out.println("xz = " + xz + "xy = " + xy + "distance = " + minDistance);


            double a1;

            //double a2 = Math.sqrt((minDistance * minDistance * a1) /(1 + a1));


//            y = ((xy > 0)? (a2) : -(a2));
//
//            a1 = Math.cos(xz) * Math.cos(xz);
//
//            a2 = Math.sqrt((minDistance * minDistance * a1) / (1 + a1));
//
//            x = ((xz > 0)? (a2) : -(a2));
//
//            a1 = Math.sqrt(x*x + y*y);
//
//            z = a1*Math.sin(xz);

            x = (xz < 0)? 1 : -1;
            //z = (xz < 0)? (x/Math.tan(xz)) : -(x/Math.tan(xz));
            z = (x/Math.tan(xz));
            y = Math.tan(xy)*Math.sqrt(x*x + z*z);

            a1 = Math.sqrt(x*x + y*y + z*z);

            x /= a1;
            y /= a1;
            z /= a1;

            x *= minDistance;
            y *= minDistance;
            z *= minDistance;

            pos.x = pos.x + x;
            pos.y = pos.y + y;
            pos.z = pos.z + z;

            stop = !((minDistance < 0.02) || (minDistance > 600));
            //System.out.println(minDistance);

            //System.out.println(pos + ", " + Math.toDegrees(xz) + "," + Math.toDegrees(xy) + ", distance = " + minDistance);

//            System.out.println(minDistance*minDistance - pos.x*pos.x - pos.y*pos.y);
//
//            a2 = Math.sqrt(Math.abs(minDistance*minDistance - pos.x*pos.x - pos.y*pos.y));
//
//            pos.z = pos.z + ((xz > 0)? a2 : -a2);

        }

//        pos.x = pos.x - 1*x + Math.abs(x)*2/x;
//        pos.y = pos.y - 1*y + Math.abs(y)*2/y;
//        pos.z = pos.z - 1*z + Math.abs(z)*2/z;


        if(minDistance > 600) {
            closest = null;
        }
        else {
            closest.setHitPoint(pos);
            //System.out.println(closest.getEstimatedDistance(pos));
            //System.out.println("(" + x + "," + y + "," + z + ")");
        }

        return closest;
    }

    public void render(int imin, int imax) {

        Component temp;

        for(int i = imin; i < imax; i++) {
            for(int j = 0; j < colorArray[i].length; j++) {

                //System.out.println("(" + i + "," + j + ") ------");

                //System.out.println("----");
                //System.out.println("got here");


                Vec3D[] points = new Vec3D[3];

                if(shapeList != null && shapeList.size() != 0) {
                    //temp = rayMarch(((i+1)/ (double)colorArray.length)*fov - fov/2.0, ((j+1)/ (double)colorArray[i].length)*fov*colorArray[i].length/colorArray.length - fov*colorArray[i].length/colorArray.length/2.0,cameraPos);

                    Ray r = new Ray(cameraPos,new Vec3D(Math.sin(Math.toRadians(cameraRotation.y + ((i+1)/ (double)colorArray.length)*fov - fov/2.0)),Math.sin(Math.toRadians(cameraRotation.x +(((j+1)/ (double)colorArray[i].length)*fov*colorArray[i].length/colorArray.length - fov*colorArray[i].length/colorArray.length/2.0))),Math.cos(Math.toRadians(((i+1)/ (double)colorArray.length)*fov - fov/2.0))));

                    temp = rayMarch(new Ray(cameraPos,new Vec3D(Math.sin(Math.toRadians(cameraRotation.y + ((i+1)/ (double)colorArray.length)*fov - fov/2.0)),Math.sin(Math.toRadians(cameraRotation.x +(((j+1)/ (double)colorArray[i].length)*fov*colorArray[i].length/colorArray.length - fov*colorArray[i].length/colorArray.length/2.0))),Math.cos(Math.toRadians(((i+1)/ (double)colorArray.length)*fov - fov/2.0)))));

//                    if(temp != null) {
//                        colorArray[i][j] = temp.getColor();
//                    }
//                    else {
//                        colorArray[i][j] = Color.BLACK;
//                    }

//                    System.out.println(((i+1)/ (double)colorArray.length)*fov - fov/2.0);
                    //System.out.println(((((j+1)/ (double)colorArray[i].length)*fov*colorArray[i].length/colorArray.length - fov*colorArray[i].length/colorArray.length/2.0)));

                    if(temp != null) {

                        points[0] = new Vec3D(temp.getHitPoint());

                        //temp = rayMarch(((i+1)/ (double)colorArray.length)*fov - fov/2.0 + 0.1, (((j+1)/ (double)colorArray[i].length)*fov*colorArray[i].length/colorArray.length - fov*colorArray[i].length/colorArray.length/2.0),cameraPos);

                        temp = rayMarch(new Ray(cameraPos,new Vec3D(Math.sin(Math.toRadians(cameraRotation.y + ((i+1)/ (double)colorArray.length)*fov - fov/2.0 + 0.1)),Math.sin(Math.toRadians(cameraRotation.x +(((j+1)/ (double)colorArray[i].length)*fov*colorArray[i].length/colorArray.length - fov*colorArray[i].length/colorArray.length/2.0))),Math.cos(Math.toRadians(((i+1)/ (double)colorArray.length)*fov - fov/2.0)))));


                        if(temp != null) {
                            points[1] = new Vec3D(temp.getHitPoint());

                            //temp = rayMarch(((i+1)/ (double)colorArray.length)*fov - fov/2.0, ((j+1)/ (double)colorArray[i].length)*fov*colorArray[i].length/colorArray.length - fov*colorArray[i].length/colorArray.length/2.0 + 0.1,cameraPos);

                            temp = rayMarch(new Ray(cameraPos,new Vec3D(Math.sin(Math.toRadians(cameraRotation.y + ((i+1)/ (double)colorArray.length)*fov - fov/2.0)),Math.sin(Math.toRadians(cameraRotation.x + (((j+1)/ (double)colorArray[i].length)*fov*colorArray[i].length/colorArray.length - fov*colorArray[i].length/colorArray.length/2.0 + 0.1))),Math.cos(Math.toRadians(((i+1)/ (double)colorArray.length)*fov - fov/2.0)))));

                            if(temp != null) {

                                //System.out.println("got here");
                                points[2] = new Vec3D(temp.getHitPoint());

                                double[] tempp = new double[3];

                                tempp[0] = (points[0].y - points[1].y) * (points[0].z - points[2].z) - (points[0].z - points[1].z) * (points[0].y - points[2].y);
                                tempp[1] = (points[0].z - points[1].z) * (points[0].x - points[2].x) - (points[0].x - points[1].x) * (points[0].z - points[2].z);
                                tempp[2] = (points[0].x - points[1].x) * (points[0].y - points[2].y) - (points[0].y - points[1].y) * (points[0].x - points[2].x);

                                double l = Math.sqrt(tempp[0]*tempp[0] + tempp[1]*tempp[1] + tempp[2]*tempp[2]);

                                tempp[0] /= l;
                                tempp[1] /= l;
                                tempp[2] /= l;

                                double dotP = tempp[0]*r.getDirection().x + tempp[1]*r.getDirection().y + tempp[2]*r.getDirection().z;

                                double rx = r.getDirection().x - 2*dotP*tempp[0];
                                double ry = r.getDirection().y - 2*dotP*tempp[1];
                                double rz = r.getDirection().z - 2*dotP*tempp[2];

                                Ray ref = new Ray(temp.getHitPoint(),new Vec3D(rx,ry,rz));

                                Component hit2 = rayMarch(ref);

                                if(hit2 != null) {

//                                    System.out.println("sky");

                                    int u = (int)((0.5 + Math.atan2(ref.getDirection().z,ref.getDirection().x)/(2.0*Math.PI))*sky.getWidth());
                                    int v = (int)((0.5 + Math.asin(ref.getDirection().y)/Math.PI)*sky.getHeight());

//                                System.out.println("(" + u + "," + v + ")");

                                    colorArray[i][j] = new Color((int)(temp.getColor().getRed()*temp.getReflection() + (1 - temp.getReflection())*((sky.getRGB(u,v) >> 16) & 0xFF)),(int)(temp.getColor().getGreen()*temp.getReflection() + (1 - temp.getReflection())*((sky.getRGB(u,v) >> 8) & 0xFF)),(int)(temp.getColor().getBlue()*temp.getReflection() + (1 - temp.getReflection())*((sky.getRGB(u,v)) & 0xFF)));

                                }
                                else {
//                                    colorArray[i][j] = temp.getColor();
                                    colorArray[i][j] = Color.BLACK;
                                }

                                //double colorMultiplier = Math.abs(((light.x) * (tempp[0]) + light.y*tempp[1] + light.z*tempp[2] + 30)/60.0);

//                                double colorMultiplier = ((light.x) * (tempp[0]) + light.y*tempp[1] + light.z*tempp[2] + 1)/2.0;

                                //System.out.println((light.x) * (tempp[0]) + light.y*tempp[1] + light.z*tempp[2] < 0);

                                //System.out.println((light.x) * (tempp[0]) + light.y*tempp[1] + light.z*tempp[2] > 1);

                                //System.out.println(colorMultiplier);


                                //colorArray[i][j] = new Color((int)(temp.getColor().getRed()*colorMultiplier),(int)(temp.getColor().getGreen()*colorMultiplier),(int)(temp.getColor().getBlue()*colorMultiplier));

//                                colorArray[i][j] = new Color((int)(colorMultiplier*255),(int)(colorMultiplier*255),(int)(colorMultiplier*255));
//                                colorArray[i][j] = temp.getColor();
                            }
                            else {

                                int u = (int)((0.5 + Math.atan2(r.getDirection().z,r.getDirection().x)/(2.0*Math.PI))*sky.getWidth());
                                int v = (int)((0.5 + Math.asin(r.getDirection().y)/Math.PI)*sky.getHeight());

//                                System.out.println("(" + u + "," + v + ")");

                                colorArray[i][j] = new Color((sky.getRGB(u,v) >> 16) & 0xFF,(sky.getRGB(u,v) >> 8) & 0xFF,(sky.getRGB(u,v)) & 0xFF);
//                                System.out.println(colorArray[i][j]);
                            }


                        }
                        else {
                            int u = (int)((0.5 + Math.atan2(r.getDirection().z,r.getDirection().x)/(2.0*Math.PI))*sky.getWidth());
                            int v = (int)((0.5 + Math.asin(r.getDirection().y)/Math.PI)*sky.getHeight());

//                                System.out.println("(" + u + "," + v + ")");

                            colorArray[i][j] = new Color((sky.getRGB(u,v) >> 16) & 0xFF,(sky.getRGB(u,v) >> 8) & 0xFF,(sky.getRGB(u,v)) & 0xFF);
//                                System.out.println(colorArray[i][j]);
                        }


                    }
                    else {
                        int u = (int)((0.5 + Math.atan2(r.getDirection().z,r.getDirection().x)/(2.0*Math.PI))*sky.getWidth());
                        int v = (int)((0.5 + Math.asin(r.getDirection().y)/Math.PI)*sky.getHeight());

//                                System.out.println("(" + u + "," + v + ")");

                        colorArray[i][j] = new Color((sky.getRGB(u,v) >> 16) & 0xFF,(sky.getRGB(u,v) >> 8) & 0xFF,(sky.getRGB(u,v)) & 0xFF);
//                                System.out.println(colorArray[i][j]);
                    }
                }







//                if(temp != null) {
//                    colorArray[i][j] = (temp != null)? temp.getColor() : Color.BLACK;
//                    //System.out.println("a light source");
//                }
//                else if(temp != null){
//
////                    double xz =  Math.toDegrees(Math.atan2(lightSource.getPosition().z - temp.getHitPoint().z,lightSource.getPosition().x - temp.getHitPoint().x));
////                    //double xy = (Math.toDegrees(Math.atan2(lightSource.getPosition().y - temp.getHitPoint().y,Math.sqrt((lightSource.getPosition().x - temp.getHitPoint().x)*(lightSource.getPosition().x - temp.getHitPoint().x) + (lightSource.getPosition().z - temp.getHitPoint().z)*(lightSource.getPosition().z - temp.getHitPoint().z)))));
////
////                    double xy = Math.toDegrees(Math.atan2(lightSource.getPosition().y - temp.getHitPoint().y,lightSource.getPosition().x - temp.getHitPoint().x));
//
////                    double m = (lightSource.getPosition().y - temp.getHitPoint().y)/(lightSource.getPosition().x - temp.getHitPoint().x);
////                    double n = (lightSource.getPosition().z - temp.getHitPoint().z)/(lightSource.getPosition().x - temp.getHitPoint().x);
////
////                    double total = Math.sqrt(1 + m*m + n*n);
////                    m /= total;
////                    n /= total;
////
////                    double xz = ((lightSource.getPosition().x < temp.getHitPoint().x && lightSource.getPosition().z > temp.getHitPoint().z) || (lightSource.getPosition().x < temp.getHitPoint().x && lightSource.getPosition().z > temp.getHitPoint().z))? 180 + Math.toDegrees(Math.atan(n)) : Math.toDegrees(Math.atan(n));
////
////                    double xy = ((lightSource.getPosition().x < temp.getHitPoint().x && lightSource.getPosition().y > temp.getHitPoint().y) || (lightSource.getPosition().x < temp.getHitPoint().x && lightSource.getPosition().y > temp.getHitPoint().y))? 180 + Math.toDegrees(Math.atan(m)) : Math.toDegrees(Math.atan(m));
//
//
//
//                    double xz = Math.toDegrees(Math.atan((lightSource.getPosition().z - temp.getHitPoint().z) / (lightSource.getPosition().x - temp.getHitPoint().x)));
//
//                    if (lightSource.getPosition().x - temp.getHitPoint().x > 0 && lightSource.getPosition().z - temp.getHitPoint().z < 0) {
//                        xz = 180 + xz;
//                    }
//                    else if(lightSource.getPosition().x - temp.getHitPoint().x > 0 && lightSource.getPosition().z - temp.getHitPoint().z > 0) {
//                        xz = 180 + xz;
//                    }
//
//                    double r = Math.sqrt((lightSource.getPosition().z - temp.getHitPoint().z)*(lightSource.getPosition().z - temp.getHitPoint().z) + (lightSource.getPosition().x - temp.getHitPoint().x)*(lightSource.getPosition().x - temp.getHitPoint().x));
//
//                    double xy = Math.toDegrees(Math.atan(1/((r)/(lightSource.getPosition().y - temp.getHitPoint().y))));
//
//                    if ((lightSource.getPosition().y - temp.getHitPoint().y) > 0 && (lightSource.getPosition().z - temp.getHitPoint().z) < 0) {
//                        xy = 180 + xy;
//                    }
//                    else if((lightSource.getPosition().y - temp.getHitPoint().y) > 0 && (lightSource.getPosition().z - temp.getHitPoint().z) > 0) {
//                        xy = 180 + xy;
//                    }
//
////                    xy = -xy;
////                    xz = -xz;
//
//                    Component temp2 = rayMarch(xz,xy,temp.getHitPoint());
//
////                    if(xy < 0) {
////                        System.out.println("asdw");
////                    }
//
//                    //System.out.println("(" + xz + "," + xy + ") " + temp2);
//
////                    boolean a =
////
////                    System.out.println(a);
//
////                    if(temp2 == null) {
////                        System.out.println("null");
////                    }
////                    if(temp2.getColor() == Color.YELLOW) {
////                        System.out.println("yes");
////                    }
//
//
//                    if(temp2 != null && temp2.isALightSource()) {
//                        colorArray[i][j] = (temp != null)? temp.getColor() : Color.BLACK;
//                    }
//                    else if (temp2 != null && !temp2.isALightSource()){
//                        colorArray[i][j] = (temp != null)? new Color(temp.getColor().getRed()/2,temp.getColor().getGreen()/2,temp.getColor().getBlue()/2) : Color.BLACK;
//                    }
//                    else if(temp2 == null) {
//                        colorArray[i][j] = temp.getColor();
//                    }
//
//
//                }
//                else {
//                    colorArray[i][j] = Color.BLACK;
//                }

                //System.out.println("(" + i + "," + j + ")");

//                if(temp != null) {
//                    System.out.println("-NULL");
//                }

                //colorArray[i][j] = (temp != null)? temp.getColor() : Color.BLACK;

                //colorArray[i][j] = rayMarch(((i+1)/ colorArray.length)*fov, ((j+1)/ colorArray[i].length)*fov).getColor();
            }
        }

//        System.out.println("rendered");

    }
    public void render() {

        if(shapeList.size() > 0 && constructed) {

            threads[0].start();
            threads[1].start();
            threads[2].start();
            threads[3].start();

//            if(d1){
//                d1 = false;
//                threads[0].start();
//            }
//            if(d2){
//                d2 = false;
//                threads[1].start();
//            }
//            if(d3){
//                d3 = false;
//                threads[2].start();
//            }
//            if(d4){
//                d4 = false;
//                threads[3].start();
//            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (screen == null) {
            screen = new BufferedImage(defaultScreenWidth,defaultScreenHeight,BufferedImage.TYPE_INT_ARGB);
        }

        Graphics2D graphicsThing = screen.createGraphics();


//        if(shapeList.size() > 0 && constructed) {
//
//            if(!threads[0].isAlive()){
//                threads[0].start();
//            }
//            if(!threads[1].isAlive()){
//                threads[1].start();
//            }
//            if(!threads[2].isAlive()){
//                threads[2].start();
//            }
//            if(!threads[3].isAlive()){
//                threads[3].start();
//            }
//
//            //render();
//
////            for(Thread t : threads) {
////                t.run();
////            }
//        }

        //System.out.println("rendered");



        if(true) {
            for(int i = 0; i < colorArray.length; i++) {
                for(int j = 0; j < colorArray[i].length; j++) {
                    graphicsThing.setColor(colorArray[i][j]);
                    graphicsThing.fillRect(i,j,1,1);
                }
            }
        }

        graphicsThing.dispose();

        g.drawImage(screen,0,0, frame.getWidth(), frame.getHeight(),this);

    }
    public void update() {
        repaint();
        //System.out.println("updated");
    }

    public void add(Component component) {
        shapeList.add(component);
        tostart = true;
        //System.out.println(component.isALightSource());
        if(component.isALightSource()) {
            lightSource = component;
            //System.out.println("added a light source");
        }
    }
    public void waitInSeconds(double seconds) {
        try {
            long milliseconds = Math.round(seconds * 1000);
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {

        }
    }
    public void setCameraPos(Vec3D newPos) {
        cameraPos = new Vec3D(newPos);
    }
    public void setCameraRotation(Vec3D newRotation) {
        cameraRotation = new Vec3D(newRotation);
    }
    public void setBackgroundColor(Color color) {
        backgroundColor = color;
    }
    public void setTitle(String title) {
        frame.setTitle(title);
    }
    public void remove(Component c) {
        shapeList.remove(c);
    }



}
