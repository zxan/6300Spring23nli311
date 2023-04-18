package edu.gatech.seclass.textilator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Textilator implements TextilatorInterface {
    private String filepath;
    private LineParity lineToSkip;
    private String excludeString;
    private Case letterCase;
    private int cipherText;
    private boolean encodeLines;
    private String prefix;

    public Textilator() {
        reset();
    }

    @Override
    public void reset() {
        filepath = null;
        lineToSkip = null;
        excludeString = null;
        letterCase = null;
        cipherText = 0;
        encodeLines = false;
        prefix = null;
    }

    @Override
    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    @Override
    public void setLineToSkip(LineParity lineToSkip) {
        this.lineToSkip = lineToSkip;
    }

    @Override
    public void setExcludeString(String excludeString) {
        this.excludeString = excludeString;
    }

    @Override
    public void setLetterCase(Case letterCase) {
        this.letterCase = letterCase;
    }

    @Override
    public void setCipherText(int shiftAmount) {
        this.cipherText = shiftAmount;
    }

    @Override
    public void setEncodeLines(boolean encodeLines) {
        this.encodeLines = encodeLines;
    }

    @Override
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public void textilator() throws TextilatorException {
        // Check that the filepath has been set
        if (filepath == null) {
            throw new TextilatorException("Usage: textilator [ -s number | -x substring | -c case | -e num | -a | -p prefix ] FILE");
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            int lineNumber = 0;
            String lastPParam = "";
            while ((line = br.readLine()) != null) {
                lineNumber++;

                // Check if line should be skipped
                boolean skipLine = false;
                if (lineToSkip != null) {
                    int parity = lineNumber % 2;
                    if ((lineToSkip == LineParity.even && parity != 0) ||
                            (lineToSkip == LineParity.odd && parity != 1)) {
                        skipLine = true;
                    }
                }

                // Check if line contains excludeString
                boolean excludeLine = false;
                if (excludeString != null && line.contains(excludeString)) {
                    excludeLine = true;
                    if (excludeLine == true){
                        line = runX(excludeString, line);
                    }
                }

                // Modify line according to settings
                if (!skipLine && !excludeLine) {
                    if (letterCase != null) {
                        if (letterCase == Case.upper) {
                            line = runC(Case.upper, line);
                        } else if (letterCase == Case.lower) {
                            line = runC(Case.upper, line);
                        }
                    }

                    if (cipherText != 0) {
                        line = shiftText(line, cipherText);
                    }

                    if (encodeLines) {
                        line = encodeText(line);
                    }

                    if (prefix != null) {
                        if (prefix.equals("")) {
                            throw new TextilatorException("Usage: textilator [ -s number | -x substring | -c case | -e num | -a | -p prefix ] FILE");
                        }
                        line = runP(prefix, line);
                    }

                    if (line.startsWith("P:")) {
                        lastPParam = line;
                    }

                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            throw new TextilatorException("Usage: textilator [ -s number | -x substring | -c case | -e num | -a | -p prefix ] FILE");
        }
    }

    private String shiftText(String text, int shiftAmount) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')) {
                char base = Character.isUpperCase(ch) ? 'A' : 'a';
                ch = (char) (base + (ch - base + shiftAmount + 26) % 26);
            }
            result.append(ch);
        }
        return result.toString();
    }

    private String encodeText(String text) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if ((int) ch >= 32 && (int) ch <= 126) {
                result.append((int) ch);
                result.append(' ');
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    private static String runP(String content, String lastPParam) {
        String[] pLines = content.split(System.lineSeparator());
        StringBuilder result = new StringBuilder();
        for (String pLine : pLines) {
            result.append(lastPParam).append(pLine).append(System.lineSeparator());
        }
        return result.toString();
    }

    private static String runX(String content, String lastXParam){
        String[] xLines = content.split(System.lineSeparator());
        StringBuilder result = new StringBuilder();
        for (String xLine : xLines){
            if (!xLine.contains(lastXParam)){
                result.append(xLine).append(System.lineSeparator());
            }
        }

        return result.toString();
    }

    private static String runS(String content, String lastSParam) {
        String[] sLines = content.split(System.lineSeparator());
        StringBuilder result = new StringBuilder();
        int Snum = Integer.parseInt(lastSParam);
        for (int i = 0; i < sLines.length; i++) {
            if ((i % 2 != 0 && Snum == 0) || (i % 2 == 0 && Snum == 1)) {
                continue;
            }
            result.append(sLines[i]).append(System.lineSeparator());
        }
        return result.toString();
    }

    private static String runC(String content, String lastCParam){
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < content.length(); i++) {
            char ch = content.charAt(i);
            if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')) {
                if (lastCParam.equals("upper")){
                    ch = Character.toUpperCase(ch);
                }
                if (lastCParam.equals("lower")){
                    ch = Character.toLowerCase(ch);
                }
            }
            result.append(ch);
        }
        return result.toString();
    }
}