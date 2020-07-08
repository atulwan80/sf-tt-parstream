package reduce;

import java.util.concurrent.ThreadLocalRandom;

class Average {
  private double sum;
  private long count;

  public Average(double sum, long count) {
    this.sum = sum;
    this.count = count;
  }

  public Average include(double d) {
    return new Average(this.sum + d, this.count + 1);
  }

  public Average merge(Average other) {
//    System.out.println("Merge called...");
    return new Average(this.sum + other.sum, this.count + other.count);
  }

  public double get() { // would be better as OptionalDouble or Optional<Double>
    return sum / count;
  }
}
public class Averager {
  public static void main(String[] args) {
    long start = System.nanoTime();

    Average result = ThreadLocalRandom.current()
        .doubles(1_000_000, -Math.PI, +Math.PI)
        .parallel()
        .boxed()
        .reduce(
            new Average(0, 0),
            (a, d) -> a.include(d),
            (a1, a2) -> a1.merge(a2));
    long time = System.nanoTime() - start;
    System.out.println("Average is " + result.get() +
        " time was: " + (time / 1_000_000_000.0));
  }
}
