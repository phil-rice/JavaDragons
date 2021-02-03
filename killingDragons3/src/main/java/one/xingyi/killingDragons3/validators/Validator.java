package one.xingyi.killingDragons3.validators;

import lombok.RequiredArgsConstructor;
import one.xingyi.killingDragons3.functions.DelegateFunction;
import one.xingyi.killingDragons3.functions.Function3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface Validator<T> extends Function<T, List<String>> {

    static String errorsToString(List<String> errors) {
        if (errors.size() == 1) return errors.get(0);
        else return errors.toString();
    }

    static <From, To> DelegateFunction<From, To> validate(Validator<From> validator, BiFunction<From, List<String>, To> onFail) {
        return new DelegateValidator<>(validator, onFail);
    }

    static <T> Validator<T> compose(Validator<T>... validators) {
        return new ComposedValidator<>(Arrays.asList(validators));
    }
}

@RequiredArgsConstructor
class DelegateValidator<From, To> implements DelegateFunction<From, To> {
    final Validator<From> validator;
    final BiFunction<From, List<String>, To> onFail;

    @Override public Function<From, To> apply(Function<From, To> raw) {
        return from -> {
            List<String> errors = validator.apply(from);
            return errors.size() == 0 ? raw.apply(from) : onFail.apply(from, errors);
        };
    }
}

@RequiredArgsConstructor
class ComposedValidator<T> implements Validator<T> {
    final List<Validator<T>> validators;

    @Override public List<String> apply(T from) {
        List<String> result = new ArrayList<>();
        for (Validator<T> v : validators)
            result.addAll(v.apply(from));
        return result;
    }
}