package one.xingyi.killingDragons3;

import one.xingyi.killingDragons3.functions.ComposedDelegate;
import one.xingyi.killingDragons3.nonFunctionals.ErrorStrategy;
import org.junit.jupiter.api.Test;

import static one.xingyi.killingDragons3.DragonNonFunctionals.logMsgFn;
import static one.xingyi.killingDragons3.DragonNonFunctionals.metricNameFn;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class DragonNonFunctionalsTest {

    DragonDamageResult dragonDamageResult = new DragonDamageResult(Dragon3.freshDragon, "someDescription");
    Attack someAttack = new Attack(1, Dragon3.freshDragon);

    @Test
    public void testMetricNameIsJustDescription() {
        assertEquals("someDescription", metricNameFn.apply(someAttack, dragonDamageResult));
    }

    @Test
    public void testLogMsg() {
        assertEquals("Hit dragon for 1 and did someDescription", logMsgFn.apply(someAttack, dragonDamageResult));
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
        assertEquals("ErrorHandler,Metrics,LoggerDelegate,DelegateValidator", DragonNonFunctionals.nonFunctionals.classes);
        //note that this is a (deliberatly) weak test
        //we just check that 'we have' some sort of error handler, metrics, logger and validator
        //we make no assertions about what they are. if we want to do that we can do that in another test
        //This test protects us from accidentally deleting, and could be considered to be adequate
    }
}