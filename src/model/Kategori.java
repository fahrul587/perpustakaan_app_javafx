package model; // Package yang berisi kelas model untuk representasi data Kategori

public class Kategori {
    private int id; // Variabel untuk menyimpan ID kategori
    private String kategori; // Variabel untuk menyimpan nama kategori
    private String join; // Variabel untuk menyimpan hasil penggabungan ID dan nama kategori

    // Konstruktor untuk membuat objek Kategori dengan data yang diberikan
    public Kategori(int id, String kategori) {
        this.id = id;
        this.kategori = kategori;
        this.join = id + "_" + kategori;
    }

    // Getter untuk mendapatkan nilai ID kategori
    public int getId() {
        return id;
    }

    // Getter untuk mendapatkan nama kategori
    public String getKategori() {
        return kategori;
    }

    // Getter untuk mendapatkan hasil penggabungan ID dan nama kategori
    public String getJoin() {
        return join;
    }
}