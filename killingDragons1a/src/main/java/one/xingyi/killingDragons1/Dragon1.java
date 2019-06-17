package one.xingyi.killingDragons1;

import lombok.*;
import org.apache.log4j.Logger;

import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
class DragonAndResult {
    final Dragon1 dragon;
    final String result;
}

class DragonConstants {
    public static String validationError = "validationError";
    public static String killedIt = "killedIt";
    public static String damagedIt = "damagedIt";
}

public class Dragon1 {
    final static Logger logger = Logger.getLogger(Dragon1.class);

    final int hitpoints;
    final boolean alive;

    final static AtomicInteger damageCount = new AtomicInteger();

    public static int counts() {
        return damageCount.get();
    }

    boolean isDead() {
        return !alive;
    }

    public static Dragon1 freshDragon = new Dragon1(1000, true);

    Dragon1(int hitpoints, boolean alive) {
        this.hitpoints = hitpoints;
        this.alive = alive;
    }


    static ResourceBundle bundle = ResourceBundle.getBundle("dragons");

    <From, To> Function<From, To> sideeffect(BiConsumer<From, To> consumer, Function<From, To> delegate) {
        return from -> {
            To to = delegate.apply(from);
            consumer.accept(from, to);
            return to;
        };
    }

    <From, To> Function<From, To> logMe(Function<To, String> messageFn, BiFunction<From, To, Object[]> paramsFn, Function<From, To> delegate) {
        return sideeffect((from, to) ->
                logger.info(MessageFormat.format(bundle.getString(messageFn.apply(to)), paramsFn.apply(from, to))), delegate);
    }

    public <From, To> Function<From, To> metrics(MetricsAbstraction abstraction, Function<To, String> metricsNameFn, Function<From, To> delegate) {
        return sideeffect((from, to) ->
            abstraction.addOneTo(metricsNameFn.apply(to)), delegate);
    }

    //Scaffolding
    public Dragon1 damage(int damage) {
        return metrics(MetricsAbstraction.dragonMetrics, DragonAndResult::getResult,
                logMe(DragonAndResult::getResult, (d, dr) -> new Object[]{d, dr.dragon.hitpoints},
                        this::mydamage)).apply(damage).dragon;
    }


    interface MetricsAbstraction {
        void addOneTo(String countName);

        MetricsAbstraction dragonMetrics = countName -> {
            if (countName.equals("damageCount")) damageCount.incrementAndGet();
        };
    }


    public DragonAndResult mydamage(int damage) {
        try {
            if (damage <= 0 || isDead()) return new DragonAndResult(this, "validationError");
            int newHitpoints = hitpoints - damage;
            if (newHitpoints <= 0) {
                return new DragonAndResult(new Dragon1(0, false), "killedIt");
            } else {
                return new DragonAndResult(new Dragon1(newHitpoints, alive), "damagedIt");
            }
        } catch (RuntimeException e) {
            logger.error("Unexpected error damaging " + this + " for " + damage + " hitpoints", e);
            throw e;
        }
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Dragon1 dragon = (Dragon1) object;
        return hitpoints == dragon.hitpoints && alive == dragon.alive;
    }

    public int hashCode() {
        return Objects.hash(super.hashCode(), hitpoints, alive);
    }

    @Override
    public String toString() {
        return "Dragon1{hitpoints=" + hitpoints + ", alive=" + alive + '}';
    }

    public static void main(String[] args) {
        System.out.println("Killing Dragons for Fun and Profit");

        Dragon1 d1 = Dragon1.freshDragon;
        Dragon1 d2 = d1.damage(100);
        Dragon1 d3 = d2.damage(100);
        Dragon1 d4 = d2.damage(900);
        for (Dragon1 d : Arrays.asList(d1, d2, d3, d4))
            System.out.println(d);
        System.out.println("Your dragon is " + (d4.alive ? "alive" : "dead"));
        System.out.println();
        System.out.println("Metrics are: ");
        System.out.println(Dragon1.damageCount.get());

    }
}
