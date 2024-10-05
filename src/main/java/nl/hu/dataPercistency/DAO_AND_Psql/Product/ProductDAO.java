package nl.hu.dataPercistency.DAO_AND_Psql.Product;

import nl.hu.dataPercistency.domain.Product;

import java.util.List;

public interface ProductDAO {
    void save(Product product);

    Product findById(int productNr);

    List<Product> findByOVChipkaart(int kaartNummer);

    List<Product> findAll();

    void update(Product product);

    void delete(int productNr);
}
