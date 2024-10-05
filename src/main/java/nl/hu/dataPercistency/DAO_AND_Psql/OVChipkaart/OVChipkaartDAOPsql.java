package nl.hu.dataPercistency.DAO_AND_Psql.OVChipkaart;

import nl.hu.dataPercistency.DAO_AND_Psql.Product.ProductDAO;
import nl.hu.dataPercistency.DAO_AND_Psql.Product.ProductDAOPsql;
import nl.hu.dataPercistency.DAO_AND_Psql.Reiziger.ReizigerDAOPsql;
import nl.hu.dataPercistency.domain.OVChipkaart; // Adjust this import according to your project structure
import nl.hu.dataPercistency.domain.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;

public class OVChipkaartDAOPsql implements OVChipkaartDAO {
    private Connection conn;

    // Constructor to initialize the database connection
    public OVChipkaartDAOPsql(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void save(OVChipkaart ovChipkaart) {
        String sql = "INSERT INTO ov_chipkaart (kaart_nummer, geldig_tot, klasse, saldo, reiziger_id, product_nr_fk) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, ovChipkaart.getKaartNummer());
            pstmt.setDate(2, new java.sql.Date(ovChipkaart.getGeldigTot().getTime()));
            pstmt.setInt(3, ovChipkaart.getKlasse());
            pstmt.setDouble(4, ovChipkaart.getSaldo());
            pstmt.setInt(5, ovChipkaart.getReiziger());

            if (ovChipkaart.getProduct() != null) {
                pstmt.setInt(6, ovChipkaart.getProduct().getProductNr());  // Sla de product foreign key op
            } else {
                pstmt.setNull(6, java.sql.Types.INTEGER);  // Geen product gekoppeld
            }

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public OVChipkaart findById(int kaartNummer) {
        OVChipkaart ovChipkaart = null;
        String sql = "SELECT * FROM ov_chipkaart WHERE kaart_nummer = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, kaartNummer);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                ovChipkaart = new OVChipkaart();
                ovChipkaart.setKaartNummer(rs.getInt("kaart_nummer"));
                ovChipkaart.setGeldigTot(rs.getDate("geldig_tot"));
                ovChipkaart.setKlasse(rs.getInt("klasse"));
                ovChipkaart.setSaldo(rs.getDouble("saldo"));
                ovChipkaart.setReiziger(rs.getInt("reiziger_id"));

                // Haal het geassocieerde product op
                int productNr = rs.getInt("product_nr_fk");
                if (productNr != 0) {
                    ProductDAO productDAO = new ProductDAOPsql(conn);
                    Product product = productDAO.findById(productNr);  // Zoek het product op
                    ovChipkaart.setProduct(product);  // Koppel het product aan de OVChipkaart
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ovChipkaart;
    }


    @Override
    public List<OVChipkaart> findByReizigerId(int reizigerId) {
        ProductDAOPsql productDAO = new ProductDAOPsql(conn);

        List<OVChipkaart> ovChipkaarten = new ArrayList<>();
        String sql = "SELECT * FROM ov_chipkaart WHERE reiziger_id = ?"; // Zorg ervoor dat deze kolom bestaat
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, reizigerId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                OVChipkaart ovChipkaart = new OVChipkaart();
                ovChipkaart.setKaartNummer(rs.getInt("kaart_nummer"));
                ovChipkaart.setGeldigTot(rs.getDate("geldig_tot"));
                ovChipkaart.setKlasse(rs.getInt("klasse"));
                ovChipkaart.setSaldo(rs.getDouble("saldo"));
                ovChipkaart.setProduct(productDAO.findById(rs.getInt("product_nr_fk")));
                ovChipkaarten.add(ovChipkaart);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }
        return ovChipkaarten;
    }


    @Override
    public void update(OVChipkaart ovChipkaart) {
        String sql = "UPDATE ov_chipkaart SET geldig_tot = ?, klasse = ?, saldo = ?, reiziger_id = ?, product_nr_fk = ? WHERE kaart_nummer = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, new java.sql.Date(ovChipkaart.getGeldigTot().getTime()));
            pstmt.setInt(2, ovChipkaart.getKlasse());
            pstmt.setDouble(3, ovChipkaart.getSaldo());
            pstmt.setInt(4, ovChipkaart.getReiziger());

            if (ovChipkaart.getProduct() != null) {
                pstmt.setInt(5, ovChipkaart.getProduct().getProductNr());  // Update het product_nr_fk
            } else {
                pstmt.setNull(5, java.sql.Types.INTEGER);  // Verwijder de product referentie als het product null is
            }

            pstmt.setInt(6, ovChipkaart.getKaartNummer());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void delete(int kaartNummer) {
        String sql = "DELETE FROM ov_chipkaart WHERE kaart_nummer = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, kaartNummer);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
