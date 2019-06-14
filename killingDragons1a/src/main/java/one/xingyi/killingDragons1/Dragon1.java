package one.xingyi.killingDragons1;

import lombok.*;
import org.apache.log4j.Logger;

import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
class DragonAndResult {
    final Dragon1 dragon;
    final String result;
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

    Function<Integer, DragonAndResult> logMe(Function<Integer, DragonAndResult> delegate) {
        return from -> {
            DragonAndResult dr = delegate.apply(from);
            logger.info(MessageFormat.format(dr.result, new Object[]{from, dr.dragon.hitpoints}));
            return dr;
        };
    }
    //Scaffolding
    public Dragon1 damage(int damage) {
        return logMe(this::mydamage).apply(damage).dragon;
    }

    public DragonAndResult mydamage(int damage) {
        try {
            if (damage <= 0 || isDead()) return new DragonAndResult(this, "validationError");
            int newHitpoints = hitpoints - damage;
            if (newHitpoints <= 0) {
//                logger.info("dragon was hit for " + damage + " and is now DEAD!");
                return new DragonAndResult(new Dragon1(0, false), "killedIt");
            } else {
                damageCount.incrementAndGet();
//                logger.info("damage dragon for " + damage + "hitpoints. Hitpoints now" + newHitpoints);
                return new DragonAndResult(new Dragon1(newHitpoints, alive), "damagedIt");
            }
        } catch (RuntimeException e) {
            logger.error("Unexpected error damaging " + this + " for " + damage + " hitpoints", e);
            throw e;
        }
    }

    public DragonAndResult healing(int healing) {
        try {
            if (damage <= 0 || isDead()) return new DragonAndResult(this, "validationError");
            int newHitpoints = hitpoints - damage;
            if (newHitpoints <= 0) {
//                logger.info("dragon was hit for " + damage + " and is now DEAD!");
                return new DragonAndResult(new Dragon1(0, false), "killedIt");
            } else {
                damageCount.incrementAndGet();
//                logger.info("damage dragon for " + damage + "hitpoints. Hitpoints now" + newHitpoints);
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
