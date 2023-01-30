package com.example.hicare_prototype;

public class NomorAntrianGenerator {

    private static int angka = 1;

    public NomorAntrianGenerator(){

    }

    public String generate(){
        String curAngka = Integer.toString(angka);
        angka++;
        return ("A"+curAngka);
    }
}
