package one.xingyi.killingDragons2.validators;
import one.xingyi.killingDragons2.functions.Function3;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
public interface Validator<T1, T2> extends BiFunction<T1, T2, List<String>> {

    static String errorsToString(List<String> errors) {
        if (errors.size() == 1) return errors.get(0);
        else return errors.toString();
    }

    static <T1, T2, T> Function<T1, T> validate(Validator<T1, T2> validator, Function3<T1, T2, List<String>, T> onFail, T2 ref, Function<T1, T> fn) {
        return from -> {
            List<String> errors = validator.apply(from, ref);
            return errors.size() == 0 ? fn.apply(from) : onFail.apply(from, ref, errors);
        };
    }

    static <T1, T2> Validator<T1, T2> compose(Validator<T1, T2>... validators) {
        return (from, ref) -> {
            List<String> result = new ArrayList<>();
            for (Validator<T1, T2> v : validators)
                result.addAll(v.apply(from, ref));
            return result;
        };
    }
}
