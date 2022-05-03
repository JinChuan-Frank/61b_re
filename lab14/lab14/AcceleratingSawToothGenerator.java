package lab14;

import lab14lib.Generator;

public class AcceleratingSawToothGenerator implements Generator {
    private int period;
    private int state;
    private double factor;

    public AcceleratingSawToothGenerator(int period, double factor) {
        state = 0;
        this.period = period;
        this.factor = factor;
    }

    @Override
    public double next() {
        state = (state + 1);
        return normalize(state);
    }

    private double normalize(int num) {
        int x = num % period;

        if (x == 0) {
            this.state -= period;
            period = (int) Math.floor(factor * period);
            return -1.0;
        }

        double unitIncrease = 2.0 / period;
        double y = -1.0 + x * unitIncrease;
        /**System.out.println("state: " + state + " " + "period: "
                + period + " " + "x: " + x + " " + "y: " + y); */
        return y;
    }
}
