package nl.hu.dataPercistency.Hibernate;

import nl.hu.dataPercistency.DAO_AND_Psql.Reiziger.ReizigerDAO;
import nl.hu.dataPercistency.domain.Reiziger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ReizigerDAOHibernate implements ReizigerDAO {
    private SessionFactory sessionFactory;

    // Constructor to inject the session factory
    public ReizigerDAOHibernate(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Reiziger reiziger) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(reiziger);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public Reiziger findById(int id) {
        Session session = sessionFactory.openSession();
        try {
            return session.get(Reiziger.class, id);
        } finally {
            session.close();
        }
    }

    @Override
    public List<Reiziger> findAll() {
        Session session = sessionFactory.openSession();
        try {
            Query<Reiziger> query = session.createQuery("from Reiziger", Reiziger.class);
            return query.getResultList();
        } finally {
            session.close();
        }
    }

    @Override
    public void update(Reiziger reiziger) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(reiziger);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Reiziger reiziger = session.get(Reiziger.class, id);
            if (reiziger != null) {
                session.delete(reiziger);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
