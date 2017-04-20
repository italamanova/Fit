package com.example.ira.fit.Motion;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

//Squatting calculator
public class MotionDetector
{
    // list of the motion types
    public static int UNKNOWN = 0;
    public static int xyzMIN_MAX = 1;
    public static int xyzMAX_MIN = 2; // froward-backward
    public static int absMIN_MAX_MIN = 3;
    public static int absMIN_MAX = 4;
    public static int absMAX_MIN = 5;
    public static int absMAX_MIN_MAX = 6;
    //=======================================
    private static float STANDARD_GRAVITY  = 9.80665f;
    //=======================================
    private static float absolute(float x, float y, float z)
    {
        return (float)Math.sqrt(x*x + y*y + z*z) - STANDARD_GRAVITY;
    }
    private static String pNumber(long number)
    {
        String s = "";
        if(number < 10) s+= "   ";
        else if(number < 100) s+= "  ";
        else if(number < 1000) s+= " ";
        return s + number + "|";
    }
    private static String dFloat(float f, char q, char m)
    {
        char tmpl[] = {'.','.','.','.','.','.','.','.','|','.','.','.','.','.','.','.','.'};
        int i;
        int n = (int)f;
        if(n < -8) tmpl[0] = m;
        else if(n < 0)
            tmpl[8 + n] = q;
        else if(n > 8) tmpl[16] = m;
        else tmpl[8 + n] = q;
        return new String(tmpl) + "|";
    }

    private static String xyzToString(long number, float a, float x, float y, float z)
    {
        return pNumber(number) + dFloat(a, 'a', 'A') + dFloat(x, 'x', 'X') + dFloat(y, 'y', 'Y') + dFloat(z, 'z', 'Z');
    }
    private static String at(int axis)
    {
        String at = "X";
        if(axis == 2) at = "Y";
        if(axis == 3) at = "Z";
        return at;
    }
    private static float actualMax(float m, ExtremeVal e)
    {
        float max = m;
        ExtremeVal ev = e;
        if(Math.abs(ev.getValue()) > max)
            max = Math.abs(ev.getValue());
        return max;
    }
    //=======================================
    private int type;            // motion type (see list of the motion types)
    private float epsilon;      //limit extreme value for calc extreme
    private ExtremeSumCalc eXCalc; // extreme sum calc for x axis acceleration
    private ExtremeSumCalc eYCalc; // extreme sum calc for y axis acceleration
    private ExtremeSumCalc eZCalc; // extreme sum calc for z axis acceleration
    private ExtremeCalc eACalc;    // extreme val calc for absolute acceleration
    private ExtremeVal firstX;  // first extreme sum for x axis acceleration
    private ExtremeVal firstY;  // first extreme sum for y axis acceleration
    private ExtremeVal firstZ;  // first extreme sum for z axis acceleration
    private ExtremeVal lastX;   // last extreme sum for x axis acceleration
    private ExtremeVal lastY;   // last extreme sum for y axis acceleration
    private ExtremeVal lastZ;   // last extreme sum for z axis acceleration
    private float maxX;         // max for sum value for x axis acceleration
    private float maxY;         // max for sum value for y axis acceleration
    private float maxZ;         // max for sum value for z axis acceleration
    private long count[];         // number of shake
    private float abc = 0.0f;   // absolute acceleration
    private float x;            // x axis acceleration
    private float y;            // y axis acceleration
    private float z;            // z axis acceleration
    private long number;        // sequence number
    private ExtremeVal firstA;  // first extreme val for absolute acceleration
    private ExtremeVal lastA;   // last extreme val for absolute acceleration
    public String fname = "";
    public String fnameExtr = "";
    //====================================
    public MotionDetector(int type, float epsilon)
    {
        this.type = type;
        this.epsilon = epsilon;
        eXCalc = new ExtremeSumCalc();
        eYCalc = new ExtremeSumCalc();
        eZCalc = new ExtremeSumCalc();
        eACalc = new ExtremeCalc();
        count = new long[4];
        for(int i = 0; i < 4; i++)
            count[i] = 0;
        number = 0;
        clear(0);
    }
    public void clear(int axis)
    {
        eXCalc.clear();
        eYCalc.clear();
        eZCalc.clear();
        eACalc.clear();
        firstX = null;
        firstY = null;
        firstZ = null;
        firstA = null;
        lastX = null;
        lastY = null;
        lastZ = null;
        lastA = null;
        if(axis == 1)
        {
            maxX = maxX/2;
            epsilon = maxX/5;
        }
        else
            maxX = 0;
        if(axis == 2)
        {
            maxY = maxY/2;
            epsilon = maxY/5;
        }
        else
            maxY = 0;
        if(axis == 3)
        {
            maxZ = maxZ/2;
            epsilon = maxZ/5;
        }
        else
            maxZ = 0;
    }
    public void add(float[] original, float[] filtered)
    {
        number++;
        abc = absolute(original[0], original[1], original[2]);
        x = filtered[0];
        y = filtered[1];
        z = filtered[2];
    }
    private int selectionAxis() //1 = x, 2 =y, 3 = z
    {
        if(count[1] > 0) return 1;
        if(count[2] > 0) return 2;
        if(count[3] > 0) return 3;
        if((maxX > maxY) && (maxX > maxZ)) return 1;
        if((maxY > maxX) && (maxY > maxZ)) return 2;
        if((maxZ > maxX) && (maxZ > maxY)) return 3;
        return 1;

    }
    private ExtremeVal calcPriority(int axis)
    {
        ExtremeVal priority = firstX;
        if(axis == 1) priority = firstX;
        if(axis == 2) priority = firstY;
        if(axis == 3) priority = firstZ;
        return priority;
    }
    private void calcXYZSumExtreme()
    {
        ExtremeVal ev = eXCalc.add(x, lastX);
        if(ev != null)
        {
            printGraph("add extremeX="+ev);
            if(firstX == null) firstX = ev;
            if(lastX != null)  lastX.setNext(ev);
            lastX = ev;
            maxX = actualMax(maxX,ev);
        }
        ev = eYCalc.add(y, lastY);
        if(ev != null)
        {
            printGraph("add extremeY="+ev);
            if(firstY == null) firstY = ev;
            if(lastY != null)  lastY.setNext(ev);
            lastY = ev;
            maxY = actualMax(maxY,ev);
        }
        ev = eZCalc.add(z, lastZ);
        if(ev != null)
        {
            printGraph("add extremeZ="+ev);
            if(firstZ == null) firstZ = ev;
            if(lastZ != null)  lastZ.setNext(ev);
            lastZ = ev;
            maxZ = actualMax(maxZ,ev);
        }

    }
    private void calcAExtreme()
    {
        ExtremeVal ev;
        ev = eACalc.add(abc, lastA);
        if(ev != null)
        {
            printGraph("!!! add extremeA="+ev);
            if(firstA == null) firstA = ev;
            if(lastA != null)  lastA.setNext(ev);
            lastA = ev;
        }
    }
    public boolean isShake()
    {
        boolean ok = false;
        if(type == xyzMIN_MAX)
        {
            calcXYZSumExtreme();
            int axis = selectionAxis();
            ExtremeVal priority = calcPriority(axis);
            ok = ExtremeVal.analyzeMIN_MAX(priority, epsilon);
            if(ok)
            {
                count[axis]++;
                printExtreme("!ok("+count[axis]+") " + at(axis) + ExtremeVal.chainToString(priority));
            }
            if(ok) clear(axis);
            return ok;
        }
        if(type == xyzMAX_MIN)
        {
            calcXYZSumExtreme();
            int axis = selectionAxis();
            ExtremeVal priority = calcPriority(axis);
            ok = ExtremeVal.analyzeMAX_MIN(priority, epsilon);
            if(ok)
            {
                count[axis]++;
                printExtreme("!ok("+count[axis]+") " + at(axis) + ExtremeVal.chainToString(priority));
            }
            if(ok) clear(axis);
            return ok;
        }
        if(type == absMIN_MAX_MIN)
        {
            calcAExtreme();
            ok = ExtremeVal.analyzeMIN_MAX_MIN(firstA, epsilon);
            if(ok)
            {
                count[0]++;
                printExtreme("!ok("+count[0]+") A=" + ExtremeVal.chainToString(firstA));
            }
            if(ok) clear(0);
            return ok;
        }
        if(type == absMIN_MAX)
        {
            calcAExtreme();
            ok = ExtremeVal.analyzeMIN_MAX(firstA, epsilon);
            if(ok)
            {
                count[0]++;
                printExtreme("!ok("+count[0]+") A+" + ExtremeVal.chainToString(firstA));
            }
            if(ok) clear(0);
            return ok;
        }
        if(type == absMAX_MIN)
        {
            calcAExtreme();
            ok = ExtremeVal.analyzeMAX_MIN(firstA, epsilon);
            if(ok)
            {
                count[0]++;
                printExtreme("!ok("+count[0]+") A+" + ExtremeVal.chainToString(firstA));
            }
            if(ok) clear(0);
            return ok;
        }
        if(type == absMAX_MIN_MAX)
        {
            calcAExtreme();
            ok = ExtremeVal.analyzeMAX_MIN_MAX(firstA, epsilon);
            if(ok)
            {
                count[0]++;
                printExtreme("!ok("+count[0]+") A=" + ExtremeVal.chainToString(firstA));
            }
            if(ok) clear(0);
            return ok;
        }

        printExtreme(xyzToString(number, abc, x, y, z));
        return ok;
    }

    public void printGraph(String s){
            try {
                FileOutputStream outputStream;
                File file = new File ("/storage/emulated/0/folder/", fname);
                outputStream = new FileOutputStream(file, true);
                outputStream.write((s + "\n").getBytes());
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    public void printExtreme(String s){
        try {
            FileOutputStream outputStream;
            File file = new File ("/storage/emulated/0/folder/", fnameExtr);
            outputStream = new FileOutputStream(file, true);
            outputStream.write((s + "\n").getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

