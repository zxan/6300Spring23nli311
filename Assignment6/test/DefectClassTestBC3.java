import edu.gatech.seclass.DefectClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DefectClassTestBC3 {

    DefectClass dc = new DefectClass();
    // Test that reveals the fault with 100% branch coverage
    @Test
    public void test1() {
        dc.defectMethod3(0, 2);
    }
    @Test
    public void test2() {
        dc.defectMethod3(-1, -1);
    }
}
