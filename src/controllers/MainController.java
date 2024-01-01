package controllers;
// Package controllers: Package yang berisi kelas-kelas yang bertanggung jawab untuk mengendalikan logika aplikasi.

import java.net.URL; // Import kelas URL untuk menangani URL.
import java.util.ResourceBundle; // Import kelas ResourceBundle untuk mengelola sumber daya lokal.
import javafx.event.Event; // Import kelas Event dari paket javafx untuk menangani peristiwa GUI.
import javafx.fxml.FXML; // Import anotasi FXML untuk menghubungkan elemen FXML dengan kode Java.
import javafx.fxml.Initializable; // Import antarmuka Initializable untuk menentukan metode initialize.
import javafx.scene.control.Button; // Import kelas Button untuk menangani tombol.
import javafx.scene.layout.BorderPane; // Import kelas BorderPane untuk menangani tata letak GUI.

// Kelas MainController: Bertanggung jawab untuk mengontrol tampilan utama aplikasi dan menanggapi peristiwa GUI.
public class MainController implements Initializable {
    // Elemen Dashboard
    @FXML
    private Button btn_buku; // Tombol untuk menampilkan halaman buku.

    @FXML
    private Button btn_pinjam; // Tombol untuk menampilkan halaman pinjaman.

    @FXML
    private Button btn_anggota; // Tombol untuk menampilkan halaman anggota.

    @FXML
    private Button btn_dashboard; // Tombol untuk menampilkan halaman dashboard.

    @FXML
    private Button btn_kategori; // Tombol untuk menampilkan halaman kategori.

    @FXML
    private BorderPane mainContent; // BorderPane utama yang menampilkan konten aplikasi.

    /**
     * Menampilkan halaman dashboard pada BorderPane utama.
     */
    public void showDashboard() {
        FxmlLoader object = new FxmlLoader(); // Objek untuk memuat tampilan dari file FXML.
        BorderPane view = object.getPage("Dashboard"); // Memuat tampilan Dashboard.fxml.
        mainContent.setCenter(view); // Menetapkan tampilan sebagai pusat BorderPane.
    }

    /**
     * Menampilkan halaman buku pada BorderPane utama.
     */
    public void showTableBuku() {
        FxmlLoader object = new FxmlLoader();
        BorderPane view = object.getPage("PageBooks");
        mainContent.setCenter(view);
    }

    /**
     * Menampilkan halaman anggota pada BorderPane utama.
     */
    public void showTableAnggota() {
        FxmlLoader object = new FxmlLoader();
        BorderPane view = object.getPage("PageAnggota");
        mainContent.setCenter(view);
    }

    /**
     * Menampilkan halaman pinjaman pada BorderPane utama.
     */
    public void showTablePinjaman() {
        FxmlLoader object = new FxmlLoader();
        BorderPane view = object.getPage("PagePinjam");
        mainContent.setCenter(view);
    }

    /**
     * Menampilkan halaman kategori pada BorderPane utama.
     */
    public void showTableKategori() {
        FxmlLoader object = new FxmlLoader();
        BorderPane view = object.getPage("PageKategori");
        mainContent.setCenter(view);
    }

    /**
     * Mengganti tampilan utama berdasarkan tombol yang diklik.
     * 
     * @param event Peristiwa yang terjadi (klik tombol).
     */
    public void switchDisplay(Event event) {
        if (event.getSource() == btn_dashboard) showDashboard();
        else if (event.getSource() == btn_buku) showTableBuku();
        else if (event.getSource() == btn_anggota) showTableAnggota();
        else if (event.getSource() == btn_pinjam) showTablePinjaman();
        else if (event.getSource() == btn_kategori) showTableKategori();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showDashboard(); // Menampilkan halaman dashboard saat kontroler pertama kali diload.
    }
}
