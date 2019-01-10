package one.xingyi.killingDragons2.nonFunctionals;

import org.apache.log4j.Logger;

import java.util.function.BiFunction;
import java.util.function.Function;
class LoggerWrapper<F, T> implements Function<F, T> {
    final Function<F, T> fn;
    final BiFunction<F, T, String> msgFn;
    final Logger logger;
    public LoggerWrapper(Logger logger, BiFunction<F, T, String> msgFn, Function<F, T> fn) {
        this.msgFn = msgFn;
        this.fn = fn;
        this.logger = logger;
    }
    @Override public T apply(F f) {
        T result = fn.apply(f);
        String msg = msgFn.apply(f, result);
        logger.info(msg);
        return result;
    }
}
