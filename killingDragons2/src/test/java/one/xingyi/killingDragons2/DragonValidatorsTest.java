package one.xingyi.killingDragons2;
import one.xingyi.killingDragons2.functions.Function3;
import one.xingyi.killingDragons2.validators.Validator;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static one.xingyi.killingDragons2.DragonMessages.alreadyDead;
import static one.xingyi.killingDragons2.DragonMessages.cannotDoNegativeDamage;
import static one.xingyi.killingDragons2.DragonValidators.*;
import static org.junit.Assert.*;


public class DragonValidatorsTest {

    @Test
    public void testCheckDragonIsntDead() {
        assertEquals(Arrays.asList(alreadyDead), checkDragonIsntDead.apply(0, Dragon2.dead));
        assertEquals(Arrays.asList(), checkDragonIsntDead.apply(0, Dragon2.freshDragon));
    }

    @Test
    public void testCheckCannotDoNegativeDamage() {
        assertEquals(Arrays.asList(), checkCannotDoNegativeDamage.apply(1, Dragon2.freshDragon));
        assertEquals(Arrays.asList(), checkCannotDoNegativeDamage.apply(0, Dragon2.freshDragon));
        assertEquals(Arrays.asList(cannotDoNegativeDamage), checkCannotDoNegativeDamage.apply(-1, Dragon2.freshDragon));
        assertEquals(Arrays.asList(cannotDoNegativeDamage), checkCannotDoNegativeDamage.apply(-100, Dragon2.freshDragon));
    }

    List<String> messages1 = Arrays.asList("some message1");
    List<String> messages2 = Arrays.asList("some message1", "some message2");

    @Test
    public void testOnValidateFailReturnsDragonWithErrorsAsMessage() {
        Validator<Integer, Dragon2> validator1 = (d, dragon) -> messages1;
        Validator<Integer, Dragon2> validator2 = (d, dragon) -> messages2;

        assertEquals(new DragonDamageResult(Dragon2.freshDragon, "some message1"), onValidateFail.apply(100, Dragon2.freshDragon, messages1));
        assertEquals(new DragonDamageResult(Dragon2.freshDragon, "[some message1, some message2]"), onValidateFail.apply(100, Dragon2.freshDragon, messages2));
    }
}