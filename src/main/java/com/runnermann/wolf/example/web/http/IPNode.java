package com.runnermann.wolf.example.web.http;

public class IPNode {

    // Number of times ip address has been hit
    private int num;
    // Time of first hit this session
    private long time;


    public IPNode() {
        this.num = 0;
        this.time = System.currentTimeMillis();
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
