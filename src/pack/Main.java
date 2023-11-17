package pack;

import java.awt.*;

public class Main {
    public static void main(String[] args) {

        Window w = Window.getInstance();

        int x = 3^2;

        System.out.println(x);

        //Mandlebulb m = new Mandlebulb(new Vec3D(0,0,-40),Color.WHITE);

        Sphere s = new Sphere(-3,20,-40, 10, Color.WHITE,false);

        Torus s2 = new Torus(10,20,-40,0,0,0,new Vec2D(10,4), Color.BLUE,false);

        Shape S = new Shape(s2,s,Color.WHITE,10);
        System.out.println("start");

        w.setCameraPos(new Vec3D(0,10,0));

//        for(int i = 0; i < 10000; i++) {
//            System.out.println(" ");
//        }
//
//        w.update();
//        w.update();
//        w.update();

        int count = 0;

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