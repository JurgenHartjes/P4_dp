package nl.hu.dataPercistency.Hibernate;

import nl.hu.dataPercistency.DAO_AND_Psql.Product.ProductDAO;
import nl.hu.dataPercistency.domain.Product;
import nl.hu.dataPercistency.domain.OVChipkaart;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class ProductDAOHibernate implements ProductDAO {
    private Session session;

    // Constructor to initialize the Hibernate session
    public ProductDAOHibernate(Session session) {
        this.session = session;
    }

    @Override
    public void save(Product product) {
        Transaction transaction = session.beginTransaction();
        try {
            session.save(product);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace(); // Handle exceptions appropriately
        }
    }

    @Override
    public Product findById(int productNr) {
        Product product = null;
        try {
            product = session.get(Product.class, productNr);
        } catch (Exception e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }
        return product;
    }

    @Override
    public List<Product> findByOVChipkaart(int kaartNummer) {
        return null;
    }
//
//    @Override
//    public Product findByOVChipkaart(int kaartNummer) {
//        Product product = null;
//        try {
//            OVChipkaart ovChipkaart = session.get(OVChipkaart.class, kaartNummer);
//            if (ovChipkaart != null) {
//                product = ovChipkaart.getProduct();
//            }
//        } catch (Exception e) {
//            e.printStackTrace(); // Handle exceptions appropriately
//        }
//        return product;
//    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        try {
            products = session.createQuery("FROM Product", Product.class).list();
        } catch (Exception e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }
        return products;
    }

    @Override
    public void update(Product product) {
        Transaction transaction = session.beginTransaction();
        try {
            session.update(product);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace(); // Handle exceptions appropriately
        }
    }

    @Override
    public void delete(int productNr) {
        Transaction transaction = session.beginTransaction();
        try {
            Product product = session.get(Product.class, productNr);
            if (product != null) {
                session.delete(product);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace(); // Handle exceptions appropriately
        }
    }
}
