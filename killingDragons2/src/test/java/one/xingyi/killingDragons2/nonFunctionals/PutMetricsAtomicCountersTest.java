package one.xingyi.killingDragons2.nonFunctionals;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PutMetricsAtomicCountersTest {

    @Test
    public void testToStringWithNoMetricsIsEmpty(){
        AtomicCounters counters = (AtomicCounters) PutMetrics.atomicCounters();
        assertEquals("{}", counters.toString());
    }
    @Test
    public void testToStringWithMetricsIsEmpty(){
        AtomicCounters counters = (AtomicCounters) PutMetrics.atomicCounters();
        counters.addOne("a");
        counters.addOne("a");
        counters.addOne("a");
        counters.addOne("b");
        assert( counters.toString().contains("a=3"));
        assert( counters.toString().contains("b=1"));
    }
}
