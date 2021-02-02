package one.xingyi.killingDragons1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Dragon1Test {
    Dragon1 fresh = Dragon1.freshDragon;
    Dragon1 hitpoints900 = new Dragon1(900, true);
    Dragon1 hitpoints100 = new Dragon1(100, true);
    Dragon1 dead = new Dragon1(0, false);

    @Test
    public void testCreatedFreshDragonWith1000HitPoints() {
        assertEquals(1000, Dragon1.freshDragon.hitpoints);
    }
    @Test
    public void testCreatedFreshDragonAlive() {
        assertEquals(true, fresh.alive);
        assertEquals(false, fresh.isDead());
    }

    @Test
    public void testDamageDragonWithoutKilling() {
        assertEquals(hitpoints900, fresh.damage(100));
        assertEquals(hitpoints100, fresh.damage(900));
    }
    @Test
    public void testJustKillingDragon_ZeroHitpointsIsDeath() {
        assertEquals(dead, fresh.damage(1000));
        assertEquals(dead, hitpoints900.damage(900));
        assertEquals(dead, hitpoints100.damage(100));
    }
    @Test
    public void testOverKillingDragon() {
        assertEquals(dead, fresh.damage(10000));
        assertEquals(dead, fresh.damage(1001));
        assertEquals(dead, hitpoints900.damage(901));
        assertEquals(dead, hitpoints100.damage(101));
    }

    @Test
    public void testCannotHurtADeadDragon() {
        assertEquals(dead, dead.damage(1));
        assertEquals(dead, dead.damage(1000));
    }

    @Test
    public void testCannotHealDragonByusingNegativeHitpoints() {
        assertEquals(hitpoints900, hitpoints900.damage(-1));
        assertEquals(hitpoints900, hitpoints900.damage(-100));
        assertEquals(fresh, fresh.damage(-100));
        assertEquals(dead, dead.damage(-1));
        assertEquals(dead, dead.damage(-100));
    }

    @Test
    public void testCounter() {
        assertEquals(2, Dragon1.counts());//what's wrong with this?
    }

    @Test
    public void testLogging() {
        //OK how do we do this?????
    }
}
