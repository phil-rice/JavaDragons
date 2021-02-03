package one.xingyi.killingDragons3.nonFunctionals;

import lombok.RequiredArgsConstructor;
import one.xingyi.killingDragons3.functions.DelegateFunction;
import org.apache.logging.log4j.Logger;

import java.util.function.BiFunction;
import java.util.function.Function;

//This is tightly coupled to Log4J. It would be good to decouple it like the PutMetrics have done.
@RequiredArgsConstructor
public class LoggerDelegate<F, T> implements DelegateFunction<F, T> {
    final Logger logger;
    final BiFunction<F, T, String> msgFn;

    @Override public Function<F, T> apply(Function<F, T> raw) {
        return f -> {
            T result = raw.apply(f);
            String msg = msgFn.apply(f, result);
            logger.info(msg);
            return result;
        };
    }
}
