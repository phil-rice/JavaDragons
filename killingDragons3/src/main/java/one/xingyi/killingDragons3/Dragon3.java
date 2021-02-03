package one.xingyi.killingDragons3;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import one.xingyi.killingDragons3.functions.ComposedDelegate;
import one.xingyi.killingDragons3.functions.DelegateFunction;
import one.xingyi.killingDragons3.nonFunctionals.*;
import one.xingyi.killingDragons3.validators.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import static one.xingyi.killingDragons3.DragonDamageResult.aliveWith;
import static one.xingyi.killingDragons3.DragonMessages.*;
import static one.xingyi.killingDragons3.DragonValidators.*;


interface DragonMessages {
    String weKilledADragon = "we killed a dragon!";
    String damaged = "damaged";
    String alreadyDead = "alreadyDead";
    String cannotDoNegativeDamage = "Cannot do negative damage";
}


interface DragonValidators {
    Validator<Attack> checkDragonIsntDead = attack -> !attack.dragon3.alive ? Collections.singletonList(alreadyDead) : Collections.emptyList();
    Validator<Attack> checkCannotDoNegativeDamage = attack -> attack.damage < 0 ? Collections.singletonList(cannotDoNegativeDamage) : Collections.emptyList();
    BiFunction<Attack, List<String>, DragonDamageResult> onValidateFail = (attack, errors) -> new DragonDamageResult(attack.dragon3, Validator.errorsToString(errors));

}

@RequiredArgsConstructor @EqualsAndHashCode @ToString
class Attack {
    final Integer damage;
    final Dragon3 dragon3;
}

interface DragonNonFunctionals {
    BiFunction<Attack, DragonDamageResult, String> metricNameFn = (attack, newD) -> newD.description;
    BiFunction<Attack, DragonDamageResult, String> logMsgFn = (attack, newD) -> "Hit dragon for " + attack.damage + " and did " + newD.description;
    final static Logger logger = LogManager.getLogger(Dragon3.class);

    PutMetrics metricsSystem = PutMetrics.atomicCounters();

    Validator<Attack> dragonValidator = Validator.compose(checkCannotDoNegativeDamage, checkDragonIsntDead);

    static ComposedDelegate<Attack, DragonDamageResult> nonFunctionals = DelegateFunction.compose(
            new ErrorHandler<>(ErrorStrategy.justThrow),
            new Metrics<>(metricsSystem, metricNameFn),
            new LoggerDelegate<>(logger, logMsgFn),
            Validator.validate(dragonValidator, onValidateFail)
    );

}

@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class Dragon3 {
    final int hitpoints;
    final boolean alive;

    final public static Dragon3 freshDragon = new Dragon3(1000, true);
    final static Dragon3 dead = new Dragon3(0, false);

    public static Function<Attack, DragonDamageResult> resolveAttack = attack -> {
        int newHitpoints = attack.dragon3.hitpoints - attack.damage;
        return newHitpoints > 0 ? DragonDamageResult.aliveWith(newHitpoints, damaged) : aliveWith(dead, weKilledADragon);
    };

    public static Function<Attack, DragonDamageResult> resolveAttackWithNonFunctionals = DragonNonFunctionals.nonFunctionals.apply(resolveAttack);

    //this is the scaffolding to keep the signatures the same. But in general we will delete this method as we improve the rest of the code
    public Dragon3 damage(int damage) { return resolveAttackWithNonFunctionals.apply(new Attack(damage, this)).dragon; }


    public static void main(String[] args) {
        System.out.println("Killing Dragons for Fun and Profit");

        Dragon3 d1 = Dragon3.freshDragon;
        Dragon3 d2 = d1.damage(100);
        Dragon3 d3 = d2.damage(100);
        Dragon3 d4 = d3.damage(900);
        for (Dragon3 d : Arrays.asList(d2, d3, d4))
            System.out.println(d);
        System.out.println();
        System.out.println("Metrics are: ");
        System.out.println(DragonNonFunctionals.metricsSystem);
    }
}
