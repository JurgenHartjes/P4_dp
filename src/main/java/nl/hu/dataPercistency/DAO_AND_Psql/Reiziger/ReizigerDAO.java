package nl.hu.dataPercistency.DAO_AND_Psql.Reiziger;

import nl.hu.dataPercistency.domain.Reiziger;

import java.util.List;

public interface ReizigerDAO {
    void save(Reiziger reiziger);

    Reiziger findById(int id);

    List<Reiziger> findAll();

    void update(Reiziger reiziger);

    void delete(int id);
}
