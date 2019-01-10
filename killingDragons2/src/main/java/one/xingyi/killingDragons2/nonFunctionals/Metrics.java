package one.xingyi.killingDragons2.nonFunctionals;
import java.util.function.BiFunction;
import java.util.function.Function;

class Metrics<From, To> implements Function<From, To> {

    final PutMetrics putMetrics;
    final BiFunction<From, To, String> metricNameFn;
    final Function<From, To> fn;

    public Metrics(PutMetrics putMetrics, BiFunction<From, To, String> metricNameFn, Function<From, To> fn) {
        this.putMetrics = putMetrics;
        this.metricNameFn = metricNameFn;
        this.fn = fn;
    }

    @Override public To apply(From from) {
        To result = fn.apply(from);
        String metricName = metricNameFn.apply(from, result);
        putMetrics.addOne(metricName);
        return result;
    }
}
