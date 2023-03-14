package edu.gatech.seclass;

import edu.gatech.seclass.DefectClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class DefectClassTestSC4 {
    DefectClass dc = new DefectClass();
    @Test
    public void test1() {
        dc.defectMethod4(true ,0, 0,0);
    }

    @Test
    public void test2() {
        dc.defectMethod4(false ,0, 1,0);
    }

    @Test
    public void test3() {
        dc.defectMethod4(false ,1, 0,1);
    }
}