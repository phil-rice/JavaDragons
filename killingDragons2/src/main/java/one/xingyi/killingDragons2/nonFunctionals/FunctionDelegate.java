package one.xingyi.killingDragons2.nonFunctionals;
import java.util.function.Function;
public interface FunctionDelegate<F, T> extends Function<Function<F, T>, Function<F, T>> {
}
