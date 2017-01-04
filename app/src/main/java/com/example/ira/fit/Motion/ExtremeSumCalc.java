package com.example.ira.fit.Motion;
// extreme calc use for find etreme sum values
public class ExtremeSumCalc
{
	private float probe;    //prev sum value
	private float minVal;   // min value in calc process
	private float maxVal;   // max value in calc process
	private long count;     // number probe try time
	private long start;     // start number probe find extreme
	private float sum;
	public ExtremeSumCalc()
	{
		clear();
		count = 0;
		sum = 0;
	}
	public void clear(float pr)
	{
		probe = pr;
		minVal = 1000;
		maxVal = -1000;
		start = count + 1;
	}
	public void clear()
	{
		clear(0f);
	}
	public ExtremeVal add(float val,/* float epsilon,*/ ExtremeVal prev)
	{
		count++;
		sum += val;
		if(minVal > sum)
			minVal = sum;
		if(maxVal < sum)
			maxVal = sum;
		if(probe != 0)
		{
			if((probe > 0) && (sum < 0))
			{
				ExtremeVal ev = new ExtremeVal(false, maxVal, start, count, prev);
				clear();
				return ev;
			}
			if((probe < 0) && (sum > 0))
			{
				ExtremeVal ev = new ExtremeVal(true, minVal, start, count, prev);
				clear();
				return ev;
			}
		}
		probe = sum;
		return null;
	}
}
