package pack;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Main {
    public static void main(String[] args) {

        Window w = Window.getInstance();

        int x = 3^2;

        System.out.println(x);

        //Mandlebulb m = new Mandlebulb(new Vec3D(0,0,-40),Color.WHITE);


//
//        Triangle t = new Triangle(new Vec3D(-3,20,-41),new Vec3D(10,22,-40),new Vec3D(7,10,-35),Color.WHITE,false);
//
//        Shape S = new Shape(s2,s,Color.WHITE,10);
//        Shape S = new Shape(s2,s,Color.WHITE,10);

//        Cube s2 = new Cube(10,0,-40,new Vec3D(7,7,7),Color.WHITE,false,0.1);

        Plane p = new Plane(-10,0.3);

//        Torus s2 = new Torus(10,0,-40,0,0,0,new Vec2D(10,4), Color.BLUE,false,0.1);
//        Torus s3 = new Torus(40,0,-40,0,0,0,new Vec2D(10,4), Color.WHITE,false,0.01);
        Sphere s = new Sphere(-3,0,-40, 10, Color.RED,false,0.3);
//        Shape S = new Shape(s2,s,Color.WHITE,10,0.01);

//        LoadShape ls = new LoadShape(new Vec3D(-3,0,-41),Color.WHITE,0,0.01,"src//ImageToStl.com_suzanne_blender_monkey.obj");

        System.out.println("start");

        w.getFrame().addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_G) {
                    s.pos.setX(s.pos.x - 1);
                }
                if (keyCode == KeyEvent.VK_F) {
                    s.pos.setX(s.pos.x + 1);
                }
            }
        });

        w.setCameraPos(new Vec3D(0,0,0));

        w.render();

        while (true) {
            w.update();
        }


//        for(int i = 0; i < 10000; i++) {
//            System.out.println(" ");
//        }
//
//        w.update();
//        w.update();
//        w.update();



//        while (count < 3000) {
//
//            s.setPosition(new Vec3D(Math.sin(Math.toRadians(count))*20 - 10,10,-40));
//            w.update();
//            w.waitInSeconds(0.05);
//            count++;
//
//        }

        //Sphere light = new Sphere(-20,-20,-60,5,Color.YELLOW,true);


    }
}