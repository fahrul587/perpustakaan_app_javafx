package controllers;

import java.text.*;
import java.util.Locale;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import CRUD.CrudAnggota;//mengambil fungsi-fungsi dari package crud anggota
import CRUD.CrudPinjam;//mengambil fungsi-fungsi dari package crud pinjam
import model.Anggota;//mengambil objek / class anggota
import model.Pinjam;//mengambil objek / class pinjam

public class NotaController {
    
    @FXML
    private TableColumn<Pinjam, String> nota_col_biaya;//utk mengambil node dengan id kolom biaya
    
    @FXML
    private TableColumn<Pinjam, String> nota_col_buku;//utk mengambil node dengan id kolom buku
    
    @FXML
    private TableColumn<Pinjam, String> nota_col_id;//utk mengambil node dengan id kolom id pinjam
    
    @FXML
    private TableView<Pinjam> table_nota;//utk mengambil node dengan id tabel dan isi dari nota
    
    @FXML
    private Label nota_alamatAnggota;//utk mengambil node dengan id alamat anggota

    @FXML
    private Label nota_idAnggota;//utk mengambil node dengan id id_anggota

    @FXML
    private Label nota_namaAnggota;//utk mengambil node dengan id nama anggota

    @FXML
    private Label nota_no;//utk mengambil node dengan id nomor nota

    @FXML
    private Label nota_tanggal;//utk mengambil node dengan id tgl nota

    @FXML
    private Label nota_tenggat;//utk mengambil node dengan id tenggat nota

    @FXML
    private Label nota_total;//utk mengambil node dengan id total harga

    public String dateFormat(String tanggalString, String formatTanggal){//fungsi yg digunakan utk mengubah format tgl
        String formatDiinginkan = formatTanggal;//digunakan utk menyimpan format tgl yg diinginkan
        java.util.Date tanggalObjek = null;//digunakan utk menyimpan data tgl string yg diubah ke tipe Date
        try {
            tanggalObjek = new SimpleDateFormat("yyyy-MM-dd").parse(tanggalString);//mengubah data tgl string ke Date
        } catch (ParseException e) {
            e.printStackTrace();//menampilkan error
        }
        if (tanggalObjek != null) {//jika tanggalObjek bukan bernilai null
            String tanggalDiinginkan = new SimpleDateFormat(formatDiinginkan).format(tanggalObjek);//mengubah format date lama ke format yang diinginkan
            return tanggalDiinginkan;//mengembalikan nilai tgl yg telah diberi format baru
        } else {
            return "Gagal mengonversi tanggal.";//mengembalikan nilai jika gagal
        }
    }

    public void setBiayaSewa(ObservableList<Pinjam> listPinjam) {//menjumlahkan harga sewa buku
        NumberFormat formatUangIDR = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));//utk mengubah format double menjadi rupiah
        double totalSewa = listPinjam.stream().mapToDouble(Pinjam::getSewa).sum();//menjumlahkan semua sewa buku
        nota_total.setText(formatUangIDR.format(totalSewa));//merubah jumlah sewa buku menjadi rupiah
    }

    public void setNota(String id, String tenggat){//menampilkan data nota
        Pinjam dataPinjam = CrudPinjam.getDataPinjam("WHERE peminjaman.id_anggota = '"+id+"' and peminjaman.tgl_kembali = '"+tenggat+"' ").getFirst();//mengambil dan menyimpan satu data pinjam berdasarkan id_anggota dan tgl kembali
        Anggota dataAnggota = CrudAnggota.getDataAnggota("WHERE id_anggota = "+id+"").getFirst();//mengambil dan menyimpan data anggota berdasarkan id_anggota

        nota_no.setText(dateFormat(dataPinjam.getPinjam(), "yyMMddd"));//menampikan nomor nota dengan data pinjam yang telah diubah formatnya
        nota_tanggal.setText(dateFormat(dataPinjam.getPinjam(), "dd/MM/yyyy"));//menampikan tgl nota dengan data pinjam yang telah diubah formatnya
        nota_tenggat.setText(dateFormat(dataPinjam.getKembali(), "dd/MM/yyyy"));//menampikan tenggat nota dengan data pinjam yang telah diubah formatnya
        nota_idAnggota.setText(String.valueOf(dataAnggota.getId()));//menampilan id anggota
        nota_namaAnggota.setText(dataAnggota.getNama());//menampilkan nama anggota
        nota_alamatAnggota.setText(dataAnggota.getAlamat());//menampilkan alamat anggota
        showTableNota(id, tenggat);//menampilkan isi tabel nota dan memberinya anrgumen
    }

    public void showTableNota(String id, String tenggat){//menampikan tabel nota dan menerima parameter
        ObservableList<Pinjam> dataList = CrudPinjam.getDataPinjam("WHERE peminjaman.id_anggota = '"+id+"' and peminjaman.tgl_kembali = '"+tenggat+"' ");//mengambil dan menyimpan seluruh data anggota berdasarkan id_anggota
        
        nota_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));//memberi properti id pada tabel nota
        nota_col_buku.setCellValueFactory(new PropertyValueFactory<>("buku"));//memberi properti buku pada tabel nota
        nota_col_biaya.setCellValueFactory(new PropertyValueFactory<>("sewa"));//memberi properti sewa pada tabel nota
        table_nota.setItems(dataList);//memberi value pada utk tabel nota
        setBiayaSewa(dataList);//menjalankan fungsi utk menampilkan total sewa
    }
}
