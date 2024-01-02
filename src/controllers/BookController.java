package controllers;

import java.net.URL;
import java.util.*;

import CRUD.CrudBuku;
import CRUD.CrudKategori;
import de.jensd.fx.glyphs.fontawesome.*;
import javafx.collections.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import model.Buku;
import model.Kategori;

public class BookController implements Initializable {
    @FXML
    private TableColumn<Buku, String> buku_col_action;

    @FXML
    private TableColumn<Buku, String> buku_col_idBuku;

    @FXML
    private TableColumn<Buku, String> buku_col_judul;

    @FXML
    private TableColumn<Buku, String> buku_col_kategori;

    @FXML
    private TableColumn<Buku, String> buku_col_penerbit;

    @FXML
    private TableColumn<Buku, String> buku_col_penulis;

    @FXML
    private TableColumn<Buku, String> buku_col_stok;

    @FXML
    private TableColumn<Buku, String> buku_col_terbit;

    @FXML
    private TableColumn<Buku, String> buku_col_sewa;

    @FXML
    private TextField search_book;

    @FXML
    private TableView<Buku> table_buku;

    @FXML
    private AnchorPane tb_buku;

    @FXML
    private Button btn_addKategori;

    @FXML
    private TextField form_addKategori;

    @FXML
    private TextField form_idBuku;

    @FXML
    private TextField form_judul;

    @FXML
    private TextField form_sewa;

    @FXML
    private ComboBox<String> form_kategori;

    @FXML
    private TextField form_penerbit;

    @FXML
    private TextField form_penulis;

    @FXML
    private TextField form_stok;

    @FXML
    private TextField form_tahun;

    @FXML
    private AnchorPane tb_form_buku;

    @FXML
    private Label header_title;

    @FXML
    void tambahKategori(ActionEvent event) {
        Alert alert;
        if (form_addKategori.getText().isEmpty()) {
            return;
        } else {
            if (CrudKategori.addKategori(form_addKategori.getText()) == 1) {
                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Kategori berhasil disimpan");
                alert.showAndWait();
            } else {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("kategori sudah terdaftar");
                alert.showAndWait();
            }
            loadKategori();
            form_addKategori.clear();
        }
    }

    private Buku bookUpdate;

    public void showDataBuku(ObservableList<Buku> listBuku) {

        buku_col_sewa.setCellValueFactory(new PropertyValueFactory<>("sewa"));
        buku_col_judul.setCellValueFactory(new PropertyValueFactory<>("judul"));
        buku_col_kategori.setCellValueFactory(new PropertyValueFactory<>("kategori"));
        buku_col_penulis.setCellValueFactory(new PropertyValueFactory<>("penulis"));
        buku_col_penerbit.setCellValueFactory(new PropertyValueFactory<>("penerbit"));
        buku_col_stok.setCellValueFactory(new PropertyValueFactory<>("stok"));
        buku_col_idBuku.setCellValueFactory(new PropertyValueFactory<>("id"));
        buku_col_terbit.setCellValueFactory(new PropertyValueFactory<>("terbit"));

        Callback<TableColumn<Buku, String>, TableCell<Buku, String>> cellFoctory = (
                TableColumn<Buku, String> param) -> {
            final TableCell<Buku, String> cell = new TableCell<Buku, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.EDIT);

                        deleteIcon.setFill(new Color(0.6316, 0.1345, 0.1345, 1.0));
                        deleteIcon.setStyle("-glyph-size:25px;");
                        deleteIcon.setCursor(Cursor.HAND);

                        editIcon.setFill(new Color(0.0478, 0.6447, 0.0677, 1.0));
                        editIcon.setStyle("-glyph-size:25px;");
                        editIcon.setCursor(Cursor.HAND);

                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                            Alert alert;
                            Buku buku = table_buku.getSelectionModel().getSelectedItem();
                            alert = new Alert(AlertType.CONFIRMATION);
                            alert.setTitle("Confirm Message");
                            alert.setHeaderText(null);
                            alert.setContentText("Anda yakin ingin menghapus '" + buku.getJudul() +
                                    "'?");
                            Optional<ButtonType> buttonType = alert.showAndWait();

                            if (buttonType.isPresent() && buttonType.get().equals(ButtonType.OK)) {
                                if (CrudBuku.deleteBuku(buku.getId()) == 1) {
                                    alert = new Alert(AlertType.INFORMATION);
                                    alert.setTitle("Information Message");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Buku berhasil dihapus");
                                    alert.showAndWait();
                                    showDataBuku(CrudBuku.addDataBuku(""));
                                } else {
                                    alert = new Alert(AlertType.ERROR);
                                    alert.setTitle("Error Message");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Buku gagal dihapus");
                                    alert.showAndWait();
                                }
                            }
                        });

                        editIcon.setOnMouseClicked((MouseEvent event) -> {
                            Alert alert;
                            Buku buku = table_buku.getSelectionModel().getSelectedItem();
                            alert = new Alert(AlertType.CONFIRMATION);
                            alert.setTitle("Confirm Message");
                            alert.setHeaderText(null);
                            alert.setContentText("Lanjut Edit '" + buku.getJudul() + "'?");
                            Optional<ButtonType> buttonType = alert.showAndWait();
                            if (buttonType.isPresent() && buttonType.get().equals(ButtonType.OK)) {
                                bookUpdate = buku;
                                setFormUpdate();
                                showFormTambahBuku();
                            }
                        });

                        HBox managebtn = new HBox(editIcon, deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        managebtn.setSpacing(15);
                        setGraphic(managebtn);
                        setText(null);
                    }
                }
            };
            return cell;
        };

        buku_col_action.setCellFactory(cellFoctory);
        table_buku.setItems(listBuku);

    }

    public void showTableBuku() {
        showDataBuku(CrudBuku.addDataBuku(""));
        tb_buku.setVisible(true);
        tb_form_buku.setVisible(false);
        header_title.setText("Tabel Buku");
    }

    public void showFormTambahBuku() {
        loadKategori();
        tb_buku.setVisible(false);
        tb_form_buku.setVisible(true);
        if (form_idBuku.getText().isEmpty()) {
            header_title.setText("Form Tambah Buku");
        } else {
            header_title.setText("Form Edit Buku");
        }
    }

    public void loadKategori() {
        ObservableList<Kategori> listKategori = CrudKategori.getKategori();
        String[] coba = new String[listKategori.size()];
        int i = 0;
        for (Kategori kategori : listKategori) {
            coba[i] = kategori.getJoin();
            i++;
        }
        ObservableList<String> items = FXCollections.observableArrayList(coba);
        form_kategori.setItems(items);
    }

    public void setFormUpdate() {
        form_idBuku.setText(String.valueOf(bookUpdate.getId()));
        form_kategori.setValue(bookUpdate.getKategori());
        form_judul.setText(bookUpdate.getJudul());
        form_penulis.setText(bookUpdate.getPenulis());
        form_penerbit.setText(bookUpdate.getPenerbit());
        form_tahun.setText(bookUpdate.getTerbit());
        form_stok.setText(String.valueOf(bookUpdate.getStok()));
        form_sewa.setText(String.valueOf(bookUpdate.getSewa()));
    }

    public void resetForm() {
        if (form_idBuku.getText().isEmpty()) {
            form_idBuku.setText("");
            form_kategori.getSelectionModel().clearSelection();
            form_kategori.setValue(null);
            form_judul.setText("");
            form_penulis.setText("");
            form_penerbit.setText("");
            form_tahun.setText("");
            form_stok.setText("");
            form_sewa.setText("");
        } else
            setFormUpdate();
    }

    public boolean formBukuValidation() {
        Alert alert;
        if (form_judul.getText().isEmpty()
                || form_penerbit.getText().isEmpty()
                || form_penulis.getText().isEmpty()
                || form_stok.getText().isEmpty()
                || form_tahun.getText().isEmpty()
                || form_sewa.getText().isEmpty()
                || form_kategori.getValue() == null) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Tolong isi semua kolom");
            alert.showAndWait();
            return false;
        } else if ((form_idBuku
                .getText().isEmpty() && CrudBuku.addDataBuku("WHERE judul = '" + form_judul.getText() + "'").size() > 0)
                ||
                (CrudBuku.addDataBuku(
                        "WHERE judul = '" + form_judul.getText() + "' and id_buku != '" + form_idBuku.getText() + "'")
                        .size() > 0)) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Buku sudah terdaftar");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    public void submitButton() {

        if (formBukuValidation()) {
            if (form_idBuku.getText().isEmpty())
                tambahBuku();
            else
                updateBuku();
            showTableBuku();
            form_idBuku.setText("");
            resetForm();
        }
    }

    public void tambahBuku() {
        Alert alert;
        int addBuku = CrudBuku.addBuku(new Buku(
                null,
                form_kategori.getValue().split("_")[0],
                form_judul.getText(),
                form_penulis.getText(),
                form_penerbit.getText(),
                form_tahun.getText(),
                Integer.parseInt(form_stok.getText()),
                Integer.parseInt(form_sewa.getText())));
        if (addBuku == 1) {
            alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Buku berhasil ditambah");
            alert.showAndWait();
            resetForm();
        }
    }

    public void updateBuku() {
        Alert alert;
        int upBuku = CrudBuku.updateBuku(new Buku(
                Integer.parseInt(form_idBuku.getText()),
                form_kategori.getValue().split("_")[0],
                form_judul.getText(),
                form_penulis.getText(),
                form_penerbit.getText(),
                form_tahun.getText(),
                Integer.parseInt(form_stok.getText()),
                Integer.parseInt(form_sewa.getText())));
        if (upBuku == 1) {
            alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Buku berhasil diubah");
            alert.showAndWait();
        }
    }

    public void liveSearchBook(Event event) {
        if (event.getSource() == search_book) {
            showDataBuku(CrudBuku.searchBuku(search_book.getText()));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        showTableBuku();

    }
}
