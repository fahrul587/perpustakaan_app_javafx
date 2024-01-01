import javafx.application.Application; // Mengimpor kelas Application dari paket javafx
import javafx.fxml.FXMLLoader; // Mengimpor kelas FXMLLoader dari paket javafx.fxml
import javafx.scene.*; // Mengimpor kelas Scene dari paket javafx.scene
import javafx.scene.image.Image; // Mengimpor kelas Image dari paket javafx.scene.image
import javafx.stage.Stage; // Mengimpor kelas Stage dari paket javafx.stage

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
