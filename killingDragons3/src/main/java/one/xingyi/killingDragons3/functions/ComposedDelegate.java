package one.xingyi.killingDragons3.functions;

import lombok.val;

import java.util.List;
import java.util.function.Function;

public class ComposedDelegate<From, To> implements DelegateFunction<From, To> {
    public final String classes;
    final List<DelegateFunction<From, To>> delegates;

    ComposedDelegate(List<DelegateFunction<From, To>> delegates) {
        this.delegates = delegates;
        val builder = new StringBuilder();
        for (DelegateFunction<From, To> fn : delegates) {
            if (builder.length() > 0) builder.append(',');
            builder.append(fn.getClass().getSimpleName());
        }
        classes = builder.toString();
    }

    @Override public Function<From, To> apply(Function<From, To> raw) {
        Function<From, To> result = raw;
        for (DelegateFunction<From, To> fn : delegates)
            result = fn.apply(result);
        return result;
    }

}
