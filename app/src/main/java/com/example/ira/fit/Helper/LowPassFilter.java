package com.example.ira.fit.Helper;

/**
 * An implementation of the Android Developer low-pass filter. The Android
 * Developer LPF, is an IIR single-pole implementation. The coefficient, a
 * (alpha), can be adjusted based on the sample period of the sensor to produce
 * the desired time constant that the filter will act on. It is essentially the
 * same as the Wikipedia LPF. It takes a simple form of y[0] = alpha * y[0] + (1
 * - alpha) * x[0]. Alpha is defined as alpha = timeConstant / (timeConstant +
 * dt) where the time constant is the length of signals the filter should act on
 * and dt is the sample period (1/frequency) of the sensor.
 */
public class LowPassFilter {
    private float filterCoefficient = 0.9f;//5f;

    // Gravity and linear accelerations components for the
    // Wikipedia low-pass filter
//	private float[] output = new float[]
//	{ 0, 0, 0 };

    private float/*[]*/ gravity = 0;//new float[]
//	{ 0, 0, 0 };

    // Raw accelerometer data
//	private float[] input = new float[]
//	{ 0, 0, 0 };

    //baa
    private boolean first = true;

    public LowPassFilter() {
        first = true;
    }

    /**
     * Add a sample.
     *
     * @param acceleration The acceleration data.
     * @return Returns the output of the filter.
     */
    public float filter(float acceleration) {
        if (first) {
            gravity = acceleration;
            first = false;
        }
//		System.arraycopy(acceleration, 0, input, 0, acceleration.length);

        float oneMinusCoeff = (1.0f - filterCoefficient);

        gravity = filterCoefficient * gravity + oneMinusCoeff * acceleration;

        // Determine the linear acceleration
        return acceleration - gravity;
    }

    /**
     * The complementary filter coefficient, a floating point value between 0-1,
     * exclusive of 0, inclusive of 1.
     *
     * @param filterCoefficient
     */
    public void setFilterCoefficient(float filterCoefficient) {
        this.filterCoefficient = filterCoefficient;
    }
}
