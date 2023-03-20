package edu.gatech.seclass;


/**
 * This is a Georgia Tech provided code example for use in assigned
 * private GT repositories. Students and other users of this template
 * code are advised not to share it with other students or to make it
 * available on publicly viewable websites including repositories such
 * as GitHub and GitLab.  Such sharing may be investigated as a GT
 * honor code violation. Created for CS6300 Spring 2023.
 *
 * Template provided for the White-Box Testing Assignment. Follow the
 * assignment directions to either implement or provide comments for
 * the appropriate methods.
 */

public class DefectClass {

    public static void exampleMethod1(int a) {
        // ...
        int x = a / 0; // Example of instruction that makes the method
                       // fail with an ArithmeticException
        // ...
    }

    public static int exampleMethod2(int a, int b) {
        // ...
        return (a + b) / 0; // Example of instruction that makes the
                            // method fail with an ArithmeticException
    }

    public static void exampleMethod3() {
        // NOT POSSIBLE: This method cannot be implemented because
        // <REPLACE WITH REASON> (this is the example format for a
        // method that is not possible) ***/
    }

    public static void defectMethod1(int a, int b) {
        //Task 1: Create in class DefectClass a method called defectMethod1 that contains a division by zero fault and at least two branches,
        // such that (1) it is possible to create a test suite that achieves less than 100% branch coverage and reveals the fault,
        // and (2) it is possible to create a test suite that achieves 100% branch coverage and does not reveal the fault.
        int calc;
        if (a > 0) {
            calc = b * a;
        }
        else{
            calc = b / a;
        }
    }

    public static void defectMethod2(int a, int b) {
        //Not possible because in order to achieve 100% branch coverage, both branches (the one with the fault and the one without) must be executed, and thus the fault will be revealed.
    }

    //Create in class DefectClass a method called defectMethod3 that contains a division by zero fault and at least two branches,
    // such that (1) every possible test suite that achieves 100% branch coverage does reveal the fault,
    // and (2) it is possible to create a test suite that achieves 100% statement coverage and does not reveal the fault.
    public static void defectMethod3(int a, int b) {
        int calc = 0;
        if (a >= 0) {
            calc = b / a;
        }
        else {
            calc = b * a;
        }
    }


    public static int defectMethod4(boolean a, int b, int c, int d) {
        int result = 0; 
        if (a) {
            result = 1; 
        } else { 
            if ((b == 0) || ((c > 0) && (d != 0))) { 
                result = 2; 
            } else { 
                result = 3; 
            } 
        } 
        return result; 
    }
    

    public static String[] defectMethod5() {
        String a[] = new String[7];
        /*
        public static boolean defectMethod5(boolean a, boolean b) {
          int m = 1;
          int n = -7;
          if(a)
            m = n; 
          else
            m = 3*m;
          if(b)
            n = 2-m;
          return ((100/(m-n))<= 0);
        }

        */
        //
        // Replace the "?" in column "output" with "T", "F", or "E":
        //
        //         | a | b |output|
        //         ================
        a[0] =  /* | T | T | <T, F, or E> (e.g., "T") */ "T";
        a[1] =  /* | T | F | <T, F, or E> (e.g., "T") */ "E";
        a[2] =  /* | F | T | <T, F, or E> (e.g., "T") */ "F";
        a[3] =  /* | F | F | <T, F, or E> (e.g., "T") */ "F";
        // ================
        //
        // Replace the "?" in the following sentences with "NEVER",
        // "SOMETIMES" or "ALWAYS":
        //
        a[4] = /* Test suites with 100% path coverage */ "ALWAYS";
               /*reveal the fault in this method.*/
        a[5] = /* Test suites with 100% branch coverage */ "SOMETIMES";
               /*reveal the fault in this method.*/
        a[6] =  /* Test suites with 100% statement coverage */ "SOMETIMES";
                /*reveal the fault in this method.*/
        // ================
        return a;
    }
}

