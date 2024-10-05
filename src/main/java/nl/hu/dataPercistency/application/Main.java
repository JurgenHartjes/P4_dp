package nl.hu.dataPercistency.application;

import nl.hu.dataPercistency.DAO_AND_Psql.Adres.AdresDAO;
import nl.hu.dataPercistency.DAO_AND_Psql.Adres.AdresDAOPsql;
import nl.hu.dataPercistency.DAO_AND_Psql.OVChipkaart.OVChipkaartDAOPsql;
import nl.hu.dataPercistency.DAO_AND_Psql.Product.ProductDAOPsql;
import nl.hu.dataPercistency.DAO_AND_Psql.Reiziger.ReizigerDAO;
import nl.hu.dataPercistency.DAO_AND_Psql.Reiziger.ReizigerDAOPsql;
import nl.hu.dataPercistency.domain.Adres;
import nl.hu.dataPercistency.domain.OVChipkaart;
import nl.hu.dataPercistency.domain.Product;
import nl.hu.dataPercistency.domain.Reiziger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    private Connection connection;

    public static void main(String[] args) {
        Main main = new Main();
        main.deletePreviousTestSubjects();
        main.testReizigerDAO();
        main.testAdresDAO();
        main.testOVChipkaartDAO();
        main.testProductDAO();
    }

    private void deletePreviousTestSubjects() {
        ReizigerDAOPsql reizigerDAO = new ReizigerDAOPsql(getConnection());
        OVChipkaartDAOPsql ovChipkaartDAO = new OVChipkaartDAOPsql(getConnection());
        AdresDAOPsql adresDAO = new AdresDAOPsql(getConnection());
        ProductDAOPsql productDAO = new ProductDAOPsql(getConnection());


        adresDAO.delete(1);
        ovChipkaartDAO.delete(1);
        productDAO.delete(1234);
        reizigerDAO.delete(123);


        System.out.println("Deleted all previous testSubjects");


    }

    // Method to get the database connection
    public Connection getConnection() {
        if (connection == null) {
            try {
                // Update these parameters with your actual database information
                String url = "jdbc:postgresql://localhost:5432/ovchip"; // Replace with your database URL
                String user = "postgres"; // Replace with your database user
                String password = "fj6YDB5D"; // Replace with your database password
                connection = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                e.printStackTrace(); // Handle exceptions appropriately
            }
        }
        return connection;
    }

    // Method to close the database connection
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace(); // Handle exceptions appropriately
            }
        }
    }

    // Test method for ReizigerDAO
    public void testReizigerDAO() {
        ReizigerDAOPsql reizigerDAO = new ReizigerDAOPsql(getConnection());
// Example of creating a Reiziger and Adres
        Reiziger reiziger = new Reiziger(1, "A", null, "Vries", Date.valueOf("1990-01-01"));

        Adres adres = new Adres();
        adres.setAdresId(1);
        adres.setPostcode("1234AB");
        adres.setHuisnummer("56");
        adres.setStraat("Hoofdstraat");
        adres.setWoonplaats("Amsterdam");

// Save Reiziger
        reizigerDAO.save(reiziger);

// Save Adres and pass the Reiziger object
        reizigerDAO.save(reiziger);
        System.out.println("Reiziger persisted.");

        // Find the Reiziger by ID
        Reiziger foundReiziger = reizigerDAO.findById(1);
        System.out.println("Found Reiziger: " + foundReiziger);

        // Update the Reiziger
        reiziger.setAchternaam("Smith");
        reizigerDAO.update(reiziger);
        System.out.println("Reiziger updated.");

        // Delete the Reiziger
        reizigerDAO.delete(1);
        System.out.println("Reiziger deleted.");
    }

    public void testProductDAO() {
        ProductDAOPsql productDAO = new ProductDAOPsql(getConnection());
        OVChipkaartDAOPsql ovChipkaartDAO = new OVChipkaartDAOPsql(getConnection());
        ReizigerDAOPsql reizigerDAO = new ReizigerDAOPsql(getConnection());

        // Create and save a new Reiziger
        Reiziger reiziger = new Reiziger(123, "J", null, "Hartjes", java.sql.Date.valueOf("2006-06-22"));
        reizigerDAO.save(reiziger);

        // Create and save a new Product
        Product product = new Product();
        product.setProductNr(1234);
        product.setNaam("Reisproduct");
        product.setBeschrijving("Onbeperkt reizen");
        product.setPrijs(50.00);
        productDAO.save(product);

        // Create and save a new OVChipkaart and link it to the product
        OVChipkaart ovChipkaart = new OVChipkaart();
        ovChipkaart.setKaartNummer(1);
        ovChipkaart.setGeldigTot(java.sql.Date.valueOf("2025-01-01"));
        ovChipkaart.setKlasse(1);
        ovChipkaart.setSaldo(100.00);
        ovChipkaart.setReiziger(reiziger.getReizigerId());
        if (ovChipkaart.getProduct() != null) {
            ovChipkaart.getProduct().setProductNr(product.getProductNr());  // Koppel OVChipkaart aan Product
        }

        ovChipkaartDAO.save(ovChipkaart);

        // Test data retrieval for OVChipkaart and Product
        OVChipkaart foundOVChipkaart = ovChipkaartDAO.findById(1);
        System.out.println("OVChipkaart gevonden: " + foundOVChipkaart);
        int productnummer;
        if (foundOVChipkaart.getProduct() != null)  {
            productnummer = foundOVChipkaart.getProduct().getProductNr();
            System.out.println("Gekoppeld product nummer: " + productnummer);
            Product foundProduct = productDAO.findById(foundOVChipkaart.getProduct().getProductNr());
            System.out.println("Product gevonden: " + foundProduct.getNaam());
        }



        // Update and test update functionality
        foundOVChipkaart.setSaldo(150.00);
        ovChipkaartDAO.update(foundOVChipkaart);
        System.out.println("OVChipkaart updated.");

        // Delete and test delete functionality
        ovChipkaartDAO.delete(foundOVChipkaart.getKaartNummer());
        productDAO.delete(product.getProductNr());
        reizigerDAO.delete(reiziger.getReizigerId());
        System.out.println("OVChipkaart, product en reiziger deleted.");
    }

    // Test method for AdresDAO
    public void testAdresDAO() {
        AdresDAOPsql adresDAO = new AdresDAOPsql(getConnection());
        ReizigerDAOPsql reizigerDAO = new ReizigerDAOPsql(getConnection());
        // Create a new Adres object
        Adres adres = new Adres();
        adres.setAdresId(1); // Set attributes as needed
        adres.setPostcode("1234AB");
        adres.setHuisnummer("56");
        adres.setStraat("Hoofdstraat");
        adres.setWoonplaats("Amsterdam");
        Reiziger reiziger = new Reiziger(123, "J", null, "Hartjes", java.sql.Date.valueOf("2006-06-22"));
        reizigerDAO.save(reiziger);


        // Persist the Adres
        adresDAO.save(adres, reiziger);
        System.out.println("Adres persisted.");

        // Find the Adres by ID
        Adres foundAdres = adresDAO.findById(1);
        System.out.println("Found Adres: " + foundAdres);

        // Update the Adres
        adres.setWoonplaats("Rotterdam");
        adresDAO.update(adres);
        System.out.println("Adres updated.");

        // Delete the Adres
        adresDAO.delete(1);
        reizigerDAO.delete(123);
        System.out.println("Adres deleted.");
    }

    // Test method for OVChipkaartDAO
    public void testOVChipkaartDAO() {
        OVChipkaartDAOPsql ovChipkaartDAO = new OVChipkaartDAOPsql(getConnection());
        ReizigerDAOPsql reizigerDAO = new ReizigerDAOPsql(getConnection());


        Reiziger reiziger = new Reiziger(123, "J", null, "Hartjes", java.sql.Date.valueOf("2006-06-22"));
        reizigerDAO.save(reiziger);

        // Create a new OVChipkaart object
        OVChipkaart ovChipkaart = new OVChipkaart();
        ovChipkaart.setKaartNummer(1); // Set attributes as needed
        ovChipkaart.setGeldigTot(java.sql.Date.valueOf("2025-01-01"));
        ovChipkaart.setKlasse(1);
        ovChipkaart.setSaldo(100.00);
        ovChipkaart.setReiziger(reiziger.getReizigerId());

        // Persist the OVChipkaart
        ovChipkaartDAO.save(ovChipkaart);
        System.out.println("OVChipkaart persisted.");

        // Find the OVChipkaart by number
        OVChipkaart foundOVChipkaart = ovChipkaartDAO.findById(1);
        System.out.println(foundOVChipkaart.getKaartNummer());
        System.out.println(foundOVChipkaart.getReiziger());
        System.out.println(foundOVChipkaart.getSaldo());
        System.out.println(foundOVChipkaart.getKlasse());
        System.out.println("Found OVChipkaart: " + foundOVChipkaart);

        // Update the OVChipkaart
        ovChipkaart.setSaldo(200.00);
        ovChipkaartDAO.update(ovChipkaart);
        System.out.println("OVChipkaart updated.");

        // Delete the OVChipkaart
        ovChipkaartDAO.delete(1);
        reizigerDAO.delete(123);



        System.out.println("OVChipkaart deleted.");
    }
}
