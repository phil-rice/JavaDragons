package one.xingyi.killingDragons3;

import one.xingyi.killingDragons3.validators.Validator;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static one.xingyi.killingDragons3.DragonMessages.alreadyDead;
import static one.xingyi.killingDragons3.DragonMessages.cannotDoNegativeDamage;
import static one.xingyi.killingDragons3.DragonValidators.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class DragonValidatorsTest {

    @Test
    public void testCheckDragonIsntDead() {
        assertEquals(Arrays.asList(alreadyDead), checkDragonIsntDead.apply(new Attack(0, Dragon3.dead)));
        assertEquals(Arrays.asList(), checkDragonIsntDead.apply(new Attack(0, Dragon3.freshDragon)));
    }

    @Test
    public void testCheckCannotDoNegativeDamage() {
        assertEquals(Arrays.asList(), checkCannotDoNegativeDamage.apply(new Attack(1, Dragon3.freshDragon)));
        assertEquals(Arrays.asList(), checkCannotDoNegativeDamage.apply(new Attack(0, Dragon3.freshDragon)));
        assertEquals(Arrays.asList(cannotDoNegativeDamage), checkCannotDoNegativeDamage.apply(new Attack(-1, Dragon3.freshDragon)));
        assertEquals(Arrays.asList(cannotDoNegativeDamage), checkCannotDoNegativeDamage.apply(new Attack(-100, Dragon3.freshDragon)));
    }

    List<String> messages1 = Arrays.asList("some message1");
    List<String> messages2 = Arrays.asList("some message1", "some message2");

    @Test
    public void testOnValidateFailReturnsDragonWithErrorsAsMessage() {
        Validator<Attack> validator1 = (a) -> messages1;
        Validator<Attack> validator2 = (a) -> messages2;

        assertEquals(new DragonDamageResult(Dragon3.freshDragon, "some message1"), onValidateFail.apply(new Attack(100, Dragon3.freshDragon), messages1));
        assertEquals(new DragonDamageResult(Dragon3.freshDragon, "[some message1, some message2]"), onValidateFail.apply(new Attack(100, Dragon3.freshDragon), messages2));
    }
}