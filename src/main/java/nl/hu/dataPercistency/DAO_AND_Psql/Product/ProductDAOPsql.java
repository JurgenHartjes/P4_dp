package nl.hu.dataPercistency.DAO_AND_Psql.Product;

import nl.hu.dataPercistency.domain.Product; // Adjust this import according to your project structure

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOPsql implements ProductDAO {
    private Connection conn;

    // Constructor to initialize the database connection
    public ProductDAOPsql(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void save(Product product) {
        String sql = "INSERT INTO product (product_nummer, naam, beschrijving, prijs) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, product.getProductNr());
            pstmt.setString(2, product.getNaam());
            pstmt.setString(3, product.getBeschrijving());
            pstmt.setDouble(4, product.getPrijs());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Product findById(int productNr) {
        Product product = null;
        String sql = "SELECT * FROM product WHERE product_nummer = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, productNr);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                product = new Product();
                product.setProductNr(rs.getInt("product_nummer"));
                product.setNaam(rs.getString("naam"));
                product.setBeschrijving(rs.getString("beschrijving"));
                product.setPrijs(rs.getDouble("prijs"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public List<Product> findByOVChipkaart(int kaartNummer) {
        List<Product> products = new ArrayList<>();

        // SQL query with JOIN to retrieve products associated with the given OVChipkaart
        String sql = "SELECT p.product_nummer, p.naam, p.beschrijving, p.prijs " +
                "FROM ov_chipkaart_product ocp " +
                "JOIN product p ON ocp.product_nummer = p.product_nummer " +
                "WHERE ocp.kaart_nummer = ?"; // We use a placeholder for the kaart_nummer

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, kaartNummer); // Bind the kaart_nummer value to the query
            ResultSet rs = pstmt.executeQuery();

            // Iterate through the result set and populate the list of products
            while (rs.next()) {
                Product product = new Product();
                product.setProductNr(rs.getInt("product_nummer"));
                product.setNaam(rs.getString("naam"));
                product.setBeschrijving(rs.getString("beschrijving"));
                product.setPrijs(rs.getDouble("prijs"));

                // Add the product to the list
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }

        return products;
    }


    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM product"; // Adjust the SQL query as needed
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setProductNr(rs.getInt("product_nummer"));
                product.setNaam(rs.getString("naam"));
                product.setBeschrijving(rs.getString("beschrijving"));
                product.setPrijs(rs.getDouble("prijs"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }
        return products;
    }

    @Override
    public void update(Product product) {
        String sql = "UPDATE product SET naam = ?, beschrijving = ?, prijs = ? WHERE product_nummer = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, product.getNaam());
            pstmt.setString(2, product.getBeschrijving());
            pstmt.setDouble(3, product.getPrijs());
            pstmt.setInt(4, product.getProductNr());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }
    }

    @Override
    public void delete(int productNr) {
        String sql = "DELETE FROM product WHERE product_nummer = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, productNr);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }
    }


}
