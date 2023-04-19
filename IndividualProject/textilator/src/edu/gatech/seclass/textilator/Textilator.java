package edu.gatech.seclass.textilator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.File;

public class Textilator implements TextilatorInterface {

    private String filepath;
    private LineParity lineToSkip;
    private String excludeString;
    private Case letterCase;
    private int cipherText;
    private boolean encodeLines;
    private String prefix;
    boolean aUsed = false;
    String lastCParam = null;
    boolean cUsed = false;
    String lastEParam = null;
    boolean eUsed = false;
    String lastSParam = null;
    boolean sUsed = false;
    String lastPParam = null;
    boolean pUsed = false;
    String lastXParam = null;
    boolean xUsed = false;
    boolean error = false;

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
        error = false;
        aUsed = false;
        lastCParam = null;
        cUsed = false;
        lastEParam = null;
        eUsed = false;
        lastSParam = null;
        sUsed = false;
        lastPParam = null;
        pUsed = false;
        lastXParam = null;
        xUsed = false;
    }

    @Override
    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    @Override
    public void setLineToSkip(LineParity lineToSkip) {
        //S
        sUsed = true;
        lastSParam = lineToSkip.toString();
    }

    @Override
    public void setExcludeString(String excludeString) {
        //X
        xUsed = true;
        lastXParam = excludeString.toString();
    }

    @Override
    public void setLetterCase(Case letterCase) {
        //C
        cUsed = true;
        lastCParam = letterCase.toString();
    }

    @Override
    public void setCipherText(int shiftAmount) {
        //E
        eUsed = true;
        lastEParam = Integer.toString(shiftAmount);
    }

    @Override
    public void setEncodeLines(boolean encodeLines) {
        //A
        this.encodeLines = encodeLines;
    }

    @Override
    public void setPrefix(String prefix) {
        //P
        pUsed = true;
        lastPParam = prefix;
    }

    @Override
    public void textilator() throws TextilatorException {
        if (filepath == null) {
            throw new TextilatorException("Usage: textilator [ -s number | -x substring | -c case | -e num | -a | -p prefix ] FILE");
        } else {
            File file = new File(filepath);
            if (file.isFile() && file.exists() && file.getName().endsWith(".txt")) {
                try {
                    String content = new String(Files.readAllBytes(Paths.get(filepath)));
                    if (content.endsWith(System.lineSeparator()) || content.length() == 0) {
                        // Process the contents of the file here
                        if (sUsed && xUsed){
                            //System.out.println("Error X and S used");
                            error = true;
                        }
                        if (aUsed && eUsed){
                            //System.out.println("Error A and E used");
                            error = true;
                        }
                        if (pUsed && lastPParam.equals("")){
                            error = true;
                        }
                        if (!error){
                            if(xUsed){
                                content = runX(content, lastXParam);
                            }
                            if(sUsed){
                                if (lastSParam.equals("even")) {
                                    lastSParam = "1";
                                } else if (lastSParam.equals("odd")) {
                                    lastSParam = "0";
                                } else {
                                    throw new TextilatorException("Usage: textilator [ -s number | -x substring | -c case | -e num | -a | -p prefix ] FILE");
                                }
                                content = runS(content, lastSParam);
                            }
                            if(cUsed){
                                content = runC(content, lastCParam);
                            }
                            if(eUsed){
                                content = runE(content, lastEParam);
                            }

                            if(aUsed && this.encodeLines){
                                content = runA(content);
                            }

                            if(pUsed){
                                content = runP(content, lastPParam);
                            }
                            if (xUsed && lastXParam.equals("")){
                                content = "";
                            }
                            System.out.print(content);
                        }
                    }
                } catch (IOException e) {
                    throw new TextilatorException("Usage: textilator [ -s number | -x substring | -c case | -e num | -a | -p prefix ] FILE");
                }
            } else {
                throw new TextilatorException("Usage: textilator [ -s number | -x substring | -c case | -e num | -a | -p prefix ] FILE");
            }
        }
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

    private static String runE(String content, String lastEParam) {
        StringBuilder result = new StringBuilder();
        int Enum = Integer.parseInt(lastEParam);
        for (int i = 0; i < content.length(); i++) {
            char ch = content.charAt(i);
            if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')) {
                char base = Character.isUpperCase(ch) ? 'A' : 'a';
                ch = (char) (base + (ch - base + Enum + 26) % 26);
            }
            result.append(ch);
        }
        return result.toString();
    }

    private static String runA(String content) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < content.length(); i++) {
            char ch = content.charAt(i);
            if ((int) ch >= 32 && (int) ch <= 126) {
                result.append((int) ch);
                result.append(' ');
            }
            else{
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
}