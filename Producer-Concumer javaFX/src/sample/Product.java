package sample;

import java.util.Random;

public class Product {
    private int[] colorRGB = new int[3];
    private Random random = new Random();

    Product(){
         colorRGB[0] = random.nextInt(256);
         colorRGB[1] = random.nextInt(256);
         colorRGB[2] = random.nextInt(256);
    }

    Product(int r, int g, int b){
        colorRGB[0] = r;
        colorRGB[1] = g;
        colorRGB[2] = b;
    }

    public int[] getColor() {
        return colorRGB;
    }

    public int getR() {
        return colorRGB[0];
    }

    public int getG() {
        return colorRGB[1];
    }

    public int getB() {
        return colorRGB[2];
    }

}
