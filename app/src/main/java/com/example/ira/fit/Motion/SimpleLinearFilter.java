package com.example.ira.fit.Motion;

// do linear filtering values
public class SimpleLinearFilter implements XYZLinearFilter
{
	private float filterCoefficient = 0.5f;

	// Gravity and linear accelerations components for the
	// Wikipedia low-pass filter
	private float[] output = new float[]
	{ 0, 0, 0 };

	private float[] gravity = new float[]
	{ 0, 0, 0 };

	// Raw accelerometer data
	private float[] input = new float[]
	{ 0, 0, 0 };

	private boolean areFirst;
	public SimpleLinearFilter() {
		areFirst = true;
	}
	public float[] filter(float[] acceleration)
	{
		if(areFirst)
		{
			System.arraycopy(acceleration, 0, gravity, 0, acceleration.length);
			areFirst = false;
		}
		System.arraycopy(acceleration, 0, input, 0, acceleration.length);

		float oneMinusCoeff = (1.0f - filterCoefficient);

		gravity[0] = filterCoefficient * gravity[0] + oneMinusCoeff * input[0];
		gravity[1] = filterCoefficient * gravity[1] + oneMinusCoeff * input[1];
		gravity[2] = filterCoefficient * gravity[2] + oneMinusCoeff * input[2];
        //Log.v("gravity", "" + gravity[0] + " " + gravity[1] + " " + gravity[2]);

		// Determine the linear acceleration
		output[0] = input[0] - gravity[0];
		output[1] = input[1] - gravity[1];
		output[2] = input[2] - gravity[2];


		return output;
	}
	public void setFilterCoefficient(float filterCoefficient)
	{
		this.filterCoefficient = filterCoefficient;
	}
	public void reset()
	{
	}
}
