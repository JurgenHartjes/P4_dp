package nl.hu.dataPercistency.DAO_AND_Psql.Adres;

import nl.hu.dataPercistency.domain.Adres;
import nl.hu.dataPercistency.domain.Reiziger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdresDAOPsql implements AdresDAO {

    private Connection conn;

    // Constructor to initialize the database connection
    public AdresDAOPsql(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void save(Adres adres, Reiziger reiziger) {
        String sql = "INSERT INTO adres (adres_id, postcode, huisnummer, straat, woonplaats, reiziger_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, adres.getAdresId());
            pstmt.setString(2, adres.getPostcode());
            pstmt.setString(3, adres.getHuisnummer());
            pstmt.setString(4, adres.getStraat());
            pstmt.setString(5, adres.getWoonplaats());
            pstmt.setInt(6, reiziger.getReizigerId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }
    }

    @Override
    public Adres findById(int adresId) {
        Adres adres = null;
        String sql = "SELECT * FROM adres WHERE adres_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, adresId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                adres = new Adres();
                adres.setAdresId(rs.getInt("adres_id"));
                adres.setPostcode(rs.getString("postcode"));
                adres.setHuisnummer(rs.getString("huisnummer"));
                adres.setStraat(rs.getString("straat"));
                adres.setWoonplaats(rs.getString("woonplaats"));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }
        return adres;
    }

    @Override
    public void update(Adres adres) {
        String sql = "UPDATE adres SET postcode = ?, huisnummer = ?, straat = ?, woonplaats = ? WHERE adres_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, adres.getPostcode());
            pstmt.setString(2, adres.getHuisnummer());
            pstmt.setString(3, adres.getStraat());
            pstmt.setString(4, adres.getWoonplaats());
            pstmt.setInt(5, adres.getAdresId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }
    }

    @Override
    public void delete(int adresId) {
        String sql = "DELETE FROM adres WHERE adres_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, adresId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }
    }

}
