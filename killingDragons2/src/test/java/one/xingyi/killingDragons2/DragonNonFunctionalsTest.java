package one.xingyi.killingDragons2;

import one.xingyi.killingDragons2.nonFunctionals.ErrorStrategy;
import org.junit.jupiter.api.Test;

import static one.xingyi.killingDragons2.DragonNonFunctionals.logMsgFn;
import static one.xingyi.killingDragons2.DragonNonFunctionals.metricNameFn;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

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


    void checkJustThrows(RuntimeException e) {
        try {
            ErrorStrategy.justThrow.handle(e);
            fail();
        } catch (RuntimeException actual) {
            assertEquals(e, actual);
        }
    }

    @Test
    public void testJustThrowErrorStrategyRethrowsAnythingExtendingRuntimeException() {
        checkJustThrows(new RuntimeException("some msg"));
        checkJustThrows(new IllegalArgumentException("some msg"));
        checkJustThrows(new NullPointerException("some msg"));
    }

    void checkWraps(Exception e) {
        try {
            ErrorStrategy.justThrow.handle(e);
            fail();
        } catch (RuntimeException actual) {
            assertEquals(e, actual.getCause());
        }
    }

    @Test
    public void testJustThrowErrorStrategyWrapsExceptionWithARuntimeException() {
        checkWraps(new Exception("some message"));
    }

    @Test
    public void testOurNonFunctionalsAreACompositionOfErrorHandlingMetricsAndLogging() {
        //We can't write this test easily with our current code. Is that a sign we need to change it?
    }
}