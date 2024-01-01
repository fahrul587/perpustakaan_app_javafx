package model; // Package yang berisi kelas model untuk representasi data Pinjam

public class Pinjam {
    private int id; // Variabel untuk menyimpan ID peminjaman
    private int sewa; // Variabel untuk menyimpan lama waktu sewa
    private String buku; // Variabel untuk menyimpan judul buku yang dipinjam
    private String anggota; // Variabel untuk menyimpan nama anggota yang melakukan peminjaman
    private String pinjam; // Variabel untuk menyimpan tanggal peminjaman
    private String kembali; // Variabel untuk menyimpan tanggal pengembalian

    // Konstruktor untuk membuat objek Pinjam dengan data yang diberikan
    public Pinjam(int id, int sewa, String buku, String anggota, String pinjam, String kembali) {
        this.id = id;
        this.buku = buku;
        this.anggota = anggota;
        this.pinjam = pinjam;
        this.kembali = kembali;
        this.sewa = sewa;
    }

    // Getter-method untuk mendapatkan nilai ID peminjaman
    public int getId() {
        return id;
    }

    // Getter-method untuk mendapatkan lama waktu sewa
    public int getSewa() {
        return sewa;
    }

    // Getter-method untuk mendapatkan judul buku yang dipinjam
    public String getBuku() {
        return buku;
    }

    // Getter-method untuk mendapatkan nama anggota yang melakukan peminjaman
    public String getAnggota() {
        return anggota;
    }

    // Getter-method untuk mendapatkan tanggal peminjaman
    public String getPinjam() {
        return pinjam;
    }

    // Getter-method untuk mendapatkan tanggal pengembalian
    public String getKembali() {
        return kembali;
    }
}
