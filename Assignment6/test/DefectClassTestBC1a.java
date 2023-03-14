import edu.gatech.seclass.DefectClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class DefectClassTestBC1a {
    @Test
    public void test1() {
        //DefectClassTestBC1a should achieve less than 100% branch coverage of defectMethod1 and reveal the fault therein.
        DefectClass dc = new DefectClass();
        dc.defectMethod1(0, 5);
    }
}