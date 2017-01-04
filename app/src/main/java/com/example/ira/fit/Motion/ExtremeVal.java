package com.example.ira.fit.Motion;
// use for accumulate extreme calc ExtremeCalc

public class ExtremeVal
{
	public static boolean analyzeMIN_MAX(ExtremeVal v, float epsilon)
	{
		boolean ok = false;
		ExtremeVal ev = v;
		while(ev != null)
		{
			if(ev.isMinimum() && (Math.abs(ev.getValue()) > epsilon))
			{
				ExtremeVal evNext = ev.getNext();
				while(evNext != null)
				{
					if(!evNext.isMinimum())
					{
						ok = true;
						break;
					}
					else
					{
						evNext = evNext.getNext();
					}
				}
				break;
			}
			else
				ev = ev.getNext();
			if(ok)
				break;
		}
		return ok;
	}
	public static boolean analyzeMAX_MIN(ExtremeVal v, float epsilon)
	{
		boolean ok = false;
		ExtremeVal ev = v;
		while(ev != null)
		{
			if(!ev.isMinimum() && (Math.abs(ev.getValue()) > epsilon))
			{
				ExtremeVal evNext = ev.getNext();
				while(evNext != null)
				{
					if(evNext.isMinimum())
					{
						ok = true;
						break;
					}
					else
					{
						evNext = evNext.getNext();
					}
				}
				break;
			}
			else
				ev = ev.getNext();
			if(ok)
				break;
		}
		return ok;
	}
	public static boolean analyzeMIN_MAX_MIN(ExtremeVal v, float epsilon)
	{
		boolean ok = false;
		ExtremeVal ev = v;
		while(ev != null)
		{
			if(ev.isMinimum() && (Math.abs(ev.getValue()) > epsilon))
			{
//System.out.println("1="+ev);
				ExtremeVal evNext = ev.getNext();
				while(evNext != null)
				{
					if(!evNext.isMinimum())
					{
//System.out.println("2="+evNext);
						ExtremeVal evNextNext = evNext.getNext();
						while(evNextNext != null)
						{
							if(evNextNext.isMinimum())
							{
//System.out.println("3="+evNextNext);
								ok = true;
								break;
							}
							else
							{
								evNextNext = evNextNext.getNext();
							}
							if(ok)
								break;
						}
						//if(ok)
						break;
					}
					else
					{
						evNext = evNext.getNext();
					}
					if(ok)
						break;
				}
				break;
			}
			else
				ev = ev.getNext();
			if(ok)
				break;
		}
		return ok;
	}
	public static String chainToString(ExtremeVal first)
	{
		ExtremeVal ev = first;
		String s = ">" + ev;
		while(ev != null)
		{
			ev = ev.getNext();
			if(ev == null)
				break;
			s += "\n+" + ev;
		}
		return s;
	} 
//==========================================
	private boolean areMinimum; // min value (true( max value (false)
	private long start;         // start number extreme
	private long end;           // end number extreme
	private float value;        // extreme value
	private ExtremeVal prev;    // prev extreme
	private ExtremeVal next;    // next extreme
	public ExtremeVal(boolean areMinimum, float value, long start, long end, ExtremeVal prev)
	{
		this.areMinimum = areMinimum;
		this.value = value;
		this.start = start;
		this.end = end;
		this.prev = prev;
		next = null;
	}
	public boolean isMinimum()
	{
		return areMinimum;
 	}
	public float getValue()
	{
		return value;
	}
	public long getStart()
	{
		return start;
	}
	public long getEnd()
	{
		return end;
	}
	public ExtremeVal getPrev()
	{
		return prev;
	}
	public ExtremeVal getNext()
	{
		return next;
	}
	public void setNext(ExtremeVal next)
	{
		this.next = next;
	}
	public String toString()
	{
		String s;
		if(isMinimum())
			s = "{Min ";
		else
			s = "{Max ";
		s = s+getValue()+" at "+start+":"+end+"}";
		return s;
	
	}
}

