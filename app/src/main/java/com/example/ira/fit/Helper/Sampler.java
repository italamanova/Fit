package com.example.ira.fit.Helper;

public class Sampler {

    private static LowPassFilter fX = null;
    private static LowPassFilter fY = null;
    private static LowPassFilter fZ = null;

    public static void setXFilter(LowPassFilter f) {
        fX = f;
    }

    public static void setYFilter(LowPassFilter f) {
        fY = f;
    }

    public static void setZFilter(LowPassFilter f) {
        fZ = f;
    }

    //	private float filterCoefficient = 0.5f;
//	private float gravity = 0;
//	private boolean first = true;
    private String name;
    private int number;
    private long time;
    private float ax;
    private float ay;
    private float az;
    private float axf;
    private float ayf;
    private float azf;
    private Sampler prev = null;
    private Sampler next = null;

    public Sampler(float ax, float ay, float az, String name) {
//		this.number = number;
//		this.tim = tim;
        time = System.currentTimeMillis();
        this.ax = ax;
        this.ay = ay;
        this.az = az;
        this.name = name;
        if (fX != null)
            axf = fX.filter(ax);
        else
            axf = 0;
        if (fY != null)
            ayf = fY.filter(ay);
        else
            ayf = 0;
        if (fZ != null)
            azf = fZ.filter(az);
        else
            azf = 0;
//		gravity = 0;
//		first = true;
//		fX = new LowPassFilter();
//		fY = new LowPassFilter();
//		fZ = new LowPassFilter();
    }

    public Sampler(float ax, float ay, float az) {
        this(ax, ay, az, "");
    }

    public Sampler getPrev() {
        return prev;
    }

    public void setPrev(Sampler prev) {
        this.prev = prev;
    }

    public Sampler getNext() {
        return next;
    }

    public void setNext(Sampler next) {
        this.next = next;
    }

    public float getAx() {
        return ax;
    }

    public void setAx(float ax) {
        this.ax = ax;
    }

    public float getAy() {
        return ay;
    }

    public void setAy(float ay) {
        this.ay = ay;
    }

    public float getAz() {
        return az;
    }

    public void setAz(float az) {
        this.az = az;
    }

    public float getAxF() {
        return axf;
    }

    public float getAyF() {
        return ayf;
    }

    public float getAzF() {
        return azf;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String toString() {
//		return(name+"="+number+":"+time+":ax="+ax+":ay="+ay+":az="+az);
        return (name + "=" + number + ":" + time + ":axf=" + axf + ":ayf=" + ayf + ":azf=" + azf);
    }
}
