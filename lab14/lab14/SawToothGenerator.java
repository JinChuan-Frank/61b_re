package lab14;

import lab14lib.Generator;

public class SawToothGenerator implements Generator {
    private int period;
    private int state;

    public SawToothGenerator(int period) {
        state = 0;
        this.period = period;
    }

    @Override
    public double next() {
        state = (state + 1);
        return normalize(state);
    }

    private double normalize(int state) {
        int x = state % period;
        if (x == 0) {
            return -1.0;
        }
        double unitIncrease = 2.0 / period;
        double y = -1.0 + x * unitIncrease;
        return y;
    }
}
