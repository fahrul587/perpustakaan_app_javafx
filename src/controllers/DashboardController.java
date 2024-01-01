package controllers; // Package untuk menyimpan kelas-kelas yang mengontrol tampilan

import java.net.URL; // Import kelas URL untuk bekerja dengan URL
import java.util.ResourceBundle; // Import kelas ResourceBundle untuk mengelola sumber daya aplikasi

import CRUD.CrudAnggota; // Import kelas CrudAnggota untuk operasi pada tabel Anggota
import CRUD.CrudBuku; // Import kelas CrudBuku untuk operasi pada tabel Buku
import CRUD.CrudKategori; // Import kelas CrudKategori untuk operasi pada tabel Kategori
import CRUD.CrudPinjam; // Import kelas CrudPinjam untuk operasi pada tabel Pinjam
import javafx.fxml.FXML; // Import anotasi FXML untuk mendeklarasikan elemen GUI dari file FXML
import javafx.fxml.Initializable; // Import kelas Initializable untuk inisialisasi kontroler
import javafx.scene.control.Label; // Import kelas Label untuk menampilkan teks
import javafx.scene.input.MouseEvent; // Import kelas MouseEvent untuk menangani kejadian mouse
import javafx.scene.layout.BorderPane; // Import kelas BorderPane untuk menangani layout
import javafx.scene.layout.GridPane; // Import kelas GridPane untuk menangani layout

public class DashboardController implements Initializable {
    @FXML
    private Label anggota_terdaftar; // Label untuk menampilkan jumlah anggota terdaftar

    @FXML
    private GridPane btn_anggota; // GridPane untuk tombol akses ke halaman anggota

    @FXML
    private GridPane btn_buku; // GridPane untuk tombol akses ke halaman buku

    @FXML
    private GridPane btn_kategori; // GridPane untuk tombol akses ke halaman kategori

    @FXML
    private GridPane btn_pinjam; // GridPane untuk tombol akses ke halaman peminjaman

    @FXML
    private Label buku_terdaftar; // Label untuk menampilkan jumlah buku terdaftar

    @FXML
    private Label kategori_terdaftar; // Label untuk menampilkan jumlah kategori terdaftar

    @FXML
    private Label pinjaman_terdaftar; // Label untuk menampilkan jumlah peminjaman terdaftar

    @FXML
    private Label header_title; // Label untuk menampilkan judul header

    // Fungsi untuk menampilkan data pada dashboard
    public void showDataDashboard() {
        header_title.setText("Dashboard"); // Mengatur judul dashboard
        buku_terdaftar.setText(String.valueOf(CrudBuku.getRows())); // Menampilkan jumlah buku terdaftar
        anggota_terdaftar.setText(String.valueOf(CrudAnggota.getRows())); // Menampilkan jumlah anggota terdaftar
        kategori_terdaftar.setText(String.valueOf(CrudKategori.getRows())); // Menampilkan jumlah kategori terdaftar
        pinjaman_terdaftar.setText(String.valueOf(CrudPinjam.getRows())); // Menampilkan jumlah peminjaman terdaftar
    }

    @FXML
    void switchDisplay(MouseEvent event) {
        // Mendapatkan BorderPane utama dari elemen terkait tombol
        BorderPane mainContent = (BorderPane) btn_buku.getParent().getParent().getParent().getParent().getParent();

        // Menampilkan tampilan halaman yang sesuai dengan tombol yang diklik
        if (event.getSource() == btn_buku) {
            FxmlLoader object = new FxmlLoader();
            BorderPane view = object.getPage("PageBooks");
            mainContent.setCenter(view);
        } else if (event.getSource() == btn_anggota) {
            FxmlLoader object = new FxmlLoader();
            BorderPane view = object.getPage("PageAnggota");
            mainContent.setCenter(view);
        } else if (event.getSource() == btn_pinjam) {
            FxmlLoader object = new FxmlLoader();
            BorderPane view = object.getPage("PagePinjam");
            mainContent.setCenter(view);
        } else if (event.getSource() == btn_kategori) {
            FxmlLoader object = new FxmlLoader();
            BorderPane view = object.getPage("PageKategori");
            mainContent.setCenter(view);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Menampilkan data saat inisialisasi kontroler
        showDataDashboard();
    }
}
