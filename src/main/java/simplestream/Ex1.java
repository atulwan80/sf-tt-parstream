package simplestream;

import java.util.Optional;
import java.util.stream.Stream;

public class Ex1 {
  public static void main(String[] args) {
    Optional<Integer> result;
    result = Stream.iterate(1, x -> x + 1)
        .limit(10)
        .reduce((x, y) -> x + y);
//        .forEach(x -> System.out.println(x));

    result.ifPresent(x -> System.out.println("Sum is " + x));

    Integer res2 = Stream.iterate(1, x -> x + 1)
        .limit(10)
        .reduce(0, (x, y) -> x + y);

    result.ifPresent(x -> System.out.println("Sum is " + x));
  }
}
