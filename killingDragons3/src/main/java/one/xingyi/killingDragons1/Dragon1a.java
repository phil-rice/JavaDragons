package one.xingyi.killingDragons1;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@Getter
class DragonAndResult {
    final Dragon1a dragon;
    final String result;
}

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
class Attack {
    final Dragon1a dragon;
    final int damage;

    public DragonAndResult damageWithResult() {
        int newHitpoints = dragon.hitpoints - damage;
        return newHitpoints <= 0 ?
                new DragonAndResult(Dragon1a.deadDragon, "dragon.damageresult.killed") :
                new DragonAndResult(dragon.withHitpoints(newHitpoints), "dragon.damageresult.damaged");
    }

}

interface MyLogger {
    void info(String message);

    void error(String message, Throwable e);

    static MyLogger fromLogger(Logger logger) {
        return new FromLogger(logger);
    }

    static MyLogger nullLogger = new MyLogger() {
        @Override
        public void info(String message) {
        }

        @Override
        public void error(String message, Throwable e) {
        }
    };

    @RequiredArgsConstructor
    class FromLogger implements MyLogger {
        final Logger logger;

        @Override
        public void info(String message) {
            logger.info(message);
        }

        @Override
        public void error(String message, Throwable e) {
            logger.error(message, e);
        }
    }
}


@RequiredArgsConstructor
class DragonNonFunctionals {
    final MyLogger logger;

    PutMetrics putMetrics = PutMetrics.atomicCounters();

    DelegateFunction<Attack, DragonAndResult> logForDragon =
            NonFunctionals.log(logger, DragonAndResult::getResult, (a, dr) -> new Object[]{a.damage, dr.dragon.hitpoints});

    Validate<Attack> checkAlive = Validate.validate("dragon.validate.alreadydead", a -> a.dragon.alive);
    Validate<Attack> checkPostiveDamage = Validate.validate("dragon.validate.negativedamage", a -> a.damage > 0);
    DelegateFunction<Attack, DragonAndResult> validations = NonFunctionals.validateAndThen(
            Validate.compose(checkAlive, checkPostiveDamage),
            (a, error) -> new DragonAndResult(a.dragon, error));

    DelegateFunction<Attack, DragonAndResult> attackMetrics = NonFunctionals.metrics(putMetrics, (a, d) -> d.result);

    BiFunction<Attack, RuntimeException, RuntimeException> errorStrategy =
            NonFunctionals.logAndThrow(logger, "dragon.damage.unexpected", a -> new Object[]{a.dragon, a.damage});

    DelegateFunction<Attack, DragonAndResult> handleErrorsForAttack = NonFunctionals.handleErrors(errorStrategy);



    DelegateFunction<Attack, DragonAndResult> nonFunctionals = DelegateFunction.compose(
            handleErrorsForAttack,
//            profileMe,
            attackMetrics,
            logForDragon,
            validations);

    Function<Attack, DragonAndResult> lift(Function<Attack, DragonAndResult> delegate) {
        return nonFunctionals.apply(delegate);
    }

}

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class Dragon1a {

    final int hitpoints;
    final boolean alive;

    public Dragon1a withHitpoints(int hitpoints) { // this can be autogenerated by a library like lombok
        return new Dragon1a(hitpoints, alive);
    }

    public static Dragon1a freshDragon = new Dragon1a(1000, true);
    public static Dragon1a deadDragon = new Dragon1a(0, false);

    //Scaffolding to keep old code working
    static DragonNonFunctionals dragonNonFunctionals = new DragonNonFunctionals(MyLogger.fromLogger(Logger.getLogger(Dragon1a.class)));
    static Function<Attack, DragonAndResult> damageFn = dragonNonFunctionals.lift(Attack::damageWithResult);


    public Dragon1a damage(int damage) {
        return damageFn.apply(new Attack(this, damage)).dragon;
    }

    public static void main(String[] args) {
        System.out.println("Killing Dragons for Fun and Profit");

        Dragon1a d1 = Dragon1a.freshDragon;
        Dragon1a d2 = d1.damage(100);
        Dragon1a d3 = d2.damage(100);
        Dragon1a d4 = d2.damage(900);
        for (Dragon1a d : Arrays.asList(d1, d2, d3, d4))
            System.out.println(d);
        System.out.println("Your dragon is " + (d4.alive ? "alive" : "dead"));
        System.out.println();
        System.out.println("Metrics are: ");
        System.out.println(Dragon1a.dragonNonFunctionals.putMetrics);
    }
}
