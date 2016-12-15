package com.example.ira.fit.Helper;

public class Extreme {
    private float probe;
    private float minVal;
    private float maxVal;
    private int count;
    private float sum;

    public Extreme() {
        clear();
        sum = 0;
    }

    public void clear() {
        probe = 0;
        minVal = 1000;
        maxVal = -1000;
        count = 0;
//		sum = 0;
    }

    public float add(float val) {
        sum += val;
//System.out.println("probe="+probe+";val="+val+";sum="+sum);
        if (minVal > sum)
            minVal = sum;
        if (maxVal < sum)
            maxVal = sum;
        if (probe != 0) {
            if (((probe > 0) && (sum > 0)) || ((probe < 0) && (sum < 0)))
                probe = sum;
            if ((probe > 0) && (sum < 0))
                return maxVal;
            if ((probe < 0) && (sum > 0))
                return minVal;
        } else
            probe = sum;
        return 0;
    }
}
