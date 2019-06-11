package one.xingyi.killingDragons2.nonFunctionals;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;
@RequiredArgsConstructor
 class ErrorHandler<F, T> implements Function<F, T> {
    final ErrorStrategy errorStrategy;
    final Function<F, T> delegate;

    @Override public T apply(F f) {
        try {
            return delegate.apply(f);
        } catch (Exception e) {
            errorStrategy.handle(e);
            throw new RuntimeException("Error strategy " + errorStrategy + " broke contract, it should have thrown an exception");
        }
    }
}
