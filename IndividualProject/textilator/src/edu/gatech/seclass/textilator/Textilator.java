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
            throw new TextilatorException("Filepath not set");
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            int lineNumber = 0;
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
                }

                // Modify line according to settings
                if (!skipLine && !excludeLine) {
                    if (letterCase != null) {
                        if (letterCase == Case.upper) {
                            line = line.toUpperCase();
                        } else if (letterCase == Case.lower) {
                            line = line.toLowerCase();
                        }
                    }

                    if (cipherText != 0) {
                        line = shiftText(line, cipherText);
                    }

                    if (encodeLines) {
                        line = encodeText(line);
                    }

                    if (prefix != null) {
                        line = prefix + line;
                    }

                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            throw new TextilatorException(e.getMessage());
        }
    }

    private String shiftText(String text, int shiftAmount) {
        StringBuilder sb = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (Character.isAlphabetic(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                int offset = (c - base + shiftAmount) % 26;
                char shifted = (char) (base + offset);
                sb.append(shifted);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private String encodeText(String text) {
        StringBuilder sb = new StringBuilder();
        for (char c : text.toCharArray()) {
            int asciiValue = (int) c;
            String hexValue = Integer.toHexString(asciiValue);
            sb.append(hexValue);
        }
        return sb.toString();
    }
}