package nl.hu.dataPercistency.DAO_AND_Psql.Adres;

import nl.hu.dataPercistency.domain.Adres;
import nl.hu.dataPercistency.domain.Reiziger;

public interface AdresDAO {

    void save(Adres adres, Reiziger reiziger);

    Adres findById(int adresId);

    void update(Adres adres);

    void delete(int adresId);
}
