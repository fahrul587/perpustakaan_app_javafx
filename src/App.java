import javafx.application.Application;//kelas javafx Ini menyediakan metode start() yang harus diimplementasikan oleh pengguna untuk memulai aplikasi.
import javafx.fxml.FXMLLoader;//Kelas ini digunakan untuk memuat file FXML dan mengembalikan root node dari hierarki objek yang dibuat.
import javafx.scene.*;// Kelas ini mewakili kontainer untuk semua konten UI dalam aplikasi JavaFX. Ini mengandung semua node UI.
import javafx.scene.image.Image;//Kelas ini digunakan untuk memuat gambar.
import javafx.stage.Stage;//Kelas ini mewakili jendela aplikasi JavaFX. Ini mengandung semua scene dan node UI.

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Mengatur tata letak (layout) dari file FXML yang disebut "Login.fxml"
        Parent root = FXMLLoader.load(getClass().getResource("view/Login.fxml"));

        // Membuat objek Scene dengan menggunakan root (tata letak)
        Scene scene = new Scene(root);

        // Menambahkan ikon aplikasi (gambar/logo) ke dalam judul jendela
        primaryStage.getIcons().add(new Image("img/logo.png"));

        // Menetapkan agar jendela tidak dapat diubah ukurannya
        primaryStage.setResizable(false);

        // Menetapkan judul jendela
        primaryStage.setTitle("Login admin");

        // Menetapkan objek Scene ke dalam primaryStage (jendela utama)
        primaryStage.setScene(scene);

        // Menampilkan jendela
        primaryStage.show();
    }

    // Metode main untuk menjalankan aplikasi
    public static void main(String[] args) {
        launch(args); // Memulai aplikasi menggunakan metode launch() dari kelas Application
    }
}
