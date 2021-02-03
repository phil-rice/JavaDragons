package one.xingyi.killingDragons3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DragonDamageResultTest {
    @Test
    public void testCanMakeFromDragonAndDescription(){
        Dragon3 dragon=new Dragon3(1, true);
        assertEquals(new DragonDamageResult(dragon, "some description"), DragonDamageResult.aliveWith(dragon, "some description"));
    }

    @Test
    public void testCanMakeFromHitpointsAndDescription(){
        Dragon3 dragon=new Dragon3(123, true);
        assertEquals(new DragonDamageResult(dragon, "some description"), DragonDamageResult.aliveWith(123, "some description"));

    }
}
