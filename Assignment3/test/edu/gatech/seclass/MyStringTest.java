package edu.gatech.seclass;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Junit test class created for use in Georgia Tech CS6300.
 * <p>
 * This class is provided to interpret your grades via junit tests
 * and as a reminder, should NOT be posted in any public repositories,
 * even after the class has ended.
 */

public class MyStringTest {

    private MyStringInterface mystring;

    @BeforeEach
    public void setUp() {
        mystring = new MyString();
    }

    @BeforeEach
    public void tearDown() {
        mystring = null;
    }

    @Test
    @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
    // Description: First count number example in the interface documentation
    public void testCountAlphabeticWords1() {
        mystring.setString("My numbers are 11, 96, and thirteen");
        assertEquals(5, mystring.countAlphabeticWords());
    }

    @Test
    @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
    // Description: Tests if my string is null and should generate null pointer exception
    public void testCountAlphabeticWords2() {
        mystring = null;
        assertThrows(NullPointerException.class, () -> {
            mystring.countAlphabeticWords();
        });
    }

    @Test
    @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
    // Description: Tests if string is empty and should return Illegal Argument Exception
    public void testCountAlphabeticWords3() {
        assertThrows(IllegalArgumentException.class, () -> {
            mystring.setString("");
            mystring.countAlphabeticWords();
        });
    }

    @Test
    @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
    // Description: Testing if non-alpha numeric characters are counted as spaces
    public void testCountAlphabeticWords4() {
        mystring.setString("i#love 2 pr00gram.");
        assertEquals(4, mystring.countAlphabeticWords());
    }

    @Test
    @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
    // Description: Testing Easteregg input and should expect Illegal Argument
    public void testSetString1() {
        assertThrows(IllegalArgumentException.class, () -> {
            mystring.setString("Copyright GA Tech. All rights reserved.");
        });
    }

    @Test
    @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
    // Description: Sample encryption 1
    public void testEncrypt1() {
        mystring.setString("Cat & 5 DogS");
        assertEquals("tdK & O ylHL", mystring.encrypt(5, 3));
    }

    @Test
    @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
    // Description: Testing if empty string is illegal argument exception
    public void testEncrypt2() {
        assertThrows(IllegalArgumentException.class, () -> {
            mystring.setString("");
            mystring.encrypt(1,2);
        });
    }

    @Test
    @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
    // Description: Testing if arg1 is a negative number
    public void testEncrypt3() {
        assertThrows(IllegalArgumentException.class, () -> {
            mystring.setString("Arg1 is negative");
            mystring.encrypt(-1,2);
        });
    }

    @Test
    @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
    // Description: Testing if arg2 is above 62 number
    public void testEncrypt4() {
        assertThrows(IllegalArgumentException.class, () -> {
            mystring.setString("Arg2 is 64");
            mystring.encrypt(1,64);
        });
    }

    @Test
    @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
    // Description: Arg2 is zero. Should throw IllegalArgumentException
    public void testEncrypt5() {
        assertThrows(IllegalArgumentException.class, () -> {
            mystring.setString("Arg2 is zero");
            mystring.encrypt(0,0);
        });
    }

    @Test
    @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
    // Description: Arg1 = 1 and Arg2 = 62 should give a non encrypted message
    public void testEncrypt6() {
        mystring.setString("This is a test encryption");
        assertEquals("This is a test encryption", mystring.encrypt(1, 62));
    }

    @Test
    @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
    // Description: First convert digits example in the interface documentation
    public void testConvertDigitsToNamesInSubstring1() {
        mystring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        mystring.convertDigitsToNamesInSubstring(17, 23);
        assertEquals("I'd b3tt3r put sZerome dOneSix1ts in this 5tr1n6, right?", mystring.getString());
    }

    @Test
    @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
    // Description: Null Pointer Exception generated when string is null and return Null exception
    public void testConvertDigitsToNamesInSubstring2() {
        mystring = null;
        assertThrows(NullPointerException.class, () -> {
            mystring.convertDigitsToNamesInSubstring(1, 2);
        });
    }

    @Test
    @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
    // Description: Illegal Argument Exception generated when first position is greater than final position
    public void testConvertDigitsToNamesInSubstring3() {
        mystring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        assertThrows(IllegalArgumentException.class, () -> {
            mystring.convertDigitsToNamesInSubstring(23, 17);
        });
    }

    @Test
    @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
    // Description: My Index Out Of Bounds Exception generated when final position is greater than the length of the string
    public void testConvertDigitsToNamesInSubstring4() {
        mystring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        assertThrows(MyIndexOutOfBoundsException.class, () -> {
            mystring.convertDigitsToNamesInSubstring(23, 60);
        });
    }

    @Test
    @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
    // Description: Tests if there are no numbers between first and final positions
    public void testConvertDigitsToNamesInSubstring5() {
        mystring.setString("2abcdef1");
        mystring.convertDigitsToNamesInSubstring(2, 7);
        assertEquals("2abcdef1", mystring.getString());
    }
}
