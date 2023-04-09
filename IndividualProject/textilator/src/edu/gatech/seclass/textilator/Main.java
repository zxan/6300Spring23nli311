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
        if (args.length > 1 && args[0].equals("textilator")) {
            File file = new File(args[args.length-1]);
            if (file.isFile() && file.exists() && file.getName().endsWith(".txt")) {
                try {
                    String content = new String(Files.readAllBytes(file.toPath()));
                    if (content.endsWith(System.lineSeparator())) {
                        System.out.println("Success and Last argument is a text file in the same directory as Main.java");
                        for (int i = 1; i < args.length - 1; i++) {
                            System.out.println("Argument " + i + ": " + args[i]);
                        }
                    } else {
                        System.out.println("Last line of the text file does not end with a line separator. This is an empty file.");
                    }
                } catch (IOException e) {
                    System.out.println("Error reading file: " + e.getMessage());
                }
            } else {
                System.out.println("Error: Invalid file format or file does not exist.");
            }
        } else {
            System.out.println("Error: Invalid command format.");
        }
    }

    private static void usage() {
        System.err.println("Usage: textilator [ -s number | -x substring | -c case | -e num | -a | -p prefix ] FILE");
    }
}