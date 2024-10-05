package nl.hu.dataPercistency.DAO_AND_Psql.Reiziger;

import nl.hu.dataPercistency.DAO_AND_Psql.OVChipkaart.OVChipkaartDAOPsql;
import nl.hu.dataPercistency.domain.OVChipkaart;
import nl.hu.dataPercistency.domain.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO {
    private Connection conn;

    public ReizigerDAOPsql(Connection conn) {
        this.conn = conn;
    }

    public void save(Reiziger reiziger) {
        if (findById(reiziger.getReizigerId()) != null) {
            update(reiziger); // Update als hij al bestaat
        } else {
            String sql = "INSERT INTO reiziger (reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, reiziger.getReizigerId());
                pstmt.setString(2, reiziger.getVoorletters());
                pstmt.setString(3, reiziger.getTussenvoegsel());
                pstmt.setString(4, reiziger.getAchternaam());
                pstmt.setDate(5, new java.sql.Date(reiziger.getGeboortedatum().getTime()));
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace(); // Handle exceptions appropriately
            }
        }
    }

    @Override
    public Reiziger findById(int id) {
        String sql = "SELECT * FROM reiziger WHERE reiziger_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Reiziger reiziger = new Reiziger(
                        rs.getInt("reiziger_id"),
                        rs.getString("voorletters"),
                        rs.getString("tussenvoegsel"),
                        rs.getString("achternaam"),
                        rs.getDate("geboortedatum")
                );

                // Fetch the associated OVChipkaarten
                OVChipkaartDAOPsql ovChipkaartDao = new OVChipkaartDAOPsql(conn);
                List<OVChipkaart> ovChipkaarten = ovChipkaartDao.findByReizigerId(reiziger.getReizigerId());
                for (OVChipkaart ovChipkaart : ovChipkaarten) {
                    reiziger.addOvChipkaart(ovChipkaart);
                }
                return reiziger;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<Reiziger> findAll() {
        List<Reiziger> reizigers = new ArrayList<>();
        String sql = "SELECT * FROM reiziger";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Reiziger reiziger = new Reiziger(
                        rs.getInt("reiziger_id"),
                        rs.getString("voorletters"),
                        rs.getString("tussenvoegsel"),
                        rs.getString("achternaam"),
                        rs.getDate("geboortedatum")
                );
                reizigers.add(reiziger);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }
        return reizigers;
    }

    @Override
    public void update(Reiziger reiziger) {
        String sql = "UPDATE reiziger SET voorletters = ?, tussenvoegsel = ?, achternaam = ?, geboortedatum = ? WHERE reiziger_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, reiziger.getVoorletters());
            pstmt.setString(2, reiziger.getTussenvoegsel());
            pstmt.setString(3, reiziger.getAchternaam());
            pstmt.setDate(4, new java.sql.Date(reiziger.getGeboortedatum().getTime()));
            pstmt.setInt(5, reiziger.getReizigerId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM reiziger WHERE reiziger_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }
    }
}
