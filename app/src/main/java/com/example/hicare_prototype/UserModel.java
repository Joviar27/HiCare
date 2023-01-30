package com.example.hicare_prototype;

import static java.lang.String.valueOf;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class UserModel {

    String username;
    String email;
    String nomorTelepon;
    String usia;
    String tanggalLahir;
    String jenisKelamin;

    String profil;

    public UserModel(){
        //Default Constructor
    }

    public UserModel(String username, String email){
        this.username = username;
        this.email = email;
        nomorTelepon = "";
        usia = "";
        tanggalLahir = "";
        jenisKelamin = "";
        profil = "";
    }

    public UserModel (String username, String tanggalLahir, String jenisKelamin, String nomorTelepon, String email, String profil){
        this.username = username;
        this.tanggalLahir = tanggalLahir;
        this.jenisKelamin = jenisKelamin;
        this.nomorTelepon = nomorTelepon;
        this.email = email;
        this.usia = calculateUsia(tanggalLahir);
        this.profil = profil;
    }

    private String calculateUsia(String tanggalLahir){
        LocalDate tLahir = LocalDate.parse(tanggalLahir);
        LocalDate curDate = LocalDate.now();
        int umur = Period.between(tLahir, curDate).getYears();
        return valueOf(umur);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNomorTelepon() {
        return nomorTelepon;
    }

    public void setNomorTelepon(String nomorTelepon) {
        this.nomorTelepon = nomorTelepon;
    }

    public String getUsia() {
        return usia;
    }

    public void setUsia(String usia) {
        this.usia = usia;
    }

    public String getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(String tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getProfil() {
        return profil;
    }

    public void setProfil(String profil) {
        this.profil = profil;
    }
}
