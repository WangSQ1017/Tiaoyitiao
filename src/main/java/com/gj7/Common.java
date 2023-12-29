package com.gj7;

public class Common {
    public int xStart = 100;
    public int yStart = 100;

    public int getxStart() {
        return xStart;
    }

    public void setxStart(int xStart) {
        this.xStart = xStart;
    }

    public int getyStart() {
        return yStart;
    }

    public void setyStart(int yStart) {
        this.yStart = yStart;
    }

    private static Common instance = new Common();

    public static Common getInstance(){
        return instance;
    }


}
