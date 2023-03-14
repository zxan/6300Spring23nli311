import edu.gatech.seclass.DefectClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class DefectClassTestBC1b {
    //DefectClassTestBC1b should achieve 100% branch coverage of defectMethod1 and not reveal the fault therein.
    DefectClass dc = new DefectClass();
    @Test
    public void test1() {
        dc.defectMethod1(5, 5);

    }
    @Test
    public void test2() {
        dc.defectMethod1(-5, 5);

    }
}