package edu.gatech.seclass;

import edu.gatech.seclass.DefectClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class DefectClassTestBC1b {
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