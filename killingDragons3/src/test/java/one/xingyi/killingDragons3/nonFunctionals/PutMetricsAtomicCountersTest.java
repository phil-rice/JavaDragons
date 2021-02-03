package one.xingyi.killingDragons3.nonFunctionals;

import one.xingyi.killingDragons3.nonFunctionals.AtomicCounters;
import one.xingyi.killingDragons3.nonFunctionals.PutMetrics;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PutMetricsAtomicCountersTest {

    @Test
    public void testToStringWithNoMetricsIsEmpty() {
        AtomicCounters counters = (AtomicCounters) PutMetrics.atomicCounters();
        assertEquals("{}", counters.toString());
    }

    @Test
    public void testToStringWithMetricsIsEmpty() {
        AtomicCounters counters = (AtomicCounters) PutMetrics.atomicCounters();
        counters.addOne("a");
        counters.addOne("a");
        counters.addOne("a");
        counters.addOne("b");
        assert (counters.toString().contains("a=3"));
        assert (counters.toString().contains("b=1"));
    }
}
