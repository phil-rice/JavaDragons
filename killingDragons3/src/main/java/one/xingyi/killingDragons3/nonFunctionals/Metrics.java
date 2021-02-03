package one.xingyi.killingDragons3.nonFunctionals;

import lombok.RequiredArgsConstructor;
import one.xingyi.killingDragons3.functions.DelegateFunction;

import java.util.function.BiFunction;
import java.util.function.Function;

//Wraps another function, evaluates how it went (by calculating a metric name) and then adds one to the metric
@RequiredArgsConstructor
public class Metrics<From, To> implements DelegateFunction<From, To> {

    final PutMetrics putMetrics;
    final BiFunction<From, To, String> metricNameFn;

    @Override public Function<From, To> apply(Function<From, To> raw) {
        return from -> {
            To result = raw.apply(from);
            String metricName = metricNameFn.apply(from, result);
            putMetrics.addOne(metricName);
            return result;
        };
    }
}
