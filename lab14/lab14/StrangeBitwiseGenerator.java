package lab14;

import lab14lib.Generator;

public class StrangeBitwiseGenerator implements Generator {
    private int period;
    private int state;

    public StrangeBitwiseGenerator(int period) {
        state = 0;
        this.period = period;
    }

    @Override
    public double next() {
        state = state + 1;
        int weirdState = state & (state >> 7) % period;
        return normalize(weirdState);
    }

    private double normalize(int num) {
        int x = num % period;
        if (x == 0) {
            return -1.0;
        }
        double unitIncrease = 2.0 / period;
        double y = -1.0 + x * unitIncrease;
        return y;
    }
}
