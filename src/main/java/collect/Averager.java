package collect;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;

class Average {
  private double sum = 0;
  private long count = 0;

  public Average() {
  }

  public void include(double d) {
    this.sum += d;
    this.count += 1;
  }

  public void merge(Average other) {
    this.sum += other.sum;
    this.count += other.count;
  }

  public double get() { // would be better as OptionalDouble or Optional<Double>
    return sum / count;
  }
}
public class Averager {
  public static void main(String[] args) {
    long start = System.nanoTime();

//    Average result = ThreadLocalRandom.current()
//        .doubles(6_000_000_000L, -Math.PI, +Math.PI)
//    Average result = DoubleStream.iterate(
//        0.0, x -> ThreadLocalRandom.current().nextDouble(-Math.PI, +Math.PI))
    Average result = DoubleStream.generate(
        () -> ThreadLocalRandom.current().nextDouble(-Math.PI, +Math.PI))
        .limit(6_000_000_000L)
//        .unordered()
        .parallel()
        .collect(
            () -> new Average(),
            (a, d) -> a.include(d),
            (a1, a2) -> a1.merge(a2));
    long time = System.nanoTime() - start;
    System.out.println("Average is " + result.get() +
        " time was: " + (time / 1_000_000_000.0));
  }

}
