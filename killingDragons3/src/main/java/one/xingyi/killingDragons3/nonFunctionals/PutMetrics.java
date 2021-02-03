package one.xingyi.killingDragons3.nonFunctionals;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

//This is how we do metrics. We just decoupled ourselves from the actual implementation by doing this
public interface PutMetrics {
    void addOne(String metricName);

    static PutMetrics println() { return name -> System.out.println("Metric [" + name + "] has occured "); }

    static PutMetrics atomicCounters() { return new AtomicCounters(); }

}

class AtomicCounters implements PutMetrics {
    Map<String, AtomicInteger> map = new ConcurrentHashMap<>();

    AtomicInteger get(String metricName) {
        AtomicInteger result = map.get(metricName);
        if (result == null) {
            map.putIfAbsent(metricName, new AtomicInteger());
            result = map.get(metricName);
        }
        return result;
    }

    @Override public void addOne(String metricName) {
        get(metricName).incrementAndGet();
    }

    @Override
    public String toString() {
        return map.toString();
    }
}
