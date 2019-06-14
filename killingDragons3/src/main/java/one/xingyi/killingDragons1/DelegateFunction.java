package one.xingyi.killingDragons1;

import lombok.RequiredArgsConstructor;
import lombok.val;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;

interface DelegateFunction<From, To> extends Function<Function<From, To>, Function<From, To>> {

    static <From, To> DelegateFunction<From, To> compose(DelegateFunction<From, To>... others) {
        return new CompositeFunction<>(Arrays.asList(others));
    }

}

@RequiredArgsConstructor
class CompositeFunction<From, To> implements DelegateFunction<From, To> {

    final List<DelegateFunction<From, To>> fns;

    @Override
    public Function<From, To> apply(Function<From, To> delegate) {
        Function<From, To> result = delegate;
        for (DelegateFunction<From, To> fn : fns)
            result = fn.apply(result);
        return result;
    }
}
