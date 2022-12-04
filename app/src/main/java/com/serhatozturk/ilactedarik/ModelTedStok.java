package com.serhatozturk.ilactedarik;

public class ModelTedStok {

    String id;
    String adres;
    String tarih;
    String ilac;
    String stok;
    String telefon;
    String unvan;
    String mail;

    public ModelTedStok(String id, String adres, String tarih, String ilac, String stok, String telefon, String unvan, String mail) {
        this.id = id;
        this.adres = adres;
        this.tarih = tarih;
        this.ilac = ilac;
        this.stok = stok;
        this.telefon = telefon;
        this.unvan = unvan;
        this.mail = mail;
    }
}
