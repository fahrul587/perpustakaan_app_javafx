package controllers;
// Package controllers: Package yang berisi kelas-kelas yang mengendalikan logika antarmuka pengguna (UI) aplikasi.

import java.io.IOException; // Import kelas IOException untuk menangani pengecualian input/output.

import CRUD.Login; // Import kelas Login dari paket CRUD untuk proses otentikasi login.
import javafx.event.ActionEvent; // Import kelas ActionEvent untuk menangani peristiwa pengguna.
import javafx.fxml.FXML; // Import anotasi FXML untuk menghubungkan elemen FXML dengan kode Java.
import javafx.fxml.FXMLLoader; // Import kelas FXMLLoader untuk memuat file FXML.
import javafx.scene.Parent; // Import kelas Parent untuk menyimpan tampilan GUI.
import javafx.scene.Scene; // Import kelas Scene untuk menangani tata letak GUI.
import javafx.scene.control.Alert; // Import kelas Alert untuk menampilkan notifikasi.
import javafx.scene.control.Alert.AlertType; // Import enum AlertType untuk jenis notifikasi.
import javafx.scene.control.Button; // Import kelas Button untuk menangani tombol.
import javafx.scene.control.PasswordField; // Import kelas PasswordField untuk menangani input password.
import javafx.scene.control.TextField; // Import kelas TextField untuk menangani input teks.
import javafx.scene.image.Image; // Import kelas Image untuk menangani gambar.
import javafx.stage.Stage; // Import kelas Stage untuk menangani jendela aplikasi.

// Kelas LoginController: Mengendalikan logika untuk tampilan login aplikasi.
public class LoginController {

    @FXML
    private Button btnLogin; // Tombol untuk memicu proses login.

    @FXML
    private PasswordField password; // Input untuk memasukkan kata sandi.

    @FXML
    private TextField username; // Input untuk memasukkan nama pengguna.

    private Stage stage; // Objek Stage untuk menangani jendela aplikasi.
    private Scene scene; // Objek Scene untuk menangani tata letak GUI.
    private Parent root; // Objek Parent untuk menyimpan tampilan GUI.

    /**
     * Menangani peristiwa klik pada tombol login.
     * 
     * @param event Peristiwa yang terjadi (klik tombol).
     * @throws IOException Menangani pengecualian input/output.
     */
    public void loginAdmin(ActionEvent event) throws IOException {
        Alert alert; // Objek Alert untuk menampilkan notifikasi.

        // Validasi input pengguna.
        if (username.getText().isEmpty() || password.getText().isEmpty()) {
            alert = new Alert(AlertType.ERROR); // Notifikasi error jika ada kolom yang kosong.
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Tolong isi semua kolom");
            alert.showAndWait();
        } else {
            // Memeriksa keberhasilan login.
            boolean status = Login.getLogin(username.getText(), password.getText());
            if (status) {
                alert = new Alert(AlertType.INFORMATION); // Notifikasi berhasil jika login sukses.
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Login");
                alert.showAndWait();

                // Menutup jendela login dan membuka tampilan baru.
                try {
                    btnLogin.getScene().getWindow().hide(); // Menutup tampilan login.
                    root = FXMLLoader.load(getClass().getResource("/view/Index.fxml")); // Memuat tampilan baru.
                    stage = new Stage(); // Membuat stage baru.
                    scene = new Scene(root); // Membuat scene baru yang berisi tampilan baru.
                    stage.getIcons().add(new Image("/img/logo.png")); // Menambahkan ikon pada aplikasi.
                    stage.setMinHeight(640); // Menetapkan tinggi minimum aplikasi.
                    stage.setMinWidth(1044); // Menetapkan lebar minimum aplikasi.
                    stage.setTitle("The Booktown app"); // Menetapkan judul aplikasi.
                    stage.setScene(scene); // Menetapkan tampilan pada stage.
                    stage.show(); // Menampilkan aplikasi baru.
                } catch (Exception e) {
                    // TODO: handle exception
                }
            } else {
                alert = new Alert(AlertType.ERROR); // Notifikasi error jika login gagal.
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Username / Password Salah!");
                alert.showAndWait();
            }
        }
    }
}
