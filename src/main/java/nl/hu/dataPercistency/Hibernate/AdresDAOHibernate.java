package nl.hu.dataPercistency.Hibernate;

import nl.hu.dataPercistency.DAO_AND_Psql.Adres.AdresDAO;
import nl.hu.dataPercistency.domain.Adres;
import nl.hu.dataPercistency.domain.Reiziger;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class AdresDAOHibernate implements AdresDAO {

    private Session session;

    // Constructor to initialize the Hibernate session
    public AdresDAOHibernate(Session session) {
        this.session = session;
    }

    @Override
    public void save(Adres adres, Reiziger reiziger) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            adres.setAdresId(0); // Setting to 0 to allow Hibernate to auto-generate the ID
            session.save(adres);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Rollback on exception
            }
            e.printStackTrace(); // Handle exceptions appropriately
        }
    }

    @Override
    public Adres findById(int adresId) {
        Adres adres = null;
        try {
            adres = session.get(Adres.class, adresId);
        } catch (Exception e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }
        return adres;
    }

    @Override
    public void update(Adres adres) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(adres);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Rollback on exception
            }
            e.printStackTrace(); // Handle exceptions appropriately
        }
    }

    @Override
    public void delete(int adresId) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Adres adres = session.get(Adres.class, adresId);
            if (adres != null) {
                session.delete(adres);
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
