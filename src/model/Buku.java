package model; // Package yang berisi kelas model untuk representasi data Buku

public class Buku {
    private String judul; // Variabel untuk menyimpan judul buku
    private String kategori; // Variabel untuk menyimpan kategori buku
    private String penulis; // Variabel untuk menyimpan penulis buku
    private String penerbit; // Variabel untuk menyimpan penerbit buku
    private String terbit; // Variabel untuk menyimpan tahun terbit buku
    private Integer stok; // Variabel untuk menyimpan jumlah stok buku
    private Integer id; // Variabel untuk menyimpan ID buku
    private Integer sewa; // Variabel untuk menyimpan status sewa buku

    // Konstruktor untuk membuat objek Buku dengan data yang diberikan
    public Buku(Integer id_buku, String kategori, String judul, String penulis, String penerbit, String tahun_terbit, Integer stok, Integer sewa) {
        this.id = id_buku;
        this.kategori = kategori;
        this.judul = judul;
        this.penulis = penulis;
        this.penerbit = penerbit;
        this.terbit = tahun_terbit;
        this.stok = stok;
        this.sewa = sewa;
    }

    // Getter untuk mendapatkan status sewa buku
    public int getSewa() {
        return sewa;
    }

    // Getter untuk mendapatkan ID buku
    public int getId() {
        return id;
    }

    // Getter untuk mendapatkan tahun terbit buku
    public String getTerbit() {
        return terbit;
    }

    // Getter untuk mendapatkan kategori buku
    public String getKategori() {
        return kategori;
    }

    // Getter untuk mendapatkan judul buku
    public String getJudul() {
        return judul;
    }

    // Getter untuk mendapatkan penulis buku
    public String getPenulis() {
        return penulis;
    }

    // Getter untuk mendapatkan penerbit buku
    public String getPenerbit() {
        return penerbit;
    }

    // Getter untuk mendapatkan stok buku
    public Integer getStok() {
        return stok;
    }
}
