package com.example.hicare_prototype;

public class AntrianModel {

    private String rumahSakit;
    private String namaAnak;
    private String tanggalLahir;
    private String jenisKelamin;
    private String beratBadan;
    private String tinggiBadan;
    private String lingkarKepala;
    private String goldar;
    private String alergi;
    private String tanggalLayanan;
    private String pelayanan;
    private String kartuBPJS;
    private String noAntrian;

    public AntrianModel(){
        //Default Method
    }

    public AntrianModel(String tanggalLayanan, String rumahSakit, String beratBadan, String tinggiBadan, String lingkarKepala) {
        this.rumahSakit = rumahSakit;
        this.beratBadan = beratBadan;
        this.tinggiBadan = tinggiBadan;
        this.lingkarKepala = lingkarKepala;
        this.tanggalLayanan = tanggalLayanan;
        this.namaAnak = "";
        this.tanggalLahir = "";
        this.jenisKelamin = "";
        this.goldar = "";
        this.alergi = "";
        this.pelayanan = "";
        this.kartuBPJS = "";
        this.noAntrian = "";
    }

    public AntrianModel(String rumahSakit, String namaAnak, String tanggalLahir, String jenisKelamin, String beratBadan, String tinggiBadan, String lingkarKepala, String goldar, String alergi, String tanggalLayanan, String pelayanan, String kartuBPJS, String noAntrian) {
        this.rumahSakit = rumahSakit;
        this.namaAnak = namaAnak;
        this.tanggalLahir = tanggalLahir;
        this.jenisKelamin = jenisKelamin;
        this.beratBadan = beratBadan;
        this.tinggiBadan = tinggiBadan;
        this.lingkarKepala = lingkarKepala;
        this.goldar = goldar;
        this.alergi = alergi;
        this.tanggalLayanan = tanggalLayanan;
        this.pelayanan = pelayanan;
        this.kartuBPJS = kartuBPJS;
        this.noAntrian = noAntrian;
    }

    public String getRumahSakit() {
        return rumahSakit;
    }

    public void setRumahSakit(String rumahSakit) {
        this.rumahSakit = rumahSakit;
    }

    public String getNamaAnak() {
        return namaAnak;
    }

    public void setNamaAnak(String namaAnak) {
        this.namaAnak = namaAnak;
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

    public String getBeratBadan() {
        return beratBadan;
    }

    public void setBeratBadan(String beratBadan) {
        this.beratBadan = beratBadan;
    }

    public String getTinggiBadan() {
        return tinggiBadan;
    }

    public void setTinggiBadan(String tinggiBadan) {
        this.tinggiBadan = tinggiBadan;
    }

    public String getLingkarKepala() {
        return lingkarKepala;
    }

    public void setLingkarKepala(String lingkarKepala) {
        this.lingkarKepala = lingkarKepala;
    }

    public String getGoldar() {
        return goldar;
    }

    public void setGoldar(String goldar) {
        this.goldar = goldar;
    }

    public String getAlergi() {
        return alergi;
    }

    public void setAlergi(String alergi) {
        this.alergi = alergi;
    }

    public String getTanggalLayanan() {
        return tanggalLayanan;
    }

    public void setTanggalLayanan(String tanggalLayanan) {
        this.tanggalLayanan = tanggalLayanan;
    }

    public String getPelayanan() {
        return pelayanan;
    }

    public void setPelayanan(String pelayanan) {
        this.pelayanan = pelayanan;
    }

    public String getKartuBPJS() {
        return kartuBPJS;
    }

    public void setKartuBPJS(String kartuBPJS) {
        this.kartuBPJS = kartuBPJS;
    }

    public String getNoAntrian() {
        return noAntrian;
    }

    public void setNoAntrian(String noAntrian) {
        this.noAntrian = noAntrian;
    }
}
