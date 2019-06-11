package one.xingyi.killingDragons2;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Objects;
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class DragonDamageResult {
    final Dragon2 dragon;
    final String description;

    public static DragonDamageResult result(Dragon2 dragon, String description){
        return new DragonDamageResult(dragon, description);
    }
    public static DragonDamageResult result(int hitPoints, String description){
        return new DragonDamageResult(new Dragon2(hitPoints, true), description);
    }
}
