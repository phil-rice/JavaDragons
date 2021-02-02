package one.xingyi.killingDragons2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DragonDamageResultTest {
    @Test
    public void testCanMakeFromDragonAndDescription(){
        Dragon2 dragon=new Dragon2(1, true);
        assertEquals(new DragonDamageResult(dragon, "some description"), DragonDamageResult.result(dragon, "some description"));
    }

    @Test
    public void testCanMakeFromHitpointsAndDescription(){
        Dragon2 dragon=new Dragon2(123, true);
        assertEquals(new DragonDamageResult(dragon, "some description"), DragonDamageResult.result(123, "some description"));

    }
}
