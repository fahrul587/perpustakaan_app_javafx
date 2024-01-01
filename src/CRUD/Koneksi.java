package CRUD; // Mendeklarasikan bahwa kelas ini berada dalam paket CRUD.

import java.sql.*; // Mengimpor kelas-kelas yang dibutuhkan dari paket java.sql.

public class Koneksi {
    public static Connection connectDb() { // adalah function yang digunakan untuk membuat koneksi dari database;
        try {
            final String dbName = "jdbc:mysql://localhost/db_perpustakaan";
            // String dbName menyimpan informasi tentang lokasi dan nama database, serta protokol dan host (localhost) yang digunakan.

            Connection conn = DriverManager.getConnection(dbName, "root", "");
            // Membuat koneksi ke database menggunakan DriverManager.getConnection(). 
            // Parameters: (dbName, username, password) - Menentukan URL database, nama pengguna, dan kata sandi.

            return conn;
            // Mengembalikan objek Connection yang mewakili koneksi ke database.
        } catch (Exception e) {
            e.printStackTrace();
            // Menangkap dan mencetak stack trace jika terjadi kesalahan.
        }
        return null;
        // Mengembalikan nilai null jika terjadi kesalahan.
    }
}
