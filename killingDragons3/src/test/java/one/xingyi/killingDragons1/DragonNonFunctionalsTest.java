package one.xingyi.killingDragons1;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class DragonNonFunctionalsTest {

    DragonNonFunctionals dragonNonFunctionals = new DragonNonFunctionals(MyLogger.nullLogger);
    CompositeFunction<Attack, DragonAndResult> compose = (CompositeFunction<Attack, DragonAndResult>) dragonNonFunctionals.nonFunctionals;
    List<DelegateFunction<Attack, DragonAndResult>> delegates = compose.fns;

    @Test
    public void testNonFunctionsIncludeErrorHandling() {
        assertTrue(delegates.contains(dragonNonFunctionals.handleErrorsForAttack));
    }

    @Test
    public void testNonFunctionsIncludeMetrics() {
        assertTrue(delegates.contains(dragonNonFunctionals.attackMetrics));
    }

    @Test
    public void testNonFunctionsIncludesLogging() {
        assertTrue(delegates.contains(dragonNonFunctionals.logForDragon));
    }

    @Test
    public void testNonFunctionsIncludesValidations() {
        assertTrue(delegates.contains(dragonNonFunctionals.validations));
    }

}