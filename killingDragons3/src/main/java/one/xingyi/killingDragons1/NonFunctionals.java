package one.xingyi.killingDragons1;

import org.apache.log4j.Logger;

import java.text.MessageFormat;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

interface NonFunctionals {
    ResourceBundle bundle = ResourceBundle.getBundle("dragons");

    static <From, To> DelegateFunction<From, To> sideeffect(BiConsumer<From, To> sideeffect) {
        return delegate -> (from -> {
            To to = delegate.apply(from);
            sideeffect.accept(from, to);
            return to;
        });

    }
        Function <Integer, Integer> times2=from->from*2;



    static <From, To> DelegateFunction<From, To> log(MyLogger logger, Function<To, String> messageFn, BiFunction<From, To, Object[]> messageParams) {
        return sideeffect((from, to) -> logger.info(MessageFormat.format(bundle.getString(messageFn.apply(to)), messageParams.apply(from, to))));
    }

    static <From, To> DelegateFunction<From, To> handleErrors(BiFunction<From, RuntimeException, RuntimeException> errorStrategy) {
        return delegate -> from -> {
            try {
                return delegate.apply(from);
            } catch (RuntimeException e) {
                throw errorStrategy.apply(from, e);
            }
        };
    }

    static <From> BiFunction<From, RuntimeException, RuntimeException> logAndThrow(MyLogger logger, String messageKey, Function<From, Object[]> messageParams) {
        return (from, runtimeException) -> {
            logger.error(MessageFormat.format(bundle.getString(messageKey), messageParams.apply(from)),runtimeException);
            return runtimeException;
        };
    }

    static <From, To> DelegateFunction<From, To> metrics(PutMetrics putMetrics, BiFunction<From, To, String> metricNameFn) {
        return sideeffect((from, to) -> putMetrics.addOne(metricNameFn.apply(from, to)));
    }

    static <From, To> DelegateFunction<From, To> validateAndThen(Function<From, Optional<String>> validate, BiFunction<From, String, To> errorFn) {
        return delegate -> from -> {
            Optional<String> errors = validate.apply(from);
            return errors.isPresent() ? errorFn.apply(from, errors.get()) : delegate.apply(from);
        };
    }
}
