package edu.gatech.seclass;

import edu.gatech.seclass.DefectClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class DefectClassTestSC3 {

    DefectClass dc = new DefectClass();
    // Test that achieves 100% statement coverage without revealing the fault
    @Test
    public void test1() {
        dc.defectMethod3(1, 2);
    }
    @Test
    public void test2() {
        dc.defectMethod3(-1, -1);
    }
}

