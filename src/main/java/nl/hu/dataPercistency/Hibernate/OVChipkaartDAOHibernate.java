package nl.hu.dataPercistency.Hibernate;

import nl.hu.dataPercistency.DAO_AND_Psql.OVChipkaart.OVChipkaartDAO;
import nl.hu.dataPercistency.DAO_AND_Psql.Product.ProductDAO;
//import nl.hu.dataPercistency.DAO_AND_Psql.Product.ProductDAOHibernate;
import nl.hu.dataPercistency.domain.OVChipkaart;
import nl.hu.dataPercistency.domain.Product;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOHibernate implements OVChipkaartDAO {
    private Session session;

    // Constructor to initialize the Hibernate session
    public OVChipkaartDAOHibernate(Session session) {
        this.session = session;
    }

    @Override
    public void save(OVChipkaart ovChipkaart) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            ovChipkaart.setKaartNummer(0); // Set to 0 for auto-generated ID if needed
            session.save(ovChipkaart);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Rollback on exception
            }
            e.printStackTrace(); // Handle exceptions appropriately
        }
    }

    @Override
    public OVChipkaart findById(int kaartNummer) {
        OVChipkaart ovChipkaart = null;
        try {
            ovChipkaart = session.get(OVChipkaart.class, kaartNummer);
            if (ovChipkaart != null) {
                // Assuming the product is lazily loaded; otherwise, fetch it explicitly here
                int productNr = ovChipkaart.getProduct() != null ? ovChipkaart.getProduct().getProductNr() : 0;
                if (productNr != 0) {
                    ProductDAO productDAO = new ProductDAOHibernate(session);
                    Product product = productDAO.findById(productNr);
                    ovChipkaart.setProduct(product);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }
        return ovChipkaart;
    }

    @Override
    public List<OVChipkaart> findByReizigerId(int reizigerId) {
        return null;
    }

//    @Override
//    public List<OVChipkaart> findByReizigerId(int reizigerId) {
//        List<OVChipkaart> ovChipkaarten = new ArrayList<>();
//        String hql = "FROM OVChipkaart o WHERE o.reizigerId = :reizigerId"; // Adjust if needed
//        try {
//            ovChipkaarten = session.createQuery(hql, OVChipkaart.class)
//                    .setParameter("reizigerId", reizigerId)
//                    .getResultList();
//        } catch (Exception e) {
//            e.printStackTrace(); // Handle exceptions appropriately
//        }
//        return ovChipkaarten;
//    }

    @Override
    public void update(OVChipkaart ovChipkaart) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(ovChipkaart);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Rollback on exception
            }
            e.printStackTrace(); // Handle exceptions appropriately
        }
    }

    @Override
    public void delete(int kaartNummer) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            OVChipkaart ovChipkaart = session.get(OVChipkaart.class, kaartNummer);
            if (ovChipkaart != null) {
                session.delete(ovChipkaart);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Rollback on exception
            }
            e.printStackTrace(); // Handle exceptions appropriately
        }
    }
}
