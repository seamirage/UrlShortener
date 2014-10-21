package idgeneration;

import java.util.Random;

/**
 * Dummy implementation of IdGenerator interface, based on random numbers generator.
 */
public class DummyIdGeneratorImpl implements IdGenerator {
    public DummyIdGeneratorImpl(long maxValue) {
        this.random = new Random();
        this.maxValue = maxValue;
    }

    @Override
    public long next() {
        return nextLong();
    }

    private long nextLong() {
        long bits, val;
        do {
            bits = (random.nextLong() << 1) >>> 1;
            val = bits % maxValue;
        } while (bits-val+(maxValue-1) < 0L);
        return val;
    }

    private Random random;
    private long maxValue;
}
