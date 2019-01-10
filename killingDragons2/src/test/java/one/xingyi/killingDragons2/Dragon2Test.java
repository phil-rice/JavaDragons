package one.xingyi.killingDragons2;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
public class Dragon2Test {
    Dragon2 fresh = Dragon2.freshDragon;
    Dragon2 hitpoints900 = new Dragon2(900, true);
    Dragon2 hitpoints100 = new Dragon2(100, true);
    Dragon2 dead = new Dragon2(0, false);

    void checkDoDamage(Dragon2 expected, Dragon2 original, int hitpoints, String msg) {
        assertEquals(new DragonDamageResult(expected, msg), original.damage().apply(hitpoints));
    }
    @Test
    public void testCreatedFreshDragonWith1000HitPoints() {
        assertEquals(1000, Dragon2.freshDragon.hitpoints);
    }
    @Test
    public void testCreatedFreshDragonAlive() {
        assertEquals(true, fresh.alive);
    }

    @Test
    public void testDamageDragonWithoutKilling() {
        checkDoDamage(hitpoints900, fresh, 100, DragonMessages.damaged);
        checkDoDamage(hitpoints100, fresh, 900, DragonMessages.damaged);
    }
    @Test
    public void testJustKillingDragon_ZeroHitpointsIsDeath() {
        checkDoDamage(dead, fresh, 1000, DragonMessages.weKilledADragon);
        checkDoDamage(dead, hitpoints900, 900, DragonMessages.weKilledADragon);
        checkDoDamage(dead, hitpoints100, 100, DragonMessages.weKilledADragon);
    }
    @Test
    public void testOverKillingDragon() {
        checkDoDamage(dead, fresh, 10000, DragonMessages.weKilledADragon);
        checkDoDamage(dead, fresh, 1001, DragonMessages.weKilledADragon);
        checkDoDamage(dead, hitpoints900, 901, DragonMessages.weKilledADragon);
        checkDoDamage(dead, hitpoints100, 101, DragonMessages.weKilledADragon);
    }

    @Test
    public void testCannotHurtADeadDragon() {
        checkDoDamage(dead, dead, 1, DragonMessages.alreadyDead);
        checkDoDamage(dead, dead, 1000, DragonMessages.alreadyDead);
    }

    @Test
    public void testCannotHealDragonByusingNegativeHitpoints() {
        checkDoDamage(hitpoints900, hitpoints900, -1, DragonMessages.cannotDoNegativeDamage);
        checkDoDamage(hitpoints900, hitpoints900, -100, DragonMessages.cannotDoNegativeDamage);
        checkDoDamage(fresh, fresh, -100, DragonMessages.cannotDoNegativeDamage);
        checkDoDamage(dead, dead, -1, "[Cannot do negative damage, alreadyDead]");
        checkDoDamage(dead, dead, -100, "[Cannot do negative damage, alreadyDead]");
    }

    @Test
    public void testCounter() {
//        assertEquals(2, Dragon2.counts());//what's wrong with this?
    }

    @Test
    public void testLogging() {
        //OK how do we do this?????
    }
}
