package one.xingyi.killingDragons;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

    @AllArgsConstructor
    @EqualsAndHashCode
    @ToString
    class Money{
         int amount;
    }
public class WhyImmutable {


    public static void main(String[] args) {
        Set<Money> set = new HashSet<>();
        val m1 = new Money(1);
        val m2 = new Money(2);
        val m3 = new Money(2);

        set.add(m1);
        set.add(m2);
        set.add(m3);

        System.out.println("Set at start is " + set);
        System.out.println("    " +m1 +  set.contains(m1));
        System.out.println("    " +m2 +  set.contains(m2));
        System.out.println("    " +m3 +  set.contains(m3));

        m2.amount=5;
        System.out.println("Set at end is " + set);
        System.out.println("    " +m1 +  set.contains(m1));
        System.out.println("    " +m2 +  set.contains(m2));
        System.out.println("    " +m3 +  set.contains(m3));

    }
}
