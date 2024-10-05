package nl.hu.dataPercistency.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Assuming productNr is auto-generated
    private int productNr;

    private String naam;
    private String beschrijving;
    private double prijs;

    @ElementCollection
    @CollectionTable(name = "product_eigenaren", joinColumns = @JoinColumn(name = "product_id"))
    private List<Integer> eigenaren = new ArrayList<>();

    public void voegEigenaarToe(int eigenaarKaartNr) {
        eigenaren.add(eigenaarKaartNr);
    }

    public void haalEigenaarWeg(int eigenaarKaartNr) {
        eigenaren.remove(eigenaren.indexOf(eigenaarKaartNr));
    }

    public int getProductNr() {
        return productNr;
    }

    public void setProductNr(int productNr) {
        this.productNr = productNr;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public double getPrijs() {
        return prijs;
    }

    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }

    public List<Integer> getEigenaren() {
        return eigenaren;
    }

    public void setEigenaren(ArrayList<Integer> eigenaren) {
        this.eigenaren = eigenaren;
    }
}
