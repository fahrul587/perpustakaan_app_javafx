package controllers;

import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import CRUD.CrudAnggota;
import CRUD.CrudBuku;
import CRUD.CrudPinjam;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Anggota;
import model.Buku;
import model.Pinjam;

public class PinjamController implements Initializable {
    @FXML
    private TableColumn<Buku, String> buku_col_action;

    @FXML
    private TableColumn<Buku, String> buku_col_id;

    @FXML
    private TableColumn<Buku, String> buku_col_judul;

    @FXML
    private TableColumn<Buku, String> buku_col_kategori;

    @FXML
    private TableColumn<Buku, String> buku_col_sewa;

    @FXML
    private TextField form_id_peminjam;

    @FXML
    private TextField form_id_pinjam;

    @FXML
    private TextField form_nama_peminjam;

    @FXML
    private Label header_title;

    @FXML
    private Label detail_denda;

    @FXML
    private Label detail_total;

    @FXML
    private TableColumn<Buku, String> pbuku_col_action;

    @FXML
    private TableColumn<Buku, String> pbuku_col_id;

    @FXML
    private TableColumn<Buku, String> pbuku_col_judul;

    @FXML
    private TableColumn<Buku, String> pbuku_col_kategori;

    @FXML
    private TableColumn<Buku, String> pbuku_col_sewa;

    @FXML
    private TableColumn<Anggota, String> peminjam_col_action;

    @FXML
    private TableColumn<Anggota, String> peminjam_col_id;

    @FXML
    private TableColumn<Anggota, String> peminjam_col_nama;

    @FXML
    private TableColumn<Anggota, String> peminjam_col_sisa;

    @FXML
    private TableColumn<Pinjam, String> pinjam_col_action;

    @FXML
    private TableColumn<Pinjam, String> pinjam_col_anggota;

    @FXML
    private TableColumn<Pinjam, String> pinjam_col_buku;

    @FXML
    private TableColumn<Pinjam, String> pinjam_col_id;

    @FXML
    private TableColumn<Pinjam, String> pinjam_col_kembali;

    @FXML
    private TableColumn<Pinjam, String> pinjam_col_pinjam;

    @FXML
    private TextField search_form_anggota;

    @FXML
    private TextField search_form_pinjam;

    @FXML
    private TextField search_form_buku;

    @FXML
    private TableView<Buku> table_buku;

    @FXML
    private TableView<Buku> table_pBuku;

    @FXML
    private TableView<Anggota> table_peminjam;

    @FXML
    private TableView<Pinjam> table_peminjaman;

    @FXML
    private AnchorPane tb_form_pinjam;

    @FXML
    private AnchorPane tb_peminjaman;

    @FXML
    private AnchorPane tb_detail;

    @FXML
    private TableColumn<Pinjam, String> detail_col_id;

    @FXML
    private TableColumn<Pinjam, String> detail_col_kembali;

    @FXML
    private TableColumn<Pinjam, String> detail_col_pinjam;

    @FXML
    private TableColumn<Pinjam, String> detail_col_buku;

    @FXML
    private TableColumn<Pinjam, String> detail_col_sewa;

    @FXML
    private TableColumn<Pinjam, String> detail_col_action;

    @FXML
    private TableView<Pinjam> table_detail_buku;

    @FXML
    private Label detail_alamat;

    @FXML
    private Label detail_email;

    @FXML
    private Label detail_id;

    @FXML
    private Label detail_nama;

    @FXML
    private Label detail_wa;

    private Pinjam pinjamUpdate;

    private ObservableList<Buku> pinjamBuku = FXCollections.observableArrayList();
    private Integer remaining = 3;

    public void cetakPinjam() {
        ObservableList<String> listData = table_detail_buku.getItems().stream()
                .map(Pinjam::getKembali)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        Set<String> sortedData = listData.stream().collect(Collectors.toSet());
        for (String data : sortedData) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Nota.fxml"));
                Parent root = loader.load();
                NotaController notaController = loader.getController();
                notaController.setNota(detail_id.getText(), data);
                Stage stage = new Stage();
                stage.getIcons().add(new Image("/img/logo.png"));
                stage.setResizable(false);
                stage.setTitle("Nota");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
    }

    public void submitButton() {
        if (formValidation()) {
            if (form_id_pinjam.getText().isEmpty())
                tambahPinjam();
            else
                updatePinjam();
        }
    }

    public boolean formValidation() {
        Alert alert;
        if (pinjamBuku.size() == 0 ||
                form_id_peminjam.getText().isEmpty() ||
                form_nama_peminjam.getText().isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Tolong isi semua kolom");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    public void tambahPinjam() {
        ObservableList<Buku> buku = table_pBuku.getItems();
        Alert alert;
        int status = 0;
        for (Buku dataBuku : buku) {
            status += CrudPinjam.addDataPinjam(dataBuku.getId(), Integer.parseInt(form_id_peminjam.getText()));
        }
        if (status == buku.size()) {
            alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Data peminjaman berhasil dibuat!");
            alert.showAndWait();
            resetFormPinjam();
            showTablePinjam();
        } else if (status >= 100) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Oops sepertinya ada buku yang sudah anda pinjam");
            alert.showAndWait();
            resetFormPinjam();
            showTablePinjam();
        } else {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Oops sepertinya ada data yang gagal dibuat!");
            alert.showAndWait();
        }
    }

    public void updatePinjam() {
        ObservableList<Buku> buku = table_pBuku.getItems();
        Alert alert;
        int status = 0;
        for (Buku dataBuku : buku) {
            status += CrudPinjam.updatePinjam(dataBuku.getId(),
                    Integer.parseInt(form_id_pinjam.getText()),
                    Integer.parseInt(form_id_peminjam.getText()));
        }
        if (status == buku.size()) {
            alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Data peminjaman berhasil diubah!");
            alert.showAndWait();
            resetFormPinjam();
            showTablePinjam();
        } else if (status == 100) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Oops sepertinya buku sudah anda pinjam ");
            alert.showAndWait();
        } else {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Oops sepertinya ada data yang gagal dibuat!");
            alert.showAndWait();
        }
    }

    public void liveSearchPinjam(KeyEvent event) {
        showDataPeminjaman(CrudPinjam.searchPinjam(search_form_pinjam.getText()));
    }

    public void liveSearchBuku() {
        showTableBuku(CrudBuku.searchBuku(search_form_buku.getText()));
    }

    public void liveSearchAnggota(KeyEvent event) {
        showTableAnggota(CrudAnggota.searchAnggota(search_form_anggota.getText()));
    }

    public void showTablePinjam() {
        showDataPeminjaman(CrudPinjam.getDataPinjam(""));
        header_title.setText("Tabel Pinjam");
        tb_peminjaman.setVisible(true);
        tb_form_pinjam.setVisible(false);
        tb_detail.setVisible(false);
    }

    public void showFormPinjam() {
        header_title.setText("Form Pinjam");
        showTableAnggota(CrudAnggota.getDataAnggota(""));
        showTableBuku(CrudBuku.addDataBuku("WHERE stok > 0"));
        tb_peminjaman.setVisible(false);
        tb_form_pinjam.setVisible(true);
        tb_detail.setVisible(false);
    }

    public void showDetail() {
        header_title.setText("Detail Pinjam");
        showTableDetail();
        tb_peminjaman.setVisible(false);
        tb_form_pinjam.setVisible(false);
        tb_detail.setVisible(true);
    }

    public void resetFormPinjam() {
        table_pBuku.refresh();
        pinjamBuku.clear();
        if (form_id_pinjam.getText().isEmpty()) {
            form_id_peminjam.setText("");
            form_nama_peminjam.setText("");
            remaining = 3;
        } else
            setFormPinjam();
    }

    public void setFormPinjam() {
        search_form_anggota.setDisable(true);
        table_peminjam.setDisable(true);
        String[] peminjam = pinjamUpdate.getAnggota().split("_");
        form_id_pinjam.setText(String.valueOf(pinjamUpdate.getId()));
        form_id_peminjam.setText(peminjam[0]);
        form_nama_peminjam.setText(peminjam[1]);
        pinjamBuku = CrudBuku.addDataBuku("WHERE id_buku = " + pinjamUpdate.getBuku().split("_")[0]);
        remaining = 1;
        showDataPinjam();
    }

    public void setDetailAnggota() {
        Anggota anggota = CrudAnggota.getDataAnggota("WHERE id_anggota = " + detail_id.getText() + "").get(0);
        detail_nama.setText(anggota.getNama());
        detail_email.setText(anggota.getEmail());
        detail_wa.setText(anggota.getWa());
        detail_alamat.setText(anggota.getAlamat());
    }

    public void setBiayaSewa(ObservableList<Pinjam> listPinjam) {
        NumberFormat formatUangIDR = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        final double denda = 1000;
        double totalDenda = 0.0;
        double totalSewa = listPinjam.stream().mapToDouble(Pinjam::getSewa).sum();
        for (Pinjam pinjam : listPinjam) {
            LocalDate tglString = LocalDate.parse(pinjam.getKembali());
            LocalDate tglSekarang = LocalDate.now();

            if (tglSekarang.isAfter(tglString)) {
                long perbedaanHari = ChronoUnit.DAYS.between(tglString, tglSekarang);
                totalDenda += denda * perbedaanHari;
            }
        }
        detail_denda.setText(formatUangIDR.format(totalDenda));
        detail_total.setText(formatUangIDR.format(totalSewa + totalDenda));
    }

    public void showTableDetail() {
        ObservableList<Pinjam> listPinjam = CrudPinjam
                .getDataPinjam("WHERE peminjaman.id_anggota = " + detail_id.getText() + " ");
        detail_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        detail_col_buku.setCellValueFactory(new PropertyValueFactory<>("buku"));
        detail_col_pinjam.setCellValueFactory(new PropertyValueFactory<>("pinjam"));
        detail_col_kembali.setCellValueFactory(new PropertyValueFactory<>("kembali"));
        detail_col_sewa.setCellValueFactory(new PropertyValueFactory<>("sewa"));

        setBiayaSewa(listPinjam);

        Callback<TableColumn<Pinjam, String>, TableCell<Pinjam, String>> cellFoctory = (
                TableColumn<Pinjam, String> param) -> {
            final TableCell<Pinjam, String> cell = new TableCell<Pinjam, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        FontAwesomeIconView returnIcon = new FontAwesomeIconView(FontAwesomeIcon.REPLY);

                        returnIcon.setFill(new Color(0.0478, 0.6447, 0.0677, 1.0));
                        returnIcon.setStyle("-glyph-size:25px;");
                        returnIcon.setCursor(Cursor.HAND);

                        returnIcon.setOnMouseClicked((MouseEvent event) -> {
                            Alert alert;
                            Pinjam pinjam = table_detail_buku.getSelectionModel().getSelectedItem();
                            alert = new Alert(AlertType.CONFIRMATION);
                            alert.setTitle("Confirm Message");
                            alert.setHeaderText(null);
                            alert.setContentText(
                                    "Kembalikan buku '" + pinjam.getBuku() + "'?");
                            Optional<ButtonType> buttonType = alert.showAndWait();
                            if (buttonType.isPresent() && buttonType.get().equals(ButtonType.OK)) {
                                if (CrudPinjam.deletePinjam(pinjam.getId()) == 1) {
                                    alert = new Alert(AlertType.INFORMATION);
                                    alert.setTitle("Information Message");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Buku berhasil dikembalikan");
                                    alert.showAndWait();
                                    showTableDetail();
                                } else {
                                    alert = new Alert(AlertType.ERROR);
                                    alert.setTitle("Error Message");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Buku gagal dikembalikan");
                                    alert.showAndWait();
                                }
                            }
                        });

                        HBox managebtn = new HBox(returnIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        setGraphic(managebtn);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        detail_col_action.setCellFactory(cellFoctory);
        table_detail_buku.setItems(listPinjam);
    }

    public void showDataPeminjaman(ObservableList<Pinjam> listPinjam) {

        pinjam_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        pinjam_col_buku.setCellValueFactory(new PropertyValueFactory<>("buku"));
        pinjam_col_anggota.setCellValueFactory(new PropertyValueFactory<>("anggota"));
        pinjam_col_pinjam.setCellValueFactory(new PropertyValueFactory<>("pinjam"));
        pinjam_col_kembali.setCellValueFactory(new PropertyValueFactory<>("kembali"));

        Callback<TableColumn<Pinjam, String>, TableCell<Pinjam, String>> cellFoctory = (
                TableColumn<Pinjam, String> param) -> {
            final TableCell<Pinjam, String> cell = new TableCell<Pinjam, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.EDIT);
                        FontAwesomeIconView detailIcon = new FontAwesomeIconView(FontAwesomeIcon.PRINT);

                        deleteIcon.setFill(new Color(0.6316, 0.1345, 0.1345, 1.0));
                        deleteIcon.setStyle("-glyph-size:25px;");
                        deleteIcon.setCursor(Cursor.HAND);

                        editIcon.setFill(new Color(0.0478, 0.6447, 0.0677, 1.0));
                        editIcon.setStyle("-glyph-size:25px;");
                        editIcon.setCursor(Cursor.HAND);

                        detailIcon.setFill(new Color(0.8824, 0.6941, 0.1765, 1.0));
                        detailIcon.setStyle("-glyph-size:25px;");
                        detailIcon.setCursor(Cursor.HAND);

                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                            Alert alert;
                            Pinjam pinjam = table_peminjaman.getSelectionModel().getSelectedItem();
                            alert = new Alert(AlertType.CONFIRMATION);
                            alert.setTitle("Confirm Message");
                            alert.setHeaderText(null);
                            alert.setContentText(
                                    "Anda yakin ingin menghapus pinjaman '" + pinjam.getAnggota() + "'?");
                            Optional<ButtonType> buttonType = alert.showAndWait();
                            if (buttonType.isPresent() && buttonType.get().equals(ButtonType.OK)) {
                                if (CrudPinjam.deletePinjam(pinjam.getId()) == 1) {
                                    alert = new Alert(AlertType.INFORMATION);
                                    alert.setTitle("Information Message");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Data berhasil dihapus");
                                    alert.showAndWait();
                                    showDataPeminjaman(CrudPinjam.getDataPinjam(""));
                                } else {
                                    alert = new Alert(AlertType.ERROR);
                                    alert.setTitle("Error Message");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Data gagal dihapus");
                                    alert.showAndWait();
                                }
                            }
                        });

                        editIcon.setOnMouseClicked((MouseEvent event) -> {
                            Pinjam pinjam = table_peminjaman.getSelectionModel().getSelectedItem();
                            Alert alert;
                            alert = new Alert(AlertType.CONFIRMATION);
                            alert.setTitle("Confirm Message");
                            alert.setHeaderText(null);
                            alert.setContentText(
                                    "Lanjut edit peminjam '" + pinjam.getAnggota() + "'?");
                            Optional<ButtonType> buttonType = alert.showAndWait();
                            if (buttonType.isPresent() && buttonType.get().equals(ButtonType.OK)) {
                                pinjamUpdate = pinjam;
                                showFormPinjam();
                                resetFormPinjam();
                                setFormPinjam();
                            }
                        });

                        detailIcon.setOnMouseClicked((MouseEvent event) -> {
                            Pinjam pinjam = table_peminjaman.getSelectionModel().getSelectedItem();
                            Alert alert;
                            alert = new Alert(AlertType.CONFIRMATION);
                            alert.setTitle("Confirm Message");
                            alert.setHeaderText(null);
                            alert.setContentText(
                                    "Lihat pinjaman '" + pinjam.getAnggota() + "'?");
                            Optional<ButtonType> buttonType = alert.showAndWait();
                            if (buttonType.isPresent() && buttonType.get().equals(ButtonType.OK)) {
                                detail_id.setText(pinjam.getAnggota().split("_")[0]);
                                setDetailAnggota();
                                showDetail();
                            }
                        });

                        HBox managebtn = new HBox(detailIcon, editIcon, deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        managebtn.setSpacing(20);
                        setGraphic(managebtn);
                        setText(null);

                    }
                }
            };
            return cell;
        };

        pinjam_col_action.setCellFactory(cellFoctory);
        table_peminjaman.setItems(listPinjam);
    }

    public void showTableBuku(ObservableList<Buku> listBuku) {

        buku_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        buku_col_judul.setCellValueFactory(new PropertyValueFactory<>("judul"));
        buku_col_kategori.setCellValueFactory(new PropertyValueFactory<>("kategori"));
        buku_col_sewa.setCellValueFactory(new PropertyValueFactory<>("sewa"));

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

                        FontAwesomeIconView addIcon = new FontAwesomeIconView(FontAwesomeIcon.PLUS_SQUARE);

                        addIcon.setFill(new Color(0.0478, 0.6447, 0.0677, 1.0));
                        addIcon.setStyle("-glyph-size:25px;");
                        addIcon.setCursor(Cursor.HAND);

                        addIcon.setOnMouseClicked((MouseEvent event) -> {
                            Buku buku = table_buku.getSelectionModel().getSelectedItem();
                            addDataPinjam(buku);
                        });

                        HBox managebtn = new HBox(addIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(addIcon, new Insets(2, 3, 0, 2));
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

    public void addDataPinjam(Buku buku) {
        Alert alert;
        if (pinjamBuku.size() == remaining) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Maksimal buku yang dipinjam " + remaining);
            alert.showAndWait();
        } else {
            for (Buku cekBuku : pinjamBuku) {
                if (buku.getId() == cekBuku.getId()) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Buku sudah ada");
                    alert.showAndWait();
                    return;
                }
            }
            pinjamBuku.add(buku);
            showDataPinjam();
        }
    }

    public void showDataPinjam() {

        pbuku_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        pbuku_col_judul.setCellValueFactory(new PropertyValueFactory<>("judul"));
        pbuku_col_kategori.setCellValueFactory(new PropertyValueFactory<>("kategori"));
        pbuku_col_sewa.setCellValueFactory(new PropertyValueFactory<>("sewa"));

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

                        deleteIcon.setFill(new Color(0.6316, 0.1345, 0.1345, 1.0));
                        deleteIcon.setStyle("-glyph-size:25px;");
                        deleteIcon.setCursor(Cursor.HAND);

                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                            Buku deleteBuku = table_pBuku.getSelectionModel().getSelectedItem();
                            ObservableList<Buku> newData = FXCollections.observableArrayList();
                            for (Buku buku : pinjamBuku) {
                                if (buku.getId() != deleteBuku.getId()) {
                                    newData.add(buku);
                                }
                            }
                            pinjamBuku = newData;
                            showDataPinjam();
                        });
                        HBox managebtn = new HBox(deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 3, 0, 2));
                        setGraphic(managebtn);
                        setText(null);
                    }
                }

            };
            return cell;
        };
        pbuku_col_action.setCellFactory(cellFoctory);
        table_pBuku.setItems(pinjamBuku);
    }

    public void showTableAnggota(ObservableList<Anggota> listAnggota) {
        peminjam_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        peminjam_col_nama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        peminjam_col_sisa.setCellValueFactory(new PropertyValueFactory<>("sisa"));

        Callback<TableColumn<Anggota, String>, TableCell<Anggota, String>> cellFoctory = (
                TableColumn<Anggota, String> param) -> {
            final TableCell<Anggota, String> cell = new TableCell<Anggota, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        FontAwesomeIconView addIcon = new FontAwesomeIconView(FontAwesomeIcon.PLUS_SQUARE);

                        addIcon.setFill(new Color(0.0478, 0.6447, 0.0677, 1.0));
                        addIcon.setStyle("-glyph-size:25px;");
                        addIcon.setCursor(Cursor.HAND);

                        addIcon.setOnMouseClicked((MouseEvent event) -> {
                            Alert alert;
                            Anggota anggota = table_peminjam.getSelectionModel().getSelectedItem();
                            if (anggota.getSisa() == 0) {
                                alert = new Alert(AlertType.ERROR);
                                alert.setTitle("Error Message");
                                alert.setHeaderText(null);
                                alert.setContentText("Maaf sisa pinjam " + anggota.getNama() + " habis");
                                alert.showAndWait();
                            } else {
                                pinjamBuku.clear();
                                table_pBuku.refresh();
                                form_id_peminjam.setText(String.valueOf(anggota.getId()));
                                form_nama_peminjam.setText(anggota.getNama());
                                remaining = anggota.getSisa();
                            }
                        });

                        HBox managebtn = new HBox(addIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(addIcon, new Insets(2, 3, 0, 2));
                        setGraphic(managebtn);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        peminjam_col_action.setCellFactory(cellFoctory);
        table_peminjam.setItems(listAnggota);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        showTablePinjam();
    }
}
