package com.example.ira.fit.Accel;

/**
 * Created by Ira on 06.11.2016.
 */

public class LinearAcceleration {
    final static int MY_CONST = 6;
    private float x;
    private float y;
    private float z;

    private float lastX = 0;
    private float lastY = 0;
    private float lastZ = 0;

    private float curX = 0;
    private float curY = 0;
    private float curZ = 0;


    public LinearAcceleration(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;

    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }


    @Override
    public String toString() {
        return "LinearAcceleration{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    public void uppdateValues(){
        lastX = curX;
        lastY = curY;
        lastZ = curZ;

        curX = x;
        curY = y;
        curZ = z;
    }

    public float getCurrentAcceleration(){
        return (float) (Math.sqrt((double) (x * x + y * y + z * z)));
    }

    public boolean checkLie() {
        boolean b = false;
        if (Math.abs(lastX - curX) > MY_CONST) {
            b = true;
        }
        if (Math.abs(lastY - curY) > MY_CONST) {
            b = true;
        }
        if (Math.abs(lastZ - curZ) > MY_CONST) {
            b = true;
        }
        return b;
    }

    public float getLastX() {
        return lastX;
    }

    public void setLastX(float lastX) {
        this.lastX = lastX;
    }

    public float getLastY() {
        return lastY;
    }

    public void setLastY(float lastY) {
        this.lastY = lastY;
    }

    public float getLastZ() {
        return lastZ;
    }

    public void setLastZ(float lastZ) {
        this.lastZ = lastZ;
    }

    public float getCurX() {
        return curX;
    }

    public void setCurX(float curX) {
        this.curX = curX;
    }

    public float getCurY() {
        return curY;
    }

    public void setCurY(float curY) {
        this.curY = curY;
    }

    public float getCurZ() {
        return curZ;
    }

    public void setCurZ(float curZ) {
        this.curZ = curZ;
    }
}
