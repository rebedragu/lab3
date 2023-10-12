package ex1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

class Parabola {
    private int a;
    private int b;
    private int c;
    public double vfX;
    public double vfY;

    public Parabola(int a, int b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;

        this.vfX = -b / (2.0 * a);
        this.vfY = -calculateDelta() / (4.0 * a);
    }

    private double calculateDelta() {
        return b * b - 4 * a * c;
    }

    @Override
    public String toString() {
        return "f(x) = " + a + "x^2 + " + b + "x + " + c;
    }

    public static double[] midpoint(Parabola p1, Parabola p2) {
        double x1 = p1.a;
        double y1 = p1.b;
        double x2 = p2.a;
        double y2 = p2.b;

        double mx = (x1 + x2) / 2;
        double my = (y1 + y2) / 2;

        return new double[]{mx, my};
    }

    public static double segmentLength(Parabola p1, Parabola p2) {
        double[] midpoint = midpoint(p1, p2);
        double x1 = p1.a;
        double y1 = p1.b;
        double x2 = p2.a;
        double y2 = p2.b;

        double length = Math.hypot(midpoint[0] - x1, midpoint[1] - y1) +
                Math.hypot(midpoint[0] - x2, midpoint[1] - y2);

        return length;
    }
}



public class MainApp {
    public static void main(String[] args) {
        List<Parabola> parabolas = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/ex1/in.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] coefficients = line.split(" ");
                if (coefficients.length == 3) {
                    int a = Integer.parseInt(coefficients[0]);
                    int b = Integer.parseInt(coefficients[1]);
                    int c = Integer.parseInt(coefficients[2]);
                    Parabola parabola = new Parabola(a, b, c);
                    parabolas.add(parabola);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Parabola parabola : parabolas) {
            System.out.println(parabola);
            System.out.println("Vârful parabolei: (" + parabola.vfX + ", " + parabola.vfY + ")");
        }



        for (int i = 0; i < parabolas.size() - 1; i++) {
            Parabola p1 = parabolas.get(i);
            Parabola p2 = parabolas.get(i + 1);

            double[] midpoint = Parabola.midpoint(p1, p2);
            double length = Parabola.segmentLength(p1, p2);

            System.out.println("Mijlocul segmentului dintre parabola " + (i + 1) + " și parabola " + (i + 2) + ": (" + midpoint[0] + ", " + midpoint[1] + ")");
            System.out.println("Lungimea segmentului dintre parabola " + (i + 1) + " și parabola " + (i + 2) + ": " + length);
        }
    }
}