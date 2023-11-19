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
//                    sky = ImageIO.read(getClass().getResourceAsStream("/8da92e07cc15da40292b44541affe466da6cb843.jpeg"));
//            sky = ImageIO.read(getClass().getResourceAsStream("/360_F_406509204_zEAiqnsDuRbNiHAMKYCrgc96hzucEpb4.jpg"));
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
        threads = new Thread[8];

        threads[0] = new Thread(() -> {

            while (true) {
                if(shapeList != null && tostart) {
//                    render(0,112);
//                    render(0,112/2);
                    render(0,defaultScreenWidth/4);
//                    render(0,defaultScreenWidth);
                    d1 = true;
                }

            }

        });
        threads[1] = new Thread(() -> {

            while (true) {
                if(shapeList != null && tostart) {
//                    render(112,224);
//                    render(112/2,224/2);
                    render(defaultScreenWidth/4,2*defaultScreenWidth/4);
                    d2 = true;
                }

            }

        });
        threads[2] = new Thread(() -> {

            while (true) {
                if(shapeList != null && tostart) {
//                    render(224,336);
//                    render(224/2,336/2);
                    render(2*defaultScreenWidth/4,3*defaultScreenWidth/4);
                    d3 = true;
                }

            }

        });
        threads[3] = new Thread(() -> {

            while (true) {
                if(shapeList != null && tostart) {
//                    render(336,448);
//                    render(336/2,448/2);
                    render(3*defaultScreenWidth/4,4*defaultScreenWidth/4);
                    d4 = true;
                }

            }

        });
//        threads[4] = new Thread(() -> {
//
//            while (true) {
//                if(shapeList != null && tostart) {
////                    render(448,560);
//                    render(448/2,560/2);
//                    d4 = true;
//                }
//
//            }
//
//        });
//        threads[5] = new Thread(() -> {
//
//            while (true) {
//                if(shapeList != null && tostart) {
////                    render(560,672);
//                    render(560/2,672/2);
//                    d4 = true;
//                }
//
//            }
//
//        });
//        threads[6] = new Thread(() -> {
//
//            while (true) {
//                if(shapeList != null && tostart) {
////                    render(672,784);
//                    render(672/2,784/2);
//                    d4 = true;
//                }
//
//            }
//
//        });
//        threads[7] = new Thread(() -> {
//
//            while (true) {
//                if(shapeList != null && tostart) {
////                    render(784,900);
//                    render(784/2,900/2);
//                    d4 = true;
//                }
//
//            }
//
//        });



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
    public JFrame getFrame() {
        return frame;
    }
    public Component rayMarch(Ray ray) {

        double minDistance = 0;

//        System.out.println("m");

        Component closest = null;

        Vec3D pos = ray.getCopyOfPos();

        boolean stop = true;
        double temp;

        Vec3D d = ray.getCopyOfDirection();

        d.setY(d.y + 0.0001);

        double x,y,z;
        x = 0;
        y = 0;
        z = 0;

        Vec3D[] distances = new Vec3D[4];
        Arrays.fill(distances,null);

        while (stop) {

            minDistance = shapeList.get(0).getEstimatedDistance(pos);
            closest = shapeList.get(0);

            for(Component c : shapeList) {

                temp = c.getEstimatedDistance(pos);

                if(temp < minDistance) {
                    minDistance = temp;
                    closest = c;
                }

            }

            x = d.x * minDistance;
            y = d.y * minDistance;
            z = d.z * minDistance;


            pos.x = pos.x - x;
            pos.y = pos.y - y;
            pos.z = pos.z - z;

            distances[3] = distances[2];
            distances[2] = distances[1];
            distances[1] = distances[0];
            distances[0] = new Vec3D(pos);

//            System.out.println(d);

            stop = !((minDistance < 0.0002) || (minDistance > 100));
        }

//        pos.x = pos.x + x*5;
//        pos.y = pos.y + y*5;
//        pos.z = pos.z + z*5;

        if(minDistance > 100) {
            closest = null;
        }
        else {

            if(distances[3] != null) {
                closest.setHitPoint(distances[3]);
            }
            if(distances[2] != null) {
                closest.setHitPoint(distances[2]);
//                System.out.println("it");
            }
            else if(distances[1] != null) {
                distances[1].setX(distances[1].x + x);
                distances[1].setY(distances[1].y + y);
                distances[1].setZ(distances[1].z + z);
                closest.setHitPoint(new Vec3D(distances[1]));
//                System.out.println("it2");

            }
            else if(distances[0] != null) {
                closest.setHitPoint(new Vec3D(distances[0]));

            }

            closest.setNormalHitPoint(new Vec3D(pos));
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

                        points[0] = new Vec3D(temp.getNormalHitPoint());

                        //temp = rayMarch(((i+1)/ (double)colorArray.length)*fov - fov/2.0 + 0.1, (((j+1)/ (double)colorArray[i].length)*fov*colorArray[i].length/colorArray.length - fov*colorArray[i].length/colorArray.length/2.0),cameraPos);

                        temp = rayMarch(new Ray(cameraPos,new Vec3D(Math.sin(Math.toRadians(cameraRotation.y + ((i+1)/ (double)colorArray.length)*fov - fov/2.0 + 0.1)),Math.sin(Math.toRadians(cameraRotation.x +(((j+1)/ (double)colorArray[i].length)*fov*colorArray[i].length/colorArray.length - fov*colorArray[i].length/colorArray.length/2.0))),Math.cos(Math.toRadians(((i+1)/ (double)colorArray.length)*fov - fov/2.0)))));


                        if(temp != null) {
                            points[1] = new Vec3D(temp.getNormalHitPoint());

                            //temp = rayMarch(((i+1)/ (double)colorArray.length)*fov - fov/2.0, ((j+1)/ (double)colorArray[i].length)*fov*colorArray[i].length/colorArray.length - fov*colorArray[i].length/colorArray.length/2.0 + 0.1,cameraPos);

                            temp = rayMarch(new Ray(cameraPos,new Vec3D(Math.sin(Math.toRadians(cameraRotation.y + ((i+1)/ (double)colorArray.length)*fov - fov/2.0)),Math.sin(Math.toRadians(cameraRotation.x + (((j+1)/ (double)colorArray[i].length)*fov*colorArray[i].length/colorArray.length - fov*colorArray[i].length/colorArray.length/2.0 + 0.1))),Math.cos(Math.toRadians(((i+1)/ (double)colorArray.length)*fov - fov/2.0)))));

                            if(temp != null) {

                                //System.out.println("got here");
                                points[2] = new Vec3D(temp.getNormalHitPoint());

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

                                if(hit2 == null) {

//                                    System.out.println("sky");

                                    int u = (int)((0.5 + Math.atan2(ref.getDirection().z,ref.getDirection().x)/(2.0*Math.PI))*sky.getWidth());
                                    int v = (int)(((0.5 + Math.asin(ref.getDirection().y)/Math.PI))*sky.getHeight());

//                                System.out.println("(" + u + "," + v + ")");

                                    colorArray[i][j] = new Color((int)(temp.getColor().getRed()*temp.getReflection() + (1 - temp.getReflection())*((sky.getRGB(u,v) >> 16) & 0xFF)),(int)(temp.getColor().getGreen()*temp.getReflection() + (1 - temp.getReflection())*((sky.getRGB(u,v) >> 8) & 0xFF)),(int)(temp.getColor().getBlue()*temp.getReflection() + (1 - temp.getReflection())*((sky.getRGB(u,v)) & 0xFF)));

                                }
                                else {
                                    //colorArray[i][j] = temp.getColor();

                                    Component secHit = rayMarch(ref);

                                    if(secHit != null) {
                                        points[0] = secHit.getNormalHitPoint();
                                    }

                                    Ray r1 = new Ray(ref);
                                    r1.getDirection().setY(ref.getDirection().y + 0.1);

                                    Ray r2 = new Ray(ref);
                                    r2.getDirection().setX(ref.getDirection().x + 0.1);



                                    Component secHit2 = rayMarch(r1);

                                    if(secHit2 != null) {
                                        points[1] = secHit2.getNormalHitPoint();
                                    }

                                    Component secHit3 = rayMarch(r2);

                                    if(secHit3 != null) {
                                        points[2] = secHit3.getNormalHitPoint();
                                    }

                                    if(secHit != null && secHit2 != null && secHit3 != null) {



//                                        points[0] = secHit.getNormalHitPoint();
//                                        points[1] = secHit2.getNormalHitPoint();
//                                        points[2] = secHit3.getNormalHitPoint();

                                        points[0].setX(points[0].x + 0.01);
                                        points[1].setY(points[1].y + 0.01);
                                        points[2].setZ(points[2].z + 0.01);

//                                        points[0].setY(points[0].y - 0.01);
//                                        points[1].setZ(points[1].z - 0.01);
//                                        points[2].setX(points[2].x - 0.01);
//
//                                        points[0].setZ(points[0].z - 0.02);
//                                        points[1].setX(points[1].x - 0.02);
//                                        points[2].setY(points[2].y - 0.02);


                                        tempp[0] = (points[0].y - points[1].y) * (points[0].z - points[2].z) - (points[0].z - points[1].z) * (points[0].y - points[2].y);
                                        tempp[1] = (points[0].z - points[1].z) * (points[0].x - points[2].x) - (points[0].x - points[1].x) * (points[0].z - points[2].z);
                                        tempp[2] = (points[0].x - points[1].x) * (points[0].y - points[2].y) - (points[0].y - points[1].y) * (points[0].x - points[2].x);

//                                        System.out.println(points[2].z);
//                                        System.out.println(tempp[1]);
//                                        System.out.println(tempp[2]);
//
//                                        System.out.println(ref);
//                                        System.out.println(r1);
//                                        System.out.println(r2);
//
//                                        System.out.println(points[0]);
//                                        System.out.println(points[1]);
//                                        System.out.println(points[2]);

                                        l = Math.sqrt(tempp[0]*tempp[0] + tempp[1]*tempp[1] + tempp[2]*tempp[2]);
//                                        System.out.println(" l = " + l);

                                        tempp[0] /= l;
                                        tempp[1] /= l;
                                        tempp[2] /= l;



                                        dotP = tempp[0]*ref.getDirection().x + tempp[1]*ref.getDirection().y + tempp[2]*ref.getDirection().z;

                                        rx = ref.getDirection().x - 2*dotP*tempp[0];
                                        ry = ref.getDirection().y - 2*dotP*tempp[1];
                                        rz = ref.getDirection().z - 2*dotP*tempp[2];



                                        Ray ref2 = new Ray(secHit.getHitPoint(),new Vec3D(rx,ry,rz));


                                        Component hit3 = rayMarch(ref2);



                                        if(hit3 == null || true) {
                                            int u = (int)((0.5 + Math.atan2(ref2.getDirection().z,ref2.getDirection().x)/(2.0*Math.PI))*sky.getWidth());
                                            int v = (int)(((0.5 + Math.asin(ref2.getDirection().y)/Math.PI))*sky.getHeight());

                                            double red = temp.getReflection()*temp.getColor().getRed() + secHit.getReflection()*secHit.getColor().getRed() + ((temp.getReflection() + secHit2.getReflection() > 1)? 0 : ((1-temp.getReflection() - secHit2.getReflection())*((sky.getRGB(u,v) >> 16) & 0xFF)));
                                            double green = temp.getReflection()*temp.getColor().getGreen() + secHit.getReflection()*secHit.getColor().getGreen() + ((temp.getReflection() + secHit2.getReflection() > 1)? 0 : ((1-temp.getReflection() - secHit2.getReflection())*((sky.getRGB(u,v) >> 8) & 0xFF)));
                                            double blue = temp.getReflection()*temp.getColor().getBlue() + secHit.getReflection()*secHit.getColor().getBlue() + ((temp.getReflection() + secHit2.getReflection() > 1)? 0 : ((1-temp.getReflection() - secHit2.getReflection())*((sky.getRGB(u,v)) & 0xFF)));

                                            if(red > 255) {
                                                red = 255;
                                            }
                                            if(green > 255){
                                                green = 255;
                                            }
                                            if(blue > 255) {
                                                blue = 255;
                                            }
                                            colorArray[i][j] = new Color((int)red,(int)green,(int)blue);
//                                            System.out.println(colorArray[i][j]);

                                            //colorArray[i][j] = new Color((int)(temp.getColor().getRed()*temp.getReflection() + (1 - temp.getReflection())*((sky.getRGB(u,v) >> 16) & 0xFF)),(int)(temp.getColor().getGreen()*temp.getReflection() + (1 - temp.getReflection())*((sky.getRGB(u,v) >> 8) & 0xFF)),(int)(temp.getColor().getBlue()*temp.getReflection() + (1 - temp.getReflection())*((sky.getRGB(u,v)) & 0xFF)));
                                        }
                                        else {
//                                            System.out.println("NULL");

                                            int u = (int)((0.5 + Math.atan2(ref2.getDirection().z,ref2.getDirection().x)/(2.0*Math.PI))*sky.getWidth());
                                            int v = (int)(((0.5 + Math.asin(ref2.getDirection().y)/Math.PI))*sky.getHeight());

                                            double red = temp.getReflection()*temp.getColor().getRed() + secHit.getReflection()*secHit.getColor().getRed() + ((temp.getReflection() + secHit2.getReflection() > 1)? 0 : ((1-temp.getReflection() - secHit2.getReflection())*(hit3.getColor().getRed())));
                                            double green = temp.getReflection()*temp.getColor().getGreen() + secHit.getReflection()*secHit.getColor().getGreen() + ((temp.getReflection() + secHit2.getReflection() > 1)? 0 : ((1-temp.getReflection() - secHit2.getReflection())*((hit3.getColor().getGreen()))));
                                            double blue = temp.getReflection()*temp.getColor().getBlue() + secHit.getReflection()*secHit.getColor().getBlue() + ((temp.getReflection() + secHit2.getReflection() > 1)? 0 : ((1-temp.getReflection() - secHit2.getReflection())*((hit3.getColor().getBlue()))));

                                            if(red > 255) {
                                                red = 255;
                                            }
                                            if(green > 255){
                                                green = 255;
                                            }
                                            if(blue > 255) {
                                                blue = 255;
                                            }
                                            colorArray[i][j] = new Color((int)red,(int)green,(int)blue);

                                        }

                                    }
                                    else {

                                        int u = (int)((0.5 + Math.atan2(ref.getDirection().z,ref.getDirection().x)/(2.0*Math.PI))*sky.getWidth());
                                        int v = (int)(((0.5 + Math.asin(ref.getDirection().y)/Math.PI))*sky.getHeight());

                                        colorArray[i][j] = new Color((int)(temp.getColor().getRed()*temp.getReflection() + (1 - temp.getReflection())*((sky.getRGB(u,v) >> 16) & 0xFF)),(int)(temp.getColor().getGreen()*temp.getReflection() + (1 - temp.getReflection())*((sky.getRGB(u,v) >> 8) & 0xFF)),(int)(temp.getColor().getBlue()*temp.getReflection() + (1 - temp.getReflection())*((sky.getRGB(u,v)) & 0xFF)));
                                    }

//                                    colorArray[i][j] = Color.BLACK;
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
//            threads[4].start();
//            threads[5].start();
//            threads[6].start();
//            threads[7].start();

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

        graphicsThing.setColor(Color.BLACK);
        graphicsThing.drawString("W,A,S,D - for movement",0,10);
        graphicsThing.drawString("F,G - for moving the sphere",0,20);

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
