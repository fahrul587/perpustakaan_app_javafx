package CRUD;

import java.sql.*; // Mengimpor kelas-kelas yang dibutuhkan dari paket java.sql

public class Login {
    private static PreparedStatement prepare;//deklarasi fungsi dari java.sql
    private static Connection conn;//deklarasi fungsi dari java.sql
    private static ResultSet result;//deklarasi fungsi dari java.sql

    public static boolean getLogin(String username, String password) {
        boolean status = false;
        String query = "SELECT * FROM admin WHERE username = ? and password = ?";
        // Query SQL untuk mendapatkan data admin berdasarkan username dan password yang diberikan.

        conn = Koneksi.connectDb();
        // Membuka koneksi ke database menggunakan metode connectDb() dari kelas Koneksi.

        try {
            prepare = conn.prepareStatement(query);
            // Mempersiapkan pernyataan SQL menggunakan koneksi ke database.

            prepare.setString(1, username);
            prepare.setString(2, password);
            // Mengatur nilai parameter pada pernyataan SQL sesuai dengan nilai yang diberikan.

            result = prepare.executeQuery();
            // Menjalankan pernyataan SQL dan mendapatkan hasilnya.

            return result.next();
            // Mengembalikan nilai true jika hasil query mengandung baris data (admin ditemukan), false jika tidak.
        } catch (Exception e) {
            System.out.println("Error : koneksi gagal");
            // Menangkap dan mencetak stack trace jika terjadi kesalahan.
        }
        return status;
        // Mengembalikan nilai status (default: false) jika terjadi kesalahan.
    }
}

