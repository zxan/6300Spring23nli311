package edu.gatech.seclass.sdpencryptor;

public class SdpModel {

    private String myString;
    private int arg1;
    private int arg2;
    private String encrypted;

    public SdpModel(String myString, int arg1, int arg2, String encrypted) {
        this.myString = myString;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.encrypted = encrypted;
    }


    @Override
    public String toString() {
        return "SdpModel{" +
                "myString='" + myString + '\'' +
                ", arg1=" + arg1 +
                ", arg2=" + arg2 +
                ", encrypted='" + encrypted + '\'' +
                '}';
    }

    public String getMyString() {
        return myString;
    }

    public int getArg1() {
        return arg1;
    }

    public int getArg2() {
        return arg2;
    }

    public String getEncrypted() {
        return encrypted;
    }

    public void setMyString(String myString) {
        this.myString = myString;
    }

    public void setArg1(int arg1) {
        this.arg1 = arg1;
    }

    public void setArg2(int arg2) {
        this.arg2 = arg2;
    }

    public void setEncrypted(String encrypted) {
        this.encrypted = encrypted;
    }
}
