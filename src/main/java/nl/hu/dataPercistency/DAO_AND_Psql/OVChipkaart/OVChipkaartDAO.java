package nl.hu.dataPercistency.DAO_AND_Psql.OVChipkaart;

import nl.hu.dataPercistency.domain.OVChipkaart;

import java.util.List;

public interface OVChipkaartDAO {

    void save(OVChipkaart ovChipkaart);

    OVChipkaart findById(int kaartNummer);

    List<OVChipkaart> findByReizigerId(int reizigerId);

    void update(OVChipkaart ovChipkaart);

    void delete(int kaartNummer);
}
