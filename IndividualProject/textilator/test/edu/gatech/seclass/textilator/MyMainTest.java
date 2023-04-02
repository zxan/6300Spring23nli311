package edu.gatech.seclass.textilator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class MyMainTest {
    // Place all  of your tests in this class, optionally using MainTest.java as an example
    private final String usageStr =
            "Usage: textilator [ -s number | -x substring | -c case | -e num | -a | -p prefix ] FILE"
                    + System.lineSeparator();

    @TempDir
    Path tempDirectory;

    @RegisterExtension
    OutputCapture capture = new OutputCapture();

    /*
     * Test Utilities
     */

    private Path createFile(String contents) {
        return createFile(contents, "sample.txt");
    }

    private Path createFile(String contents, String fileName) {
        Path file = tempDirectory.resolve(fileName);

        try {
            Files.write(file, contents.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return file;
    }

    private String getFileContent(Path file) {
        try {
            return Files.readString(file, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
     * Test Cases
     */

    @Test
    // Frame #: 1
    public void textilatorTest1() {
        String input = "";

        Path inputFile = createFile(input);
        String[] args = {inputFile.toString()};
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 2
    public void textilatorTest2() {
        String input = "Newline not set";

        Path inputFile = createFile(input);
        String[] args = {inputFile.toString()};
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 3
    public void textilatorTest3() {
        String input = "Option -s and -x selected" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {inputFile.toString()};
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 4
    public void textilatorTest4() {
        String input = "-x is not the last occurrence" + System.lineSeparator();
        String expected = "x is not the last occurrence" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-x", "O", "-x", "-", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 5
    public void textilatorTest5() {
        String input = "-s is not the last occurrence" + System.lineSeparator();
        String expected = "" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s", "1", "-s", "0", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 6
    public void textilatorTest6() {
        String input = "Number of parameters for the last occurrence of -x is more than one" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-x", "Number", "great", inputFile.toString()};
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }


    @Test
    // Frame #: 7
    public void textilatorTest7() {
        String input = "Number of parameters for -s is more than one" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s", "0","1", inputFile.toString()};
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 8
    public void textilatorTest8() {
        String input = "-s parameters type is not a number" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s", "zero", inputFile.toString()};
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 9
    public void textilatorTest9() {
        String input = "-s invalid parameter" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s" , "26", inputFile.toString()};
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 10
    public void textilatorTest10() {
        String input = "Test 10 -x" + System.lineSeparator()
                + "Second line" + System.lineSeparator();
        String expected = "prefix" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-x", "", "-c", "upper", "-e", "0", "-p", "prefix", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 11
    public void textilatorTest11() {
        String input = "-c occurrenced once :  False" + System.lineSeparator();
        String expected = "-C OCCURRENCED ONCE :  FALSE" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-c" , "lower","-c","upper", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 12
    public void textilatorTest12() {
        String input = "number of -c parameters is more than one" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-c", "lower", "upper", inputFile.toString()};
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 13
    public void textilatorTest13() {
        String input = "-c is parameter is not upper or lower" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-c", "neither", inputFile.toString()};
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 14
    public void textilatorTest14() {
        String input = "both -e and -a selected" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-e", "22", "-a", inputFile.toString()};
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 15
    public void textilatorTest15() {
        String input = "-e is the not the last occurrence" + System.lineSeparator();
        String expected = "-e is the not the last occurrence" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-e", "24", "-e", "0", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 16
    public void textilatorTest16() {
        String input = "-a is the not the last." + System.lineSeparator();
        String expected = "97 32 105 115 32 116 104 101 32 110 111 116 32 116 104 101 32 108 97 115 116 46 " + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-a", "-a", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 17
    public void textilatorTest17() {
        String input = "-e has more than one parameter" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-e", "1", "0", inputFile.toString()};
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 18
    public void textilatorTest18() {
        String input = "-a's parameter count is zero" + System.lineSeparator();
        String expected = "45 97 39 115 32 112 97 114 97 109 101 116 101 114 32 99 111 117 110 116 32 105 115 32 122 101 114 111 " + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-a", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 19
    public void textilatorTest19() {
        String input = "-a's parameter count is not zero" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-a", "1", inputFile.toString()};
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 20
    public void textilatorTest20() {
        String input = "-e's parameter count isn't an integer" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-e", "one", inputFile.toString()};
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 21
    public void textilatorTest21() {
        String input = "-e's parameter is not integer between -25 and 25" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-e", "26", inputFile.toString()};
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 22
    public void textilatorTest22() {
        String input = "-p parameter occurrenced more than once" + System.lineSeparator();
        String expected = "e-p parameter occurrenced more than once" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-p", "3", "-p" , "e", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 23
    public void textilatorTest23() {
        String input = "-p parameter has multiple parameters" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-p", "3" , "e", inputFile.toString()};
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 24
    public void textilatorTest24() {
        String input = "-p parameter is an empty string" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-p", "", inputFile.toString()};
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 25
    public void textilatorTest25() {
        String input = "TEST 25 -x, -c, -e -p" + System.lineSeparator()
                + "second line" + System.lineSeparator();
        String expected = "Case TFDPOE MLOF" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-x", "TEST", "-c", "upper", "-e", "1", "-p", "Case ", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 26
    public void textilatorTest26() {
        String input = "Test 26 -x, -c, -e" + System.lineSeparator()
                + "Second line" + System.lineSeparator();
        String expected = "SECOND LINE" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-x", "-", "-c", "upper", "-e", "0", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 27
    public void textilatorTest27() {
        String input = "Test 27 -x, -c, -a, -p" + System.lineSeparator()
                + "Second line" + System.lineSeparator();
        String expected = "Case83 69 67 79 78 68 32 76 73 78 69 " + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-x", "-", "-c", "upper", "-a", "-p", "Case", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 28
    public void textilatorTest28() {
        String input = "Test 28 -x, -c, -a" + System.lineSeparator()
                + "Second line" + System.lineSeparator();
        String expected = "83 69 67 79 78 68 32 76 73 78 69 " + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-x", "-", "-c", "upper", "-a", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 29
    public void textilatorTest29() {
        String input = "Test 29 -x, -c, -p" + System.lineSeparator()
                + "Second line" + System.lineSeparator();
        String expected = "Casesecond line" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-x", "-", "-c", "lower", "-p", "Case", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 30
    public void textilatorTest30() {
        String input = "Test 30 -x, -c" + System.lineSeparator()
                + "Second line" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-x", "-","-c", "lower", inputFile.toString()};
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 31
    public void textilatorTest31() {
        String input = "Test 31 -x, -e, -p" + System.lineSeparator()
                + "Second line" + System.lineSeparator();
        String expected = "prefixRdbnmc khmd" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-x", "-","-e", "-1", "-p", "prefix", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 32
    public void textilatorTest32() {
        String input = "Test 32 -x, -e" + System.lineSeparator()
                + "Second line" + System.lineSeparator();
        String expected = "Tfdpoe mjof" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-x", "-","-e","1", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 33
    public void textilatorTest33() {
        String input = "Test 33 -x, -a, -p" + System.lineSeparator()
                + "Second line" + System.lineSeparator();
        String expected = "prefix83 101 99 111 110 100 32 108 105 110 101 " + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-x", "-","-a","-p","prefix", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 34
    public void textilatorTest34() {
        String input = "Test 34 -x, -a" + System.lineSeparator()
                + "Second line" + System.lineSeparator();
        String expected = "83 101 99 111 110 100 32 108 105 110 101 " + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-x", "-","-a", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 35
    public void textilatorTest35() {
        String input = "Test 35 -x, -p" + System.lineSeparator()
                + "Second line" + System.lineSeparator();
        String expected = "prefixSecond line" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-x", "-","-p","prefix", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 36
    public void textilatorTest36() {
        String input = "Test 36 -x" + System.lineSeparator()
                + "Second line" + System.lineSeparator();
        String expected = "Second line" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-x", "-", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 37
    public void textilatorTest37() {
        String input = "Test 37 -s, -c, -e, p" + System.lineSeparator()
                + "Second line" + System.lineSeparator();
        String expected = "prefixsecond line" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s", "1","-c", "lower", "-e", "0", "-p", "prefix", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 38
    public void textilatorTest38() {
        String input = "Test 38 -s, -c, -e" + System.lineSeparator()
                + "Second line" + System.lineSeparator();
        String expected = "tfdpoe mjod" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s", "1","-c", "lower", "-e", "1", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 39
    public void textilatorTest39() {
        String input = "Test 39 -s, -c, -a, -p" + System.lineSeparator()
                + "Second line" + System.lineSeparator();
        String expected = "prefix 115 101 99 111 110 100 32 108 105 110 101 " + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s", "1","-c", "lower", "-a","-p", "prefix ", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 40
    public void textilatorTest40() {
        String input = "Test 40 -s, -c, -a" + System.lineSeparator()
                + "Second line" + System.lineSeparator();
        String expected = "115 101 99 111 110 100 32 108 105 110 101 " + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s", "1","-c", "lower", "-a", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 41
    public void textilatorTest41() {
        String input = "Test 41 -s, -c, -p" + System.lineSeparator()
                + "Second line" + System.lineSeparator()
                + "Third Line" + System.lineSeparator()
                + "Fourth Line" + System.lineSeparator();
        String expected = "Test Second line"
                + "Test Fourth line" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s", "1","-c", "lower", "-p", "Test ", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 42
    public void textilatorTest42() {
        String input = "Test 42 -s, -c" + System.lineSeparator()
                + "Second line" + System.lineSeparator()
                + "Third Line" + System.lineSeparator()
                + "Fourth Line" + System.lineSeparator();
        String expected = "SECOND LINE"
                + "FOURTH LINE" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s", "1","-c", "UPPER", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 43
    public void textilatorTest43() {
        String input = "Test 43 -s, -e, -p" + System.lineSeparator()
                + "Second line" + System.lineSeparator()
                + "Third Line" + System.lineSeparator()
                + "Fourth Line" + System.lineSeparator();
        String expected = "Prefix Second Line"
                + "Prefix Fourth Line" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s", "1","-e","0", "-p","Prefix ",  inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 44
    public void textilatorTest44() {
        String input = "Test 44 -s, -e, -p" + System.lineSeparator()
                + "Second line" + System.lineSeparator()
                + "Third Line" + System.lineSeparator()
                + "Fourth Line" + System.lineSeparator();
        String expected = "Rdbnmc Kjmd"
                + "Entqsg Kjmd" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s", "1","-e","-1",  inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 45
    public void textilatorTest45() {
        String input = "Test 45 -s, -a, -p" + System.lineSeparator()
                + "Second line" + System.lineSeparator()
                + "Third Line" + System.lineSeparator()
                + "Fourth Line" + System.lineSeparator();
        String expected = "prefix 83 101 99 111 110 100 32 76 105 110 101 "
                + "prefix 70 111 117 114 116 104 32 76 105 110 101 " + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s", "1","-a","-p", "prefix ",  inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 46
    public void textilatorTest46() {
        String input = "Test 46 -s, -a" + System.lineSeparator()
                + "Second line" + System.lineSeparator()
                + "Third Line" + System.lineSeparator()
                + "Fourth Line" + System.lineSeparator();
        String expected = "83 101 99 111 110 100 32 76 105 110 101 "
                + "70 111 117 114 116 104 32 076 105 110 101 " + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s", "1","-a",  inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 47
    public void textilatorTest47() {
        String input = "Test 47 -s, -p" + System.lineSeparator()
                + "Second line" + System.lineSeparator()
                + "Third Line" + System.lineSeparator()
                + "Fourth Line" + System.lineSeparator();
        String expected = "String Second line"
                + "String Fourth Line" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s", "1","-p","String ",  inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 48
    public void textilatorTest48() {
        String input = "Test 48 -s" + System.lineSeparator()
                + "Second line" + System.lineSeparator()
                + "Third Line" + System.lineSeparator()
                + "Fourth Line" + System.lineSeparator();
        String expected = "Test 48 -s"
                + "Third Line" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s", "0",  inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 49
    public void textilatorTest49() {
        String input = "Test 49 -c, -e, -p" + System.lineSeparator()
                + "Second line" + System.lineSeparator()
                + "Third Line" + System.lineSeparator()
                + "Fourth Line" + System.lineSeparator();
        String expected = "* UFTU 49 -D, -F, -Q"
                + "* UIJSE MJOF" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s", "0", "-c", "upper", "-e", "1", "-p", "* ",  inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 50
    public void textilatorTest50() {
        String input = "Test 50 -c, -e" + System.lineSeparator()
                + "Second line" + System.lineSeparator()
                + "Third Line" + System.lineSeparator()
                + "Fourth Line" + System.lineSeparator();
        String expected = "UFTU 50 -D, -F"
                + "TFDPOE MJOF" + System.lineSeparator()
                + "UIJSE MJOF" + System.lineSeparator()
                + "GPVSUI MJOF" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-c", "upper", "-e", "1", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 51
    public void textilatorTest51() {
        String input = "Test 51 -c, -p" + System.lineSeparator()
                + "Second line" + System.lineSeparator()
                + "Third Line" + System.lineSeparator()
                + "Fourth Line" + System.lineSeparator();
        String expected = "1 116 101 115 116 32 53 50 32 45 99 44 32 45 112"
                + "1 115 101 99 111 110 100 32 108 105 110 101" + System.lineSeparator()
        + "1 116 104 105 114 100 32 108 105 110 101" + System.lineSeparator()
        + "1 102 111 117 114 116 104 32 108 105 110 101" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-c", "lower", "-a", "-p", "1 ", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 52
    public void textilatorTest52() {
        String input = "Test 52 -c, -a" + System.lineSeparator()
                + "Second line" + System.lineSeparator()
                + "Third Line" + System.lineSeparator()
                + "Fourth Line" + System.lineSeparator();
        String expected = "116 101 115 116 32 53 051 32 45 99 44 32 45 97 "
                + "115 101 99 111 110 100 32 108 105 110 101 " + System.lineSeparator()
        + "116 104 105 114 100 32 108 105 110 101 " + System.lineSeparator()
        + "102 111 117 114 116 104 32 108 105 110 101 " + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-c", "lower", "-a", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 53
    public void textilatorTest53() {
        String input = "Test 53 -c, -p" + System.lineSeparator()
                + "Second line" + System.lineSeparator()
                + "Third Line" + System.lineSeparator()
                + "Fourth Line" + System.lineSeparator();
        String expected = "- Test 53 -c, -p" + System.lineSeparator()
                + "- Second line" + System.lineSeparator()
                + "- Third Line" + System.lineSeparator()
                + "- Fourth Line" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-c", "lower", "-p", "- ", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 54
    public void textilatorTest54() {
        String input = "Test 54 -c" + System.lineSeparator()
                + "Second line" + System.lineSeparator()
                + "Third Line" + System.lineSeparator()
                + "Fourth Line" + System.lineSeparator();
        String expected = "Test 54 -c" + System.lineSeparator()
                + "Second line" + System.lineSeparator()
                + "Third Line" + System.lineSeparator()
                + "Fourth Line" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-c", "lower", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 55
    public void textilatorTest55() {
        String input = "Test 55 -e, -p" + System.lineSeparator()
                + "Second line" + System.lineSeparator()
                + "Third Line" + System.lineSeparator()
                + "Fourth Line" + System.lineSeparator();
        String expected = ". Test 55 -e, -p" + System.lineSeparator()
                + ". Second line" + System.lineSeparator()
                + ". Third Line" + System.lineSeparator()
                + ". Fourth Line" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-e", "0", "-p", ". ", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 56
    public void textilatorTest56() {
        String input = "Test 56 -e" + System.lineSeparator()
                + "Second line" + System.lineSeparator()
                + "Third Line" + System.lineSeparator()
                + "Fourth Line" + System.lineSeparator();
        String expected = "Test 56 -e" + System.lineSeparator()
                + "Second line" + System.lineSeparator()
                + "Third Line" + System.lineSeparator()
                + "Fourth Line" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-e", "0", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 57
    public void textilatorTest57() {
        String input = "Test 57 -a, -p" + System.lineSeparator()
                + "Second line" + System.lineSeparator()
                + "Third Line" + System.lineSeparator()
                + "Fourth Line" + System.lineSeparator();
        String expected = "- 84 101 115 116 32 53 55 32 45 97 44 32 45 112 " + System.lineSeparator()
                + "- 83 101 99 111 110 100 32 108 105 110 101 " + System.lineSeparator()
                + "- 84 104 105 114 100 32 76 105 110 101 " + System.lineSeparator()
                + "- 70 111 117 114 116 104 32 76 105 110 101 " + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-a", "-p", "- ", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 58
    public void textilatorTest58() {
        String input = "Test 58 -a" + System.lineSeparator()
                + "Second line" + System.lineSeparator()
                + "Third Line" + System.lineSeparator()
                + "Fourth Line" + System.lineSeparator();
        String expected = "84 101 115 116 32 53 56 32 45 97 " + System.lineSeparator()
                + "83 101 99 111 110 100 32 108 105 110 101 " + System.lineSeparator()
                + "84 104 105 114 100 32 76 105 110 101 " + System.lineSeparator()
                + "70 111 117 114 116 104 32 076 105 110 101 " + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-a", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Frame #: 59
    public void textilatorTest59() {
        String input = "Test 59 -a" + System.lineSeparator()
                + "Second line" + System.lineSeparator()
                + "Third Line" + System.lineSeparator()
                + "Fourth Line" + System.lineSeparator();
        String expected = "& Test 59 -p" + System.lineSeparator()
                + "& Second line" + System.lineSeparator()
                + "& Third Line" + System.lineSeparator()
                + "& Fourth Line" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-p", "& ", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }
    @Test
    // Frame #: 60
    public void textilatorTest60() {
        String input = "Test 60" + System.lineSeparator()
                + "Second line" + System.lineSeparator()
                + "Third Line" + System.lineSeparator()
                + "Fourth Line" + System.lineSeparator();
        String expected = "Test 60" + System.lineSeparator()
                + "Second line" + System.lineSeparator()
                + "Third Line" + System.lineSeparator()
                + "Fourth Line" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }
}
