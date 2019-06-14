package one.xingyi.killingDragons1;

import lombok.val;

import java.util.Optional;
import java.util.function.Function;

interface Validate<From> extends Function<From, Optional<String>> {
    static <From> Function<From, Optional<String>> compose(Function<From, Optional<String>>... fns) {
        return from -> {
            for (Function<From, Optional<String>> fn : fns) {
                val result = fn.apply(from);
                if (result.isPresent()) return result;
            }
            return Optional.empty();
        };
    }

    static <From> Validate<From> validate(String message, Function<From, Boolean> test) {
        return from -> test.apply(from) ? Optional.empty() : Optional.of(message);
    }
}
