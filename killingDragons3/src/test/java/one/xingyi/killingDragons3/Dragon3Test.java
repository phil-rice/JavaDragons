package one.xingyi.killingDragons3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Dragon3Test {
    Dragon3 fresh = Dragon3.freshDragon;
    Dragon3 hitpoints900 = new Dragon3(900, true);
    Dragon3 hitpoints100 = new Dragon3(100, true);
    Dragon3 dead = new Dragon3(0, false);

    void checkDoDamage(Dragon3 expected, Dragon3 original, int hitpoints, String msg) {
        assertEquals(new DragonDamageResult(expected, msg), Dragon3.resolveAttackWithNonFunctionals.apply(new Attack(hitpoints, original)));
    }

    @Test
    public void testCreatedFreshDragonWith1000HitPoints() {
        assertEquals(1000, Dragon3.freshDragon.hitpoints);
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

}
