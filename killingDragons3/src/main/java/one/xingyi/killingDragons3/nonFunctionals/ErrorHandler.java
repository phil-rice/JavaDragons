package one.xingyi.killingDragons3.nonFunctionals;

import lombok.RequiredArgsConstructor;
import one.xingyi.killingDragons3.functions.DelegateFunction;

import java.util.function.Function;

@RequiredArgsConstructor
public class ErrorHandler<F, T> implements DelegateFunction<F, T> {
    final ErrorStrategy errorStrategy;


    @Override public Function<F, T> apply(Function<F, T> raw) {
        return from -> {
            try {
                return raw.apply(from);
            } catch (Exception e) {
                errorStrategy.handle(e);
                throw new RuntimeException("Error strategy " + errorStrategy + " broke contract, it should have thrown an exception");
            }
        };
    }
}
