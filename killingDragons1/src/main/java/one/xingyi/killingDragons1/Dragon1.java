package one.xingyi.killingDragons1;
import org.apache.log4j.Logger;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
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

    public Dragon1 damage(int damage) {
        try {
            if (damage <= 0 || isDead()) return this;
            int newHitpoints = hitpoints - damage;
            if (newHitpoints <= 0) {
                logger.info("dragon was hit for " + damage + " and is now DEAD!");
                return new Dragon1(0, false);
            } else {
                damageCount.incrementAndGet();
                logger.info("damage dragon for " + damage + "hitpoints. Hitpoints now" + newHitpoints);
                return new Dragon1(newHitpoints, alive);
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
    @Override public String toString() { return "Dragon1{hitpoints=" + hitpoints + ", alive=" + alive + '}'; }
}
