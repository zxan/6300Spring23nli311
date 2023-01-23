package edu.gatech.seclass;

public class MyString implements MyStringInterface{
    public String myString;
    @Override
    public String getString() {
        return myString;
    }

    @Override
    public void setString(String string) {
        myString = string;
        if (myString == easterEgg || (myString.length() == 0) || !myString.matches(".*[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890].*")) {
            throw new IllegalArgumentException();
        }
        else{
            myString = string;
        }
    }

    @Override
    public int countAlphabeticWords() {
        int count = 0;
        boolean is_it_char = false;
        if (myString == null) {
            throw new NullPointerException();
        }
        else{
            for (int c = 0; c < myString.length(); c++) {
                if (Character.isLetter(myString.charAt(c))) {
                    if (is_it_char == false){
                        is_it_char = true;
                        count += 1;
                    }
                }
                else{
                    is_it_char = false;
                }
            }
            return count;
        }
    }

    @Override
    public String encrypt(int arg1, int arg2) {
        String encrypted = "";
        if (myString == null) {
            throw new NullPointerException();
        } else if (((arg1 != 1 && arg1 != 3 && arg1 != 5 && arg1 != 7 && arg1 != 11 && arg1 != 13 && arg1 != 17 && arg1 != 19 && arg1 != 23 && arg1 != 29 && arg1 != 31 && arg1 != 37 && arg1 != 41 && arg1 != 43 && arg1 != 47 && arg1 != 53 && arg1 != 59 && arg1 != 61)
                || arg2 >= 62 || arg2 <= 0) && myString != null) {
            throw new IllegalArgumentException();
        } else {
            for (int c = 0; c < myString.length(); c++) {
                int char_num = 0;
                int char_enc = 0;
                String f = "";
                f = String.valueOf(myString.charAt(c));
                if (f.matches(".*[ABCDEFGHIJKLMNOPQRSTUVWXYZ].*")){
                    char_num = (int) myString.charAt(c) - 39;
                    char_enc = ((char_num * arg1) + arg2) % 62;
                    if (char_enc <= 25){
                        char_enc += 97;
                        encrypted += (char)char_enc;
                    } else if (char_enc <= 51) {
                        char_enc += 39;
                        encrypted += (char)char_enc;
                    } else{
                        char_enc -= 39;
                        encrypted += (char)char_enc;
                    }
                } else if (f.matches(".*[abcdefghijklmnopqrstuvwxyz].*")) {
                    char_num = (int) myString.charAt(c) - 97;
                    char_enc = ((char_num * arg1) + arg2) % 62;
                    if (char_enc <= 25){
                        char_enc += 97;
                        encrypted += (char)char_enc;
                    } else if (char_enc <= 51) {
                        char_enc += 39;
                        encrypted += (char)char_enc;
                    } else{
                        char_enc -= 39;
                        encrypted += (char)char_enc;
                    }
                } else if (f.matches(".*[1234567890].*")) {
                    char_num = (int) myString.charAt(c) + 4;
                    char_enc = ((char_num * arg1) + arg2) % 62;
                    if (char_enc <= 25){
                        char_enc += 97;
                        encrypted += (char)char_enc;
                    } else if (char_enc <= 51) {
                        char_enc += 39;
                        encrypted += (char)char_enc;
                    } else{
                        char_enc -= 39;
                        encrypted += (char)char_enc;
                    }
                }
                else{
                    encrypted += myString.charAt(c);
                }
            }
            return encrypted;
        }
    }

    @Override
    public void convertDigitsToNamesInSubstring(int firstPosition, int finalPosition) {
        String encrypted = "";
        if (myString == null) {
            throw new NullPointerException();
        } else if ((firstPosition < 1 || firstPosition > finalPosition) && myString != null) {
            throw new IllegalArgumentException();
        } else if ((finalPosition > myString.length()) && myString != null) {
            throw new MyIndexOutOfBoundsException();
        }
        else{
            for (int c = 1; c < myString.length() + 1; c++) {
                if (c >= firstPosition && c <= finalPosition) {
                    if (myString.charAt(c - 1) == '0'){
                        encrypted += "Zero";
                    } else if (myString.charAt(c - 1) == '1') {
                        encrypted += "One";
                    } else if (myString.charAt(c - 1) == '2') {
                        encrypted += "Two";
                    } else if (myString.charAt(c - 1) == '3') {
                        encrypted += "Three";
                    } else if (myString.charAt(c - 1) == '4') {
                        encrypted += "Four";
                    } else if (myString.charAt(c - 1) == '5') {
                        encrypted += "Five";
                    } else if (myString.charAt(c - 1) == '6') {
                        encrypted += "Six";
                    } else if (myString.charAt(c - 1) == '7') {
                        encrypted += "Seven";
                    } else if (myString.charAt(c - 1) == '8') {
                        encrypted += "Eight";
                    } else if (myString.charAt(c - 1) == '9') {
                        encrypted += "Nine";
                    } else {
                        encrypted += myString.charAt(c - 1);
                    }
                }
                else{
                    encrypted += myString.charAt(c - 1);
                }
            }
            myString = encrypted;
        }
    }
}

