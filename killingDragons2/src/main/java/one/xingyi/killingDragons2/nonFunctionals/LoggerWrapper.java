package one.xingyi.killingDragons2.nonFunctionals;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;

import java.util.function.BiFunction;
import java.util.function.Function;

//This is tightly coupled to Log4J. It would be good to decouple it like the PutMetrics have done.
@RequiredArgsConstructor
class LoggerWrapper<F, T> implements Function<F, T> {
    final Logger logger;
    final BiFunction<F, T, String> msgFn;
    final Function<F, T> fn;
    @Override public T apply(F f) {
        T result = fn.apply(f);
        String msg = msgFn.apply(f, result);
        logger.info(msg);
        return result;
    }
}
