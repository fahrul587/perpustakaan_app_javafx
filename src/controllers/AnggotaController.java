package controllers;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import CRUD.CrudAnggota;
import de.jensd.fx.glyphs.fontawesome.*;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.Cursor;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import model.Anggota;

public class AnggotaController implements Initializable {
    @FXML
    private TableColumn<Anggota, String> anggota_col_action;//untuk mengambil data pada elemen tableColumn

    @FXML
    private TableColumn<Anggota, String> anggota_col_alamat;

    @FXML
    private TableColumn<Anggota, String> anggota_col_email;

    @FXML
    private TableColumn<Anggota, String> anggota_col_id;

    @FXML
    private TableColumn<Anggota, String> anggota_col_nama;

    @FXML
    private TableColumn<Anggota, String> anggota_col_tgl;

    @FXML
    private TableColumn<Anggota, String> anggota_col_wa;

    @FXML
    private Button btn_submit_anggota;

    @FXML
    private TextField form_alamat;

    @FXML
    private TextField form_email;

    @FXML
    private TextField form_idAnggota;

    @FXML
    private TextField form_namaAnggota;

    @FXML
    private TextField form_wa;

    @FXML
    private Label header_title;

    @FXML
    private TextField search_anggota;

    @FXML
    private TableView<Anggota> table_anggota;

    @FXML
    private AnchorPane tb_anggota;

    @FXML
    private AnchorPane tb_form_anggota;

    private Anggota anggotaUpdate;

    @FXML
    void liveSearch(KeyEvent event) {//digunakan untuk form pencarian
        showDataAnggota(CrudAnggota.searchAnggota(search_anggota.getText()));//menampilkan data yg kita ketikan
    }

    public void setFormUpdate() {//menampilkan data yang ingin diupdate di form update
        form_idAnggota.setText(String.valueOf(anggotaUpdate.getId()));//memberi value pada textfield id anggota
        form_namaAnggota.setText(anggotaUpdate.getNama());//memberi value pada textfield nama anggota
        form_alamat.setText(anggotaUpdate.getAlamat());//memberi value pada textfield alamat anggota
        form_email.setText(anggotaUpdate.getEmail());//memberi value pada textfield email anggota
        form_wa.setText(anggotaUpdate.getWa());//memberi value pada textfield whatsaap anggota
    }

    public void resetForm() {//fungsi utk mereset form anggota
        if (form_idAnggota.getText().isEmpty()) {//kondisi jika textfield id anggota null / kosong
            form_namaAnggota.clear();//mereset inputan nama anggota
            form_alamat.clear();//mereset inputan alamat anggota
            form_email.clear();//mereset inputan email anggota
            form_wa.clear();//mereset inputan whatsaap anggota
        } else setFormUpdate(); //jika kondisi tidak terpenuhi -> mejalankan fungsi setFormUpdate
    }

    public boolean formValidation() {
        Alert alert;//mendeklarasikan variabel Alert
        if (form_namaAnggota.getText().isEmpty() ||
                form_alamat.getText().isEmpty() ||
                form_email.getText().isEmpty() ||
                form_wa.getText().isEmpty()) {//kondisi jika semua textfield pada form null / kosong
            alert = new Alert(AlertType.ERROR);//membuat alert type error
            alert.setTitle("Error Message");//set judul notifikasi
            alert.setHeaderText(null);
            alert.setContentText("Tolong isi semua kolom");//set isi notifikasi
            alert.showAndWait();//menahan notifikasi
            return false;//fungsi mengembalikan nilai false
        }
        return true; //jika kondisi tdk terpenuhi akan mengembalikan nilai true
    }

    public void submitButton() {//fungsi yang berjalan saat tombol submit diklik
        if (formValidation()) {//jika form validation mengembalikan nilai true
            if (form_idAnggota.getText().isEmpty()) tambahAnggota();//jika id anggota pada textfield null / kosong -> menjalankan fungsi tambah
            else updateAnggota();//jika kodisi tidak terpenuhi -> menjalankan fungsi update
            showTableAnggota();//menampilkan data tabel yang baru
            form_idAnggota.clear();//mereset textfield id anggota
            resetForm();//mereset semua data pada form
        }
    }

    public void tambahAnggota() {//fungsi utk menambah anggota ke database
        Alert alert;//deklarasi variabel alert
        int addAnggota = CrudAnggota.addAnggota(
                new Anggota(
                        null,
                        form_namaAnggota.getText(),
                        form_alamat.getText(),
                        form_email.getText(),
                        form_wa.getText(),
                        null, null));//melakukan insert ke tabel anggota dengan mengirim data Anggota baru
        if (addAnggota == 1) {//jika anggota berhasil ditambah ke database
            alert = new Alert(AlertType.INFORMATION);//set notifikasi informasi
            alert.setTitle("Information Message");//set judul notifikasi
            alert.setHeaderText(null);
            alert.setContentText("Anggota berhasil ditambah");//set isi notifikasi
            alert.showAndWait();//menahan notifikasi
        }
    }

    public void updateAnggota() {//fungsi utk mengupdate data anggota ke database
        Alert alert;//deklarasi variabel alert
        int upAnggota = CrudAnggota.updateAngota(
                new Anggota(
                        Integer.parseInt(form_idAnggota.getText()),
                        form_namaAnggota.getText(),
                        form_alamat.getText(),
                        form_email.getText(),
                        form_wa.getText(),
                        null, null));//melakukan update ke tabel anggota dengan mengirim data Anggota baru
        if (upAnggota == 1) {//jika anggota berhasil diupdate ke database
            alert = new Alert(AlertType.INFORMATION);//set notifikasi informasi
            alert.setTitle("Information Message");//set judul notifikasi
            alert.setHeaderText(null);
            alert.setContentText("anggota berhasil diubah");//set isi notifikasi
            alert.showAndWait();//menahan notifikasi
        }
    }

    public void showDataAnggota(ObservableList<Anggota> listAnggota) {//utk menampilkan data anggota ke dalam tabel

        anggota_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));//set properti id dari class anggota
        anggota_col_nama.setCellValueFactory(new PropertyValueFactory<>("nama"));//set properti nama dari class anggota
        anggota_col_alamat.setCellValueFactory(new PropertyValueFactory<>("alamat"));//set properti alamat dari class anggota
        anggota_col_email.setCellValueFactory(new PropertyValueFactory<>("email"));//set properti email dari class anggota
        anggota_col_wa.setCellValueFactory(new PropertyValueFactory<>("wa"));//set properti wa dari class anggota
        anggota_col_tgl.setCellValueFactory(new PropertyValueFactory<>("tanggal"));//set properti tanggal dari class anggota

        Callback<TableColumn<Anggota, String>, TableCell<Anggota, String>> cellFoctory = (
                TableColumn<Anggota, String> param) -> {
            final TableCell<Anggota, String> cell = new TableCell<Anggota, String>() {//membuat beberapa icon action
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);//membuat icon untuk delete
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.EDIT);//membuat icon untuk edit

                        deleteIcon.setFill(new Color(0.6316, 0.1345, 0.1345, 1.0));//memberi warna pada icon
                        deleteIcon.setStyle("-glyph-size:25px;");//memberi ukuran pada icon
                        deleteIcon.setCursor(Cursor.HAND);//memberi tipe cursor pada icon

                        editIcon.setFill(new Color(0.0478, 0.6447, 0.0677, 1.0));//memberi warna pada icon
                        editIcon.setStyle("-glyph-size:25px;");//memberi ukuran pada icon
                        editIcon.setCursor(Cursor.HAND);//memberi tipe cursor pada icon

                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {//saat tombol delete diklik
                            Alert alert;//deklarasi alert
                            Anggota anggota = table_anggota.getSelectionModel().getSelectedItem();//mengambil data anggota yg diklik
                            alert = new Alert(AlertType.CONFIRMATION);//set notifikasi konfirmasi
                            alert.setTitle("Confirm Message");
                            alert.setHeaderText(null);
                            alert.setContentText("Anda yakin ingin menghapus '" + anggota.getNama() + "'?");//set isi notifikasi
                            Optional<ButtonType> buttonType = alert.showAndWait();//menyimpan data konfirmasi

                            if (buttonType.isPresent() && buttonType.get().equals(ButtonType.OK)) {//jika konfirmasi OK
                                if (CrudAnggota.deleteAnggota(anggota.getId()) == 1) {//jika delete anggota mengembalikan nilai bukan 0
                                    alert = new Alert(AlertType.INFORMATION);
                                    alert.setTitle("Information Message");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Anggota berhasil dihapus");
                                    alert.showAndWait();
                                    showDataAnggota(CrudAnggota.getDataAnggota(""));
                                } else {//jika kondisi tidak terpenuhi berarti gagal menghapus anggota
                                    alert = new Alert(AlertType.ERROR);
                                    alert.setTitle("Error Message");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Anggota gagal dihapus");
                                    alert.showAndWait();
                                }
                            }
                        });

                        editIcon.setOnMouseClicked((MouseEvent event) -> {//jika tombol edit diklik
                            Alert alert;
                            Anggota anggota = table_anggota.getSelectionModel().getSelectedItem();//mengambil data yang diklik
                            alert = new Alert(AlertType.CONFIRMATION);//set notifikasi konfirmasi
                            alert.setTitle("Confirm Message");
                            alert.setHeaderText(null);
                            alert.setContentText("Lanjut Edit '" + anggota.getNama() + "'?");//set isi notifikasi
                            Optional<ButtonType> buttonType = alert.showAndWait();//menyimpan data konfirmasi
                            if (buttonType.isPresent() && buttonType.get().equals(ButtonType.OK)) {//jika konfirmasi OK
                                anggotaUpdate = anggota;//set variabel anggotaUpdate -> anggota yg diklik
                                setFormUpdate();//menjalankan funsi setFormUpdate
                                showFormAnggota();//pindah halaman ke form
                            }
                        });

                        HBox managebtn = new HBox(editIcon, deleteIcon);//membuat sebuah element parent untuk menampung 2 icon
                        managebtn.setStyle("-fx-alignment:center");//agar icon berada ditengan kolomnya
                        managebtn.setSpacing(20);//memberi jarak antar icon sebesar 20px

                        setGraphic(managebtn);//menampilkan icon

                        setText(null);

                    }
                }

            };

            return cell;//mengembalikan cell kolom icon
        };

        anggota_col_action.setCellFactory(cellFoctory);//mengisi kolom action tabel dengan icon-icon
        table_anggota.setItems(listAnggota);//set data tabel dari variabel listAnggota

    }

    public void showTableAnggota() {//menampilkan halaman tabel anggota
        showDataAnggota(CrudAnggota.getDataAnggota(""));//menampilkan data anggota
        tb_anggota.setVisible(true);//membuat halaman tabel anggota terlihat
        tb_form_anggota.setVisible(false);//membuat halaman form anggota menjadi tdk terlihat
        header_title.setText("Tabel Anggota");//merubah judul header halaman
    }

    public void showFormAnggota() {//menampilkan halaman form anggota
        tb_anggota.setVisible(false);//membuat halaman tabel anggota menjadi tdk terlihat
        tb_form_anggota.setVisible(true);//membuat halaman form anggota terlihat
        if (form_idAnggota.getText().isEmpty()) {//jika textfield pada id anggota null / kosong
            header_title.setText("Form Tambah Anggota");//merubah judul header halaman menjadi tambah
        } else {
            header_title.setText("Form Edit Anggota");//merubah judul header halaman menjadi edit
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        showTableAnggota();//menjalankan fungsi saat pertama kali halaman ter load
    }
}
