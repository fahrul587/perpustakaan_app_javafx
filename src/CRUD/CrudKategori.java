package CRUD;

import java.sql.*;
import javafx.collections.*;
import model.Kategori;

public class CrudKategori {
    private static PreparedStatement prepare;
    private static Connection conn;
    private static ResultSet result;

    public static int addKategori(String kategori) {
        String query = "SELECT * FROM kategori WHERE nama_kategori = ?";
        conn = Koneksi.connectDb();
        try {
            prepare = conn.prepareStatement(query);
            prepare.setString(1, kategori);
            result = prepare.executeQuery();
            if (result.next())
                return 0;
            prepare.close();
            query = "INSERT INTO kategori (nama_kategori) VALUES (?)";
            try {
                prepare = conn.prepareStatement(query);
                prepare.setString(1, kategori);
                return prepare.executeUpdate();
            } catch (Exception e) {
                // TODO: handle exception
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return 0;
    }

    public static ObservableList<Kategori> getKategori() {
        ObservableList<Kategori> listData = FXCollections.observableArrayList();
        String query = "SELECT * FROM kategori";
        conn = Koneksi.connectDb();
        try {
            prepare = conn.prepareStatement(query);
            result = prepare.executeQuery();
            Kategori kategori;
            while (result.next()) {
                kategori = new Kategori(result.getInt("id_kategori"), result.getString("nama_kategori"));
                listData.add(kategori);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return listData;
    }

    public static ObservableList<Kategori> searchKategori(String s) {
        ObservableList<Kategori> listData = FXCollections.observableArrayList();
        String query = "SELECT * FROM kategori WHERE nama_kategori LIKE ?";
        conn = Koneksi.connectDb();
        try {
            prepare = conn.prepareStatement(query);
            prepare.setString(1, "%" + s + "%");
            result = prepare.executeQuery();
            Kategori kategori;
            while (result.next()) {

                kategori = new Kategori(result.getInt("id_kategori"), result.getString("nama_kategori"));
                listData.add(kategori);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return listData;
    }

    public static int deleteKategori(int id_kategori) {
        String query = "DELETE FROM kategori WHERE id_kategori = " + id_kategori;
        conn = Koneksi.connectDb();
        try {
            prepare = conn.prepareStatement(query);
            return prepare.executeUpdate();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return 0;
    }

    public static int updateKategori(int id_kategori, String nama_kategori) {
        String query = "SELECT * FROM kategori WHERE id_kategori != "+id_kategori+" and nama_kategori = '"+nama_kategori+"'";
        conn = Koneksi.connectDb();
        try {
            prepare = conn.prepareStatement(query);
            result = prepare.executeQuery();
            if (result.next()) return 0;
            prepare.close();
            query = "UPDATE kategori SET nama_kategori=? WHERE id_kategori=?";
            try {
                prepare = conn.prepareStatement(query);
                prepare.setString(1, nama_kategori);
                prepare.setInt(2, id_kategori);
                return prepare.executeUpdate();
            } catch (Exception e) {
                // TODO: handle exception
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return 0;
    }

    public static int getRows() {
        String query = "SELECT COUNT(*) AS jumlah FROM kategori";
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
