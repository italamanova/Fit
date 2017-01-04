package com.example.ira.fit.Motion;

public interface XYZLinearFilter
{
	public float[] filter(float[] acceleration);
	public void setFilterCoefficient(float filterCoefficient);
	public void reset();
}
