package one.xingyi.killingDragons;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
public class Dragon {
    final static Logger logger = Logger.getLogger(Dragon.class);

    final int hitpoints;
    final boolean alive;

    final static AtomicInteger damageCount = new AtomicInteger();
    public static int counts() {
        return damageCount.get();
    }

    public static Dragon freshDragon = new Dragon(1000, true);

    Dragon(int hitpoints, boolean alive) {
        this.hitpoints = hitpoints;
        this.alive = alive;
    }

    public Dragon damage(int damage) {
        //your code goes here
        // you need to return a new dragon following the rules:
        //Damage is subtracted from Health
        //When damage received exceeds current Health, Health becomes 0 and the character dies
        //For extra points add Logging, and a Metric that counts the number of times a character is damaged.
        //You could also put in an error handler
        throw new RuntimeException("not implemented yet");
    }
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Dragon dragon = (Dragon) object;
        return hitpoints == dragon.hitpoints && alive == dragon.alive;
    }

    public int hashCode() {
        return Objects.hash(super.hashCode(), hitpoints, alive);
    }
    @Override public String toString() { return "Dragon{hitpoints=" + hitpoints + ", alive=" + alive + '}'; }
    public static void main(String[] args) {
        System.out.println("Killing Dragons for Fun and Profit");

        Dragon d1 = Dragon.freshDragon;
        Dragon d2 = d1.damage(100);
        Dragon d3 = d2.damage(100);
        Dragon d4 = d2.damage(900);
        for (Dragon d : Arrays.asList(d1, d2, d3, d4))
            System.out.println(d);
        System.out.println("Your dragon is " + (d4.alive ? "alive" : "dead"));
        System.out.println();
        System.out.println("Metrics are: ");
        System.out.println(Dragon.damageCount.get());

    }
}
