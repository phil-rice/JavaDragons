package one.xingyi.killingDragons2.nonFunctionals;

public interface ErrorStrategy {

    void handle(Exception e);

    static ErrorStrategy justThrow = new JustThrowErrorStrategy();
}

class JustThrowErrorStrategy implements ErrorStrategy {

    @Override public void handle(Exception e) {
        if (e instanceof RuntimeException)
            throw ((RuntimeException) e);
        throw new RuntimeException(e);
    }
}
