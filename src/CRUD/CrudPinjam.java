package CRUD;

import java.sql.*;
import java.time.LocalDate;
import javafx.collections.*;
import model.Pinjam;

public class CrudPinjam {
    private static PreparedStatement prepare;
    private static Connection conn;
    private static ResultSet result;

    public static ObservableList<Pinjam> getDataPinjam(String s) {
        ObservableList<Pinjam> listData = FXCollections.observableArrayList();
        // Membuat objek ObservableList yang digunakan untuk menyimpan data peminjaman.

        String query = "SELECT * FROM peminjaman " +
                "JOIN buku ON peminjaman.id_buku = buku.id_buku " +
                "JOIN anggota ON peminjaman.id_anggota = anggota.id_anggota " +
                s + "ORDER BY peminjaman.id_anggota ASC, peminjaman.tgl_pinjam ASC";
        // Membuat query SQL untuk mengambil data peminjaman dari tabel peminjaman dengan menggabungkan tabel buku dan anggota.

        conn = Koneksi.connectDb(); // Mendapatkan koneksi ke database.

        try {
            prepare = conn.prepareStatement(query);
            result = prepare.executeQuery();
            // Menyiapkan dan mengeksekusi query, kemudian menyimpan hasilnya dalam objek ResultSet.

            Pinjam pinjam;
            // Mendeklarasikan objek Pinjam yang akan digunakan untuk menyimpan data peminjaman.

            while (result.next()) {
                pinjam = new Pinjam(result.getInt("id_pinjam"),
                        result.getInt("harga_sewa"),
                        result.getInt("id_buku") + "_" + result.getString("judul"),
                        result.getInt("id_anggota") + "_" + result.getString("nama"),
                        result.getString("tgl_pinjam"),
                        result.getString("tgl_kembali"));
                // Membuat objek Pinjam dengan data yang diambil dari hasil query.

                listData.add(pinjam);
                // Menambahkan objek Pinjam ke dalam ObservableList.
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Menangkap dan mencetak stack trace jika terjadi kesalahan.
        }
        return listData;
        // Mengembalikan ObservableList yang berisi data peminjaman.
    }

    public static ObservableList<Pinjam> searchPinjam(String s) {
        ObservableList<Pinjam> listData = FXCollections.observableArrayList();
        // Membuat objek ObservableList yang digunakan untuk menyimpan data peminjaman hasil pencarian.
    
        String query = "SELECT * FROM peminjaman " +
                "JOIN buku ON peminjaman.id_buku = buku.id_buku " +
                "JOIN anggota ON peminjaman.id_anggota = anggota.id_anggota " +
                "WHERE nama LIKE ? OR judul LIKE ? " +
                "ORDER BY peminjaman.tgl_pinjam ASC";
        // Membuat query SQL untuk mencari data peminjaman yang mengandung nama atau judul tertentu, dengan menggabungkan tabel buku dan anggota, dan mengurutkan hasil berdasarkan tanggal pinjam secara ascending.
    
        conn = Koneksi.connectDb(); // Mendapatkan koneksi ke database.
    
        try {
            prepare = conn.prepareStatement(query);
            prepare.setString(1, "%" + s + "%");
            prepare.setString(2, "%" + s + "%");
            // Mengatur parameter pada prepared statement untuk pencarian, menggantikan placeholder (?) dengan nilai yang sesuai.
    
            result = prepare.executeQuery();
            // Mengeksekusi query dan menyimpan hasilnya dalam objek ResultSet.
    
            Pinjam pinjam;
            // Mendeklarasikan objek Pinjam yang akan digunakan untuk menyimpan data peminjaman.
    
            while (result.next()) {
                pinjam = new Pinjam(result.getInt("id_pinjam"),
                        result.getInt("harga_sewa"),
                        result.getInt("id_buku") + "_" + result.getString("judul"),
                        result.getInt("id_anggota") + "_" + result.getString("nama"),
                        result.getString("tgl_pinjam"),
                        result.getString("tgl_kembali"));
                // Membuat objek Pinjam dengan data yang diambil dari hasil query.
    
                listData.add(pinjam);
                // Menambahkan objek Pinjam ke dalam ObservableList.
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Menangkap dan mencetak stack trace jika terjadi kesalahan.
        }
        return listData;
        // Mengembalikan ObservableList yang berisi data peminjaman hasil pencarian.
    }

    public static int addDataPinjam(int idBuku, int idAnggota) {
        // Mendeklarasikan metode yang bertanggung jawab untuk menambahkan data peminjaman ke dalam database.
        String cekData = "SELECT * FROM peminjaman WHERE id_buku = " + idBuku + " and id_anggota = " + idAnggota;
        // Membuat query untuk memeriksa apakah data peminjaman sudah ada dengan id_buku dan id_anggota tertentu.
        conn = Koneksi.connectDb();
        // Mendapatkan koneksi ke database menggunakan kelas Koneksi.
        try {
            prepare = conn.prepareStatement(cekData);
            result = prepare.executeQuery();
            // Mengeksekusi query untuk memeriksa data peminjaman.
            if (result.next()) {
                return 100;
                // Jika data peminjaman sudah ada, mengembalikan nilai 100 untuk menandakan bahwa peminjaman sudah dilakukan sebelumnya.
            } else {
                String query = "INSERT INTO peminjaman " +
                        "(id_buku, id_anggota, tgl_kembali) " +
                        "VALUES (?,?,?)";
                // Membuat query SQL untuk menambahkan data peminjaman ke dalam tabel peminjaman.
                LocalDate sevenDaysLater = LocalDate.now().plusDays(7);
                // Menghitung tanggal pengembalian, tujuh hari dari tanggal peminjaman.
    
                try {
                    prepare = conn.prepareStatement(query);
                    prepare.setInt(1, idBuku);
                    prepare.setInt(2, idAnggota);
                    prepare.setDate(3, Date.valueOf(sevenDaysLater));
                    // Mengatur parameter pada prepared statement dengan nilai yang sesuai.
    
                    return prepare.executeUpdate();
                    // Mengeksekusi query untuk menambahkan data peminjaman dan mengembalikan nilai hasil eksekusi.
                } catch (Exception e) {
                    // TODO: handle exception
                    // Menangani eksepsi yang mungkin terjadi saat mengeksekusi query penambahan data.
                }
    
            }
        } catch (Exception e) {
            // TODO: handle exception
            // Menangani eksepsi yang mungkin terjadi saat mengeksekusi query pemeriksaan data peminjaman.
        }
        return 0;
        // Jika terdapat kesalahan atau data peminjaman sudah ada, mengembalikan nilai 0 untuk menandakan bahwa penambahan data peminjaman tidak berhasil.
    }
    

    public static int deletePinjam(int id_pinjam) {
        // Mendeklarasikan metode yang bertanggung jawab untuk menghapus data peminjaman dari database.
        String query = "DELETE FROM peminjaman WHERE id_pinjam = " + id_pinjam;
        // Membuat query SQL untuk menghapus data peminjaman berdasarkan id_pinjam.
        conn = Koneksi.connectDb();
        // Mendapatkan koneksi ke database menggunakan kelas Koneksi.
        try {
            prepare = conn.prepareStatement(query);
            // Menyiapkan query SQL untuk penghapusan data peminjaman.
            return prepare.executeUpdate();
            // Mengeksekusi query untuk menghapus data peminjaman dan mengembalikan nilai hasil eksekusi.
        } catch (Exception e) {
            // TODO: handle exception
            // Menangani eksepsi yang mungkin terjadi saat mengeksekusi query penghapusan data peminjaman.
        }
        return 0;
        // Jika terdapat kesalahan, mengembalikan nilai 0 untuk menandakan bahwa penghapusan data peminjaman tidak berhasil.
    }
    

    public static int updatePinjam(int id_buku, int id_pinjam, int id_anggota) {
        // Mendeklarasikan metode yang bertanggung jawab untuk memperbarui data peminjaman di database.
        String cekData = "SELECT * FROM peminjaman WHERE id_buku = " + id_buku + " and id_anggota = " + id_anggota;
        // Membuat query SQL untuk memeriksa apakah data peminjaman dengan id_buku dan id_anggota tertentu sudah ada.
        conn = Koneksi.connectDb();
        // Mendapatkan koneksi ke database menggunakan kelas Koneksi.
        try {
            prepare = conn.prepareStatement(cekData);
            // Menyiapkan query SQL untuk memeriksa keberadaan data peminjaman.
            result = prepare.executeQuery();
            // Mengeksekusi query dan mendapatkan hasilnya.
            if (result.next()) {
                // Jika data peminjaman sudah ada, mengembalikan nilai 100 untuk menandakan kesalahan.
                return 100;
            } else {
                // Jika data peminjaman belum ada, melanjutkan dengan proses pembaruan data.
                String query = "UPDATE peminjaman SET " +
                        "id_buku=? WHERE id_pinjam=?";
                // Membuat query SQL untuk memperbarui id_buku pada data peminjaman dengan id_pinjam tertentu.
                conn = Koneksi.connectDb();
                // Mendapatkan koneksi ke database menggunakan kelas Koneksi.
                try {
                    prepare = conn.prepareStatement(query);
                    // Menyiapkan query SQL untuk pembaruan data peminjaman.
                    prepare.setInt(1, id_buku);
                    // Mengatur parameter pertama (id_buku) pada query SQL.
                    prepare.setInt(2, id_pinjam);
                    // Mengatur parameter kedua (id_pinjam) pada query SQL.
    
                    return prepare.executeUpdate();
                    // Mengeksekusi query pembaruan data dan mengembalikan nilai hasil eksekusi.
                } catch (Exception e) {
                    // Menangani eksepsi yang mungkin terjadi saat mengeksekusi query pembaruan data.
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            // Menangani eksepsi yang mungkin terjadi saat memeriksa keberadaan data peminjaman.
            // TODO: handle exception
        }
        return 0;
        // Mengembalikan nilai 0 untuk menandakan bahwa pembaruan data peminjaman tidak berhasil.
    }
    
    public static int getRows() {
        // Mendeklarasikan metode yang bertanggung jawab untuk mendapatkan jumlah baris data dalam tabel peminjaman.
        String query = "SELECT COUNT(*) FROM peminjaman";
        // Membuat query SQL untuk menghitung jumlah baris dalam tabel peminjaman.
        conn = Koneksi.connectDb();
        // Mendapatkan koneksi ke database menggunakan kelas Koneksi.
        try {
            prepare = conn.prepareStatement(query);
            // Menyiapkan query SQL untuk mendapatkan jumlah baris.
            result = prepare.executeQuery();
            // Mengeksekusi query dan mendapatkan hasilnya.
            if (result.next()) {
                // Jika hasil query memiliki baris (row), mendapatkan nilai jumlah baris dari kolom pertama.
                int jumlah = result.getInt(1);
                return jumlah;
                // Mengembalikan nilai jumlah baris.
            }
        } catch (Exception e) {
            // Menangani eksepsi yang mungkin terjadi saat mengeksekusi query atau mendapatkan hasil.
            // TODO: handle exception
        }
        return 0;
        // Jika terjadi masalah atau tidak ada hasil, mengembalikan nilai 0.
    }
    
}
