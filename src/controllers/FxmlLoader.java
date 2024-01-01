package controllers;
// Package controllers: Package yang berisi kelas-kelas yang bertanggung jawab untuk mengendalikan logika aplikasi.

import javafx.fxml.FXMLLoader; // Import kelas FXMLLoader dari paket javafx.fxml untuk memuat file FXML.
import javafx.scene.layout.BorderPane; // Import kelas BorderPane dari paket javafx.scene.layout untuk menangani tata letak GUI.

// Kelas FxmlLoader: Bertanggung jawab untuk memuat tampilan dari file FXML ke dalam BorderPane.
public class FxmlLoader {
    private BorderPane view; // Variabel instance untuk menyimpan tampilan BorderPane yang akan dimuat.

    /**
     * Mengambil dan memuat tampilan dari file FXML sesuai dengan nama file yang diberikan.
     *
     * @param fileName Nama file FXML tanpa ekstensi (contoh: "MainView" untuk "MainView.fxml")
     * @return BorderPane yang berisi tampilan yang dimuat dari file FXML.
     */
    public BorderPane getPage(String fileName) {
        try {
            // Memuat tampilan dari file FXML menggunakan FXMLLoader.
            // File FXML diharapkan berada di dalam folder "view" dalam proyek.
            view = FXMLLoader.load(getClass().getResource("/view/" + fileName + ".fxml"));
        } catch (Exception e) {
            // Menangkap exception jika terjadi kesalahan saat memuat file FXML.
            e.printStackTrace();
        }
        // Mengembalikan tampilan yang telah dimuat.
        return view;
    }
}
