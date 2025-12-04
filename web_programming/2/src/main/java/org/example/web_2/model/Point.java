package org.example.web_2.model;

public class Point {
    public Integer index;
    public Double x;
    public Double y;
    public Double r;
    public boolean res;
    public String time;

    public Point(int index, double x, double y, double r, boolean res, String time) {
        this.index = index;
        this.x = x;
        this.y = y;
        this.r = r;
        this.res = res;
        this.time = time;
    }

    public String getX() {
        return Double.toString(x);
    }

    public String getY() {
        return Double.toString(y);
    }

    public String getR() {
        return Double.toString(r);
    }

    public String getRes() {
        return (res) ? "hit" : "miss";
    }

    public String getStrRes() {
        return (res) ? "Попадание" : "Промах";
    }

    public String getTime() {
        return time;
    }
}

