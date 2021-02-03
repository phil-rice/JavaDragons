package one.xingyi.killingDragons3.functions;

import java.util.Arrays;
import java.util.function.Function;

public interface DelegateFunction<T1, T2> extends Function<Function<T1, T2>, Function<T1, T2>> {
    @Override Function<T1, T2> apply(Function<T1, T2> raw);

    static <From, To> ComposedDelegate<From, To> compose(DelegateFunction<From, To>... fns) { return new ComposedDelegate<>(Arrays.asList(fns)); }
}


