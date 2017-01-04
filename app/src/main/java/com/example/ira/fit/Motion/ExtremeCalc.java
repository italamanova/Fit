package com.example.ira.fit.Motion;

public class ExtremeCalc
{
	private float probe;    //prev value
	private float minVal;   // min value in calc process
	private float maxVal;   // max value in calc process
	private long count;     // number probe find extreme
	private long start;     // start number probe find extreme
	public ExtremeCalc()
	{
		clear();
		count = 0;
	}
	public void clear()
	{
		probe = 0;
		minVal = 1000;
		maxVal = -1000;
		start = count + 1;
	}
	public ExtremeVal add(float val/*, float epsilon*/, ExtremeVal prev)
	{
		count++;
		if(minVal > val)
			minVal = val;
		if(maxVal < val)
			maxVal = val;
		if(probe != 0)
		{
			if((probe > 0) && (val < 0))
			{
				ExtremeVal ev = new ExtremeVal(false, maxVal, start, count, prev);
				clear();
				return ev;
			}
			if((probe < 0) && (val > 0))
			{
				ExtremeVal ev = new ExtremeVal(true, minVal, start, count, prev);
				clear();
				return ev;
			}
		}
		probe = val;
		return null;
	}
}
