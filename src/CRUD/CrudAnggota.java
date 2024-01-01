package CRUD;

import java.sql.*;
import javafx.collections.*;
import model.Anggota;

public class CrudAnggota {
    private static PreparedStatement prepare;
    private static Connection conn;
    private static ResultSet result;

    public static ObservableList<Anggota> getDataAnggota(String s) {
        ObservableList<Anggota> listData = FXCollections.observableArrayList();
        String query = "SELECT * FROM anggota " + s;
        conn = Koneksi.connectDb();

        try {
            prepare = conn.prepareStatement(query);
            result = prepare.executeQuery();
            Anggota anggota;
            while (result.next()) {
                anggota = new Anggota(
                        result.getInt("id_anggota"),
                        result.getString("nama"),
                        result.getString("alamat"),
                        result.getString("email"),
                        result.getString("no_whatsapp"),
                        result.getString("tgl_bergabung"),
                        result.getInt("remaining"));
                listData.add(anggota);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return listData;
    }

    public static int addAnggota(Anggota anggota) {
        String query = "INSERT INTO anggota " +
                "(nama, alamat, email, no_whatsapp) " +
                "VALUES (?,?,?,?)";
        conn = Koneksi.connectDb();
        try {
            prepare = conn.prepareStatement(query);
            prepare.setString(1, anggota.getNama());
            prepare.setString(2, anggota.getAlamat());
            prepare.setString(3, anggota.getEmail());
            prepare.setString(4, anggota.getWa());

            return prepare.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int updateAngota(Anggota anggota) {
        String query = "UPDATE anggota SET " +
                "nama=?,alamat=?,email=?,no_whatsapp=? WHERE id_anggota=?";
        conn = Koneksi.connectDb();
        try {
            prepare = conn.prepareStatement(query);
            prepare.setString(1, anggota.getNama());
            prepare.setString(2, anggota.getAlamat());
            prepare.setString(3, anggota.getEmail());
            prepare.setString(4, anggota.getWa());
            prepare.setInt(5, anggota.getId());

            return prepare.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int deleteAnggota(int id_anggota) {
        String query = "DELETE FROM anggota WHERE id_anggota = " + id_anggota;
        conn = Koneksi.connectDb();
        try {
            prepare = conn.prepareStatement(query);
            return prepare.executeUpdate();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return 0;
    }

    public static int getRows() {
        String query = "SELECT COUNT(*) AS jumlah FROM anggota";
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

    public static ObservableList<Anggota> searchAnggota(String s) {
        ObservableList<Anggota> listData = FXCollections.observableArrayList();
        String query = "SELECT * FROM anggota WHERE nama LIKE ? OR alamat LIKE ? OR email LIKE ? OR no_whatsapp LIKE ? OR tgl_bergabung LIKE ?";
        conn = Koneksi.connectDb();
        try {
            prepare = conn.prepareStatement(query);
            prepare.setString(1, "%" + s + "%");
            prepare.setString(2, "%" + s + "%");
            prepare.setString(3, "%" + s + "%");
            prepare.setString(4, "%" + s + "%");
            prepare.setString(5, "%" + s + "%");
            result = prepare.executeQuery();
            Anggota anggota;
            while (result.next()) {
                anggota = new Anggota(
                        result.getInt("id_anggota"),
                        result.getString("nama"),
                        result.getString("alamat"),
                        result.getString("email"),
                        result.getString("no_whatsapp"),
                        result.getString("tgl_bergabung"),
                        result.getInt("remaining"));
                listData.add(anggota);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return listData;
    }
}
