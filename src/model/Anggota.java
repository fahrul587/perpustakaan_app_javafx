package model; // Package yang berisi kelas model untuk representasi data Anggota

public class Anggota {
    private Integer id; // Variabel untuk menyimpan ID anggota
    private String nama; // Variabel untuk menyimpan nama anggota
    private String alamat; // Variabel untuk menyimpan alamat anggota
    private String email; // Variabel untuk menyimpan email anggota
    private String wa; // Variabel untuk menyimpan nomor WhatsApp anggota
    private String tanggal; // Variabel untuk menyimpan tanggal bergabung anggota
    private Integer sisa; // Variabel untuk menyimpan sisa pinjaman anggota

    // Konstruktor untuk membuat objek Anggota dengan data yang diberikan
    public Anggota(Integer id, String nama, String alamat, String email, String wa, String tanggal, Integer sisa) {
        this.id = id;
        this.nama = nama;
        this.alamat = alamat;
        this.email = email;
        this.wa = wa;
        this.tanggal = tanggal;
        this.sisa = sisa;
    }

    // Getter untuk mendapatkan nilai sisa pinjaman
    public Integer getSisa() {
        return sisa;
    }

    // Getter untuk mendapatkan nomor WhatsApp
    public String getWa() {
        return wa;
    }

    // Getter untuk mendapatkan tanggal bergabung
    public String getTanggal() {
        return tanggal;
    }

    // Getter untuk mendapatkan ID anggota
    public Integer getId() {
        return id;
    }

    // Getter untuk mendapatkan nama anggota
    public String getNama() {
        return nama;
    }

    // Getter untuk mendapatkan alamat anggota
    public String getAlamat() {
        return alamat;
    }

    // Getter untuk mendapatkan email anggota
    public String getEmail() {
        return email;
    }
}
