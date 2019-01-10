package one.xingyi.killingDragons2;
import org.junit.Test;

import static org.junit.Assert.*;

import static one.xingyi.killingDragons2.DragonDamageResult.*;
import static one.xingyi.killingDragons2.DragonNonFunctionals.*;

public class DragonNonFunctionalsTest {

    DragonDamageResult dragonDamageResult = new DragonDamageResult(Dragon2.freshDragon, "someDescription");
    @Test
    public void testMetricNameIsJustDescription() {
        assertEquals("someDescription", metricNameFn.apply(1, dragonDamageResult));
    }

    @Test
    public void testLogMsg() {
        assertEquals("Hit dragon for 1 and did someDescription", logMsgFn.apply(1, dragonDamageResult));
    }

}