package com.example.capstoneproject_pb;

public class Wisata {
    private String nama;
    private String gambar_url;
    private String kategori;
    private Integer id;

    public Wisata() { }
    public Wisata(String nama, String gambar_url, String kategori, int id) {
        this.nama =nama;
        this.gambar_url = gambar_url;
        this.kategori = kategori;
        this.id =id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getGambar_url() {
        return gambar_url;
    }

    public void setGambar_url(String gambar_url) {
        this.gambar_url = gambar_url;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
