package one.xingyi.killingDragons3;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class DragonDamageResult {
    final Dragon3 dragon;
    final String description;

    public static DragonDamageResult aliveWith(Dragon3 dragon, String description){
        return new DragonDamageResult(dragon, description);
    }
    public static DragonDamageResult aliveWith(int hitPoints, String description){
        return new DragonDamageResult(new Dragon3(hitPoints, true), description);
    }
}
