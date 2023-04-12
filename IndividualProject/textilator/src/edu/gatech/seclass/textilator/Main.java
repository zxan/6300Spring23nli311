package edu.gatech.seclass.textilator;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.File;

public class Main {
    // Empty Main class for compiling Individual Project
    // During Deliverable 1 and Deliverable 2, DO NOT ALTER THIS CLASS or implement it

    public static void main(String[] args) {
        // Empty Skeleton Method
        boolean error = false;
        if (args.length > 1 && args[0].equals("textilator")) {
            File file = new File(args[args.length-1]);
            if (file.isFile() && file.exists() && file.getName().endsWith(".txt")) {
                try {
                    String content = new String(Files.readAllBytes(file.toPath()));
                    if (content.endsWith(System.lineSeparator())) {
                        //System.out.println("Success and Last argument is a text file in the same directory as Main.java");

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


                        for (int i = 1; i < args.length - 1; i++) {
                            switch(args[i]) {
                                case "-c":
                                    if ((args[i+1].equals("upper") || args[i+1].equals("lower"))) {
                                        lastCParam = args[i+1];
                                        //System.out.println("-c parameter: " + lastCParam);
                                        i++;
                                        cUsed = true;
                                    } else {
                                        //System.out.println("Missing or invalid parameter for -c");
                                        error = true;
                                    }
                                    break;
                                case "-p":
                                    if (args[i+1] instanceof String && i < args.length - 2) {
                                        lastPParam = args[i+1];
                                        //System.out.println("-p parameter: " + lastPParam);
                                        i++;
                                        pUsed = true;
                                    } else {
                                        //System.out.println("Missing or invalid parameter for -p");
                                        error = true;
                                    }
                                    break;
                                case "-x":
                                    if (args[i+1] instanceof String && i < args.length - 2) {
                                        lastXParam = args[i+1];
                                        //System.out.println("-x parameter: " + lastXParam);
                                        i++;
                                        xUsed = true;
                                    } else {
                                        //System.out.println("Missing or invalid parameter for -x");
                                        error = true;
                                    }
                                    break;
                                case "-e":
                                    try {
                                        int e_num = Integer.parseInt(args[i + 1]);
                                        if (e_num >= -25 && e_num <= 25) {
                                            lastEParam = Integer.toString(e_num);
                                            //System.out.println("-e parameter: " + lastEParam);
                                            i++;
                                            eUsed = true;
                                            // The argument is a number between -25 and 25
                                        } else {
                                            //System.out.println("Missing or invalid parameter for -e");
                                            error = true;
                                        }
                                    } catch (NumberFormatException e) {
                                        //System.out.println("The e argument is not a valid integer");
                                        error = true;
                                    }
                                    break;
                                case "-s":
                                    try {
                                        int s_num = Integer.parseInt(args[i + 1]);
                                        if (s_num == 0 || s_num == 1) {
                                            lastSParam = Integer.toString(s_num);
                                            //System.out.println("-s parameter: " + lastSParam);
                                            i++;
                                            sUsed = true;
                                            // The argument is a number 1 or 0
                                        } else {
                                            //System.out.println("Missing or invalid parameter for -s");
                                            error = true;
                                        }
                                    } catch (NumberFormatException e) {
                                        //System.out.println("The s argument is not a valid integer");
                                        error = true;
                                    }
                                    break;

                                case "-a":
                                    //System.out.println("-a parameter: none");
                                    aUsed = true;
                                    break;
                                default:
                                    //System.out.println("This is an error Unknown argument: " + args[i]);
                                    error = true;
                                    break;
                            }
                        } //end of for loop

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
                                content = runS(content, lastSParam);
                            }
                            if(cUsed){
                                content = runC(content, lastCParam);
                            }
                            if(eUsed){
                                content = runE(content, lastEParam);
                            }

                            if(aUsed){
                                content = runA(content);
                            }

                            if(pUsed){
                                content = runP(content, lastPParam);
                            }

                            System.out.println(content);

                        }

                    } else {
                        //System.out.println("Last line of the text file does not end with a line separator. This is an empty file.");
                        error = true;
                    }
                } catch (IOException e) {
                    //System.out.println("Error reading file: " + e.getMessage());
                    error = true;
                }
            } else {
                //System.out.println("Error: Invalid file format or file does not exist.");
                error = true;
            }
        } else {
            //System.out.println("Error: Invalid command format.");
            error = true;
        }

    if (error){
        usage();
    }
    }

    private static String runX(String content, String lastXParam){
        String[] xLines = content.split("\\r?\\n");
        StringBuilder result = new StringBuilder();
        for (String xLine : xLines){
            if (!xLine.contains(lastXParam)){
                result.append(xLine).append(System.lineSeparator());
            }
        }
        return result.toString();
    }

    private static String runS(String content, String lastSParam) {
        String[] sLines = content.split("\\r?\\n");
        StringBuilder result = new StringBuilder();
        int Snum = Integer.parseInt(lastSParam);
        for (int i = 0; i < sLines.length; i++) {
            if ((i % 2 == 0 && Snum == 0) || (i % 2 != 0 && Snum == 1)) {
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
        String[] pLines = content.split("\\r?\\n");
        StringBuilder result = new StringBuilder();
        for (String pLine : pLines) {
            result.append(lastPParam).append(pLine).append("\n");
        }
        return result.toString();
    }

    private static void usage() {
        System.err.println("Usage: textilator [ -s number | -x substring | -c case | -e num | -a | -p prefix ] FILE");
    }
}