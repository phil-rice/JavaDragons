package one.xingyi.killingDragons2.nonFunctionals;

import org.apache.logging.log4j.Logger;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface NonFunctionals {
    static <F, T> Function<F, T> metrics(PutMetrics putMetrics, BiFunction<F, T, String> metricNameFn, Function<F, T> fn) {
        return new Metrics<>(putMetrics, metricNameFn, fn);
    }
    static <F, T> Function<F, T> errors(ErrorStrategy errorStrategy, Function<F, T> fn) {
        return new ErrorHandler<>(errorStrategy, fn);
    }
    static <F, T> Function<F, T> log(Logger logger, BiFunction<F, T, String> msgFn, Function<F, T> fn) {
        return new LoggerWrapper<>(logger, msgFn, fn);
    }
}

