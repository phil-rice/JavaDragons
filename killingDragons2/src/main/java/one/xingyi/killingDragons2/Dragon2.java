package one.xingyi.killingDragons2;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import one.xingyi.killingDragons2.functions.Function3;
import one.xingyi.killingDragons2.nonFunctionals.ErrorStrategy;
import one.xingyi.killingDragons2.nonFunctionals.PutMetrics;
import one.xingyi.killingDragons2.validators.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import static one.xingyi.killingDragons2.DragonDamageResult.result;
import static one.xingyi.killingDragons2.DragonMessages.*;
import static one.xingyi.killingDragons2.DragonNonFunctionals.nonFunctionals;
import static one.xingyi.killingDragons2.DragonValidators.*;
import static one.xingyi.killingDragons2.nonFunctionals.NonFunctionals.*;
import static one.xingyi.killingDragons2.validators.Validator.compose;
import static one.xingyi.killingDragons2.validators.Validator.validate;

interface DragonMessages {
    String weKilledADragon = "we killed a dragon!";
    String damaged = "damaged";
    String alreadyDead = "alreadyDead";
    String cannotDoNegativeDamage = "Cannot do negative damage";
}


interface DragonValidators {
    Validator<Integer, Dragon2> checkDragonIsntDead = (d, dragon) -> !dragon.alive ? Arrays.asList(alreadyDead) : Arrays.asList();
    Validator<Integer, Dragon2> checkCannotDoNegativeDamage = (d, dragon) -> d < 0 ? Arrays.asList(cannotDoNegativeDamage) : Arrays.asList();
    Function3<Integer, Dragon2, List<String>, DragonDamageResult> onValidateFail = (d, dragon, errors) -> new DragonDamageResult(dragon, Validator.errorsToString(errors));

}

interface DragonNonFunctionals {
    BiFunction<Integer, DragonDamageResult, String> metricNameFn = (oldD, newD) -> newD.description;
    BiFunction<Integer, DragonDamageResult, String> logMsgFn = (oldD, newD) -> "Hit dragon for " + oldD + " and did " + newD.description;
    final static Logger logger = LogManager.getLogger(Dragon2.class);

    PutMetrics metricsSystem = PutMetrics.atomicCounters();

    static Function<Integer, DragonDamageResult> nonFunctionals(Dragon2 dragon2, Function<Integer, DragonDamageResult> fn) {
        return errors(ErrorStrategy.justThrow,
                metrics(metricsSystem, metricNameFn,
                        log(logger, logMsgFn,
                                validate(compose(checkCannotDoNegativeDamage, checkDragonIsntDead), onValidateFail, dragon2, fn))));

    }
}

@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class Dragon2 {
    final int hitpoints;
    final boolean alive;

    final public static Dragon2 freshDragon = new Dragon2(1000, true);
    final static Dragon2 dead = new Dragon2(0, false);

    public Function<Integer, DragonDamageResult> damage() {
        return nonFunctionals(this, damage -> { //and should this even be in this class? Shouldn't we have the non functionals outside?
            int newHitpoints = hitpoints - damage;
            return newHitpoints > 0 ? result(newHitpoints, damaged) : result(dead, weKilledADragon);
        });
    }

    public static void main(String[] args) {
        System.out.println("Killing Dragons for Fun and Profit");

        Dragon2 d1 = Dragon2.freshDragon;
        DragonDamageResult d2 = d1.damage().apply(100);
        DragonDamageResult d3 = d2.dragon.damage().apply(100);
        DragonDamageResult d4 = d2.dragon.damage().apply(900);
        for (DragonDamageResult d : Arrays.asList(d2, d3, d4))
            System.out.println(d);
        System.out.println("Your dragon is " + (dead.alive ? "alive" : "dead"));
        System.out.println();
        System.out.println("Metrics are: ");
        System.out.println(DragonNonFunctionals.metricsSystem);

    }
}
