package one.xingyi.killingDragons2.nonFunctionals;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

//This is how we do metrics. We just decoupled ourselves from the actual implementation by doing this
public interface PutMetrics {
    void addOne(String metricName);

    static PutMetrics println() { return name -> System.out.println("Adding one to metric " + name + "  " + atomicCountersData()); }

    static PutMetrics atomicCounters() { return new AtomicCounters(); }

    static Map<String, Integer> atomicCountersData() {
        Map<String, Integer> result = new HashMap<>();
        for (Map.Entry<String, AtomicInteger> e : AtomicCounters.map.entrySet()) {
            result.put(e.getKey(), e.getValue().get());
        }
        return result;
    }
}

class AtomicCounters implements PutMetrics {
    static Map<String, AtomicInteger> map = new ConcurrentHashMap<>();

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
}
