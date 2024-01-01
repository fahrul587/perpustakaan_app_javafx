package CRUD;

import java.sql.*;
import javafx.collections.*;
import model.Buku;

public class CrudBuku {
    private static PreparedStatement prepare;
    private static Connection conn;
    private static ResultSet result;

    public static ObservableList<Buku> addDataBuku(String s) {
        ObservableList<Buku> listData = FXCollections.observableArrayList();
        String query = "SELECT * FROM buku " +
                "JOIN kategori ON buku.kategori_id = kategori.id_kategori "+ s;
        conn = Koneksi.connectDb();
        try {
            prepare = conn.prepareStatement(query);
            result = prepare.executeQuery();
            Buku buku;
            while (result.next()) {
                buku = new Buku(
                        result.getInt("id_buku"),
                        result.getInt("kategori_id") + "_" + result.getString("nama_kategori"),
                        result.getString("judul"),
                        result.getString("penulis"),
                        result.getString("penerbit"),
                        result.getString("tahun_terbit"),
                        result.getInt("stok"),
                        result.getInt("harga_sewa"));
                listData.add(buku);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return listData;
    }

    public static int addBuku(Buku buku) {
        String query = "INSERT INTO buku " +
                "(kategori_id, judul, penulis, penerbit, tahun_terbit, stok, harga_sewa) " +
                "VALUES (?,?,?,?,?,?,?)";
        conn = Koneksi.connectDb();
        try {
            prepare = conn.prepareStatement(query);
            prepare.setInt(1, Integer.parseInt(buku.getKategori()));
            prepare.setString(2, buku.getJudul());
            prepare.setString(3, buku.getPenulis());
            prepare.setString(4, buku.getPenerbit());
            prepare.setString(5, buku.getTerbit());
            prepare.setInt(6, buku.getStok());
            prepare.setInt(7, buku.getSewa());

            return prepare.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int updateBuku(Buku buku) {
        String query = "UPDATE buku SET " +
                "kategori_id=?,judul=?,penulis=?,penerbit=?,tahun_terbit=?,stok=?,harga_sewa=? WHERE id_buku=?";
        conn = Koneksi.connectDb();
        try {
            prepare = conn.prepareStatement(query);
            prepare.setInt(1, Integer.parseInt(buku.getKategori()));
            prepare.setString(2, buku.getJudul());
            prepare.setString(3, buku.getPenulis());
            prepare.setString(4, buku.getPenerbit());
            prepare.setString(5, buku.getTerbit());
            prepare.setInt(6, buku.getStok());
            prepare.setInt(7, buku.getSewa());
            prepare.setInt(8, buku.getId());

            return prepare.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int deleteBuku(int id_buku) {
        String query = "DELETE FROM buku WHERE id_buku = " + id_buku;
        conn = Koneksi.connectDb();
        try {
            prepare = conn.prepareStatement(query);
            return prepare.executeUpdate();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return 0;
    }

    public static ObservableList<Buku> searchBuku(String s) {
        ObservableList<Buku> listData = FXCollections.observableArrayList();
        String query = "SELECT * FROM buku " +
                "JOIN kategori ON buku.kategori_id = kategori.id_kategori " +
                "WHERE judul LIKE ? OR nama_kategori LIKE ? OR penulis LIKE ? OR penerbit LIKE ? OR tahun_terbit LIKE ?";
        conn = Koneksi.connectDb();
        try {
            prepare = conn.prepareStatement(query);
            prepare.setString(1, "%" + s + "%");
            prepare.setString(2, "%" + s + "%");
            prepare.setString(3, "%" + s + "%");
            prepare.setString(4, "%" + s + "%");
            prepare.setString(5, "%" + s + "%");
            result = prepare.executeQuery();
            Buku buku;
            while (result.next()) {

                buku = new Buku(
                        result.getInt("id_buku"),
                        result.getInt("kategori_id") + "_" + result.getString("nama_kategori"),
                        result.getString("judul"),
                        result.getString("penulis"),
                        result.getString("penerbit"),
                        result.getString("tahun_terbit"),
                        result.getInt("stok"),
                        result.getInt("harga_sewa"));
                listData.add(buku);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return listData;

    }

    public static int getRows() {
        String query = "SELECT COUNT(*) AS jumlah FROM buku";
        conn = Koneksi.connectDb();
        try {
            prepare = conn.prepareStatement(query);
            result = prepare.executeQuery();
            result.next();
            return result.getInt("jumlah");
        } catch (Exception e) {
            // TODO: handle exception
        }
        return 0;

    }

}
