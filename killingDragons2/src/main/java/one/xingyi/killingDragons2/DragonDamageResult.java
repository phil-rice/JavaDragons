package one.xingyi.killingDragons2;
import java.util.Objects;
public class DragonDamageResult {
    final Dragon2 dragon;
    final String description;

    public static DragonDamageResult result(Dragon2 dragon, String description){
        return new DragonDamageResult(dragon, description);
    }
    public static DragonDamageResult result(int hitPoints, String description){
        return new DragonDamageResult(new Dragon2(hitPoints, true), description);
    }

    public DragonDamageResult(Dragon2 dragon, String description) {
        this.dragon = dragon;
        this.description = description;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DragonDamageResult that = (DragonDamageResult) o;
        return Objects.equals(dragon, that.dragon) && Objects.equals(description, that.description);
    }
    @Override public int hashCode() { return Objects.hash(dragon, description); }
    @Override public String toString() { return "DragonDamageResult{dragon=" + dragon + ", description='" + description + '\'' + '}'; }
}
