package org.example;

public class Row {
    public float x;
    public float y;
    public float r;
    public boolean result;
    public long duration;
    public String time;

    public Row(float x, float y, float r, boolean result, long duration, String time) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.result = result;
        this.duration = duration;
        this.time = time;
    }
}
