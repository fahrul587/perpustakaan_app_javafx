package controllers;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import CRUD.CrudKategori;
import de.jensd.fx.glyphs.fontawesome.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import model.Kategori;

public class KategoriController implements Initializable {
    @FXML
    private TextField form_id_kategori;

    @FXML
    private TextField form_nama_kategori;

    @FXML
    private Label header_title;

    @FXML
    private TableColumn<Kategori, String> kategori_col_action;

    @FXML
    private TableColumn<Kategori, String> kategori_col_id;

    @FXML
    private TableColumn<Kategori, String> kategori_col_kategori;

    @FXML
    private TextField search_kategori;

    @FXML
    private TableView<Kategori> table_kategori;

    @FXML
    private AnchorPane tb_kategori;

    @FXML
    public void resetFormKategori() {
        form_id_kategori.setText("");
        form_nama_kategori.setText("");
    }

    @FXML
    void submitButton(ActionEvent event) {
        Alert alert;
        if (form_nama_kategori.getText().isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Tolong lengkapi kolom");
            alert.showAndWait();
        } else {
            if (form_id_kategori.getText().isEmpty())
                tambahKategori();
            else
                updateKategori();
        }
    }

    public void tambahKategori() {
        Alert alert;
        if (CrudKategori.addKategori(form_nama_kategori.getText()) == 1) {
            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Kategori berhasil ditambah");
            alert.showAndWait();
            showDataKategori(CrudKategori.getKategori());
            resetFormKategori();
        } else {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Kategori sudah ada");
            alert.showAndWait();
        }
    }

    public void updateKategori() {
        Alert alert;
        if (CrudKategori.updateKategori(Integer.parseInt(form_id_kategori.getText()),
                form_nama_kategori.getText()) == 1) {
            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Kategori berhasil diubah");
            alert.showAndWait();
            showDataKategori(CrudKategori.getKategori());
            resetFormKategori();
        } else {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Kategori sudah ada");
            alert.showAndWait();
        }
    }

    public void liveSearch() {
        showDataKategori(CrudKategori.searchKategori(search_kategori.getText()));
    }

    public void showDataKategori(ObservableList<Kategori> listKategori) {
        kategori_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        kategori_col_kategori.setCellValueFactory(new PropertyValueFactory<>("kategori"));

        Callback<TableColumn<Kategori, String>, TableCell<Kategori, String>> cellFoctory = (
                TableColumn<Kategori, String> param) -> {
            final TableCell<Kategori, String> cell = new TableCell<Kategori, String>() {
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
                            Kategori kategori = table_kategori.getSelectionModel().getSelectedItem();
                            alert = new Alert(AlertType.CONFIRMATION);
                            alert.setTitle("Confirm Message");
                            alert.setHeaderText(null);
                            alert.setContentText(
                                    "Anda yakin ingin menghapus kategori '" + kategori.getKategori() + "'?");
                            Optional<ButtonType> buttonType = alert.showAndWait();
                            if (buttonType.isPresent() && buttonType.get().equals(ButtonType.OK)) {
                                if (CrudKategori.deleteKategori(kategori.getId()) == 1) {
                                    alert = new Alert(AlertType.CONFIRMATION);
                                    alert.setTitle("Information Message");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Kategori berhasil dihapus");
                                    alert.showAndWait();
                                    showDataKategori(CrudKategori.getKategori());
                                } else {
                                    alert = new Alert(AlertType.ERROR);
                                    alert.setTitle("Error Message");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Kategori gagal dihapus");
                                    alert.showAndWait();
                                }
                            }
                        });

                        editIcon.setOnMouseClicked((MouseEvent event) -> {
                            Kategori kategori = table_kategori.getSelectionModel().getSelectedItem();
                            form_id_kategori.setText(String.valueOf(kategori.getId()));
                            form_nama_kategori.setText(kategori.getKategori());
                        });

                        HBox managebtn = new HBox(editIcon, deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        managebtn.setSpacing(20);
                        setGraphic(managebtn);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        kategori_col_action.setCellFactory(cellFoctory);
        table_kategori.setItems(listKategori);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        showDataKategori(CrudKategori.getKategori());
    }
}
