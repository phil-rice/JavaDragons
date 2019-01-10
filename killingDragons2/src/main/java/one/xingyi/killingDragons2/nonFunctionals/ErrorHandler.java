package one.xingyi.killingDragons2.nonFunctionals;
import java.util.function.Function;
 class ErrorHandler<F, T> implements Function<F, T> {
    final Function<F, T> delegate;
    final ErrorStrategy errorStrategy;

     ErrorHandler(ErrorStrategy errorStrategy, Function<F, T> delegate) {
        this.delegate = delegate;
        this.errorStrategy = errorStrategy;
    }
    @Override public T apply(F f) {
        try {
            return delegate.apply(f);
        } catch (Exception e) {
            errorStrategy.handle(e);
            throw new RuntimeException("Error strategy " + errorStrategy + " broke contract, it should have thrown an exception");
        }
    }
}
