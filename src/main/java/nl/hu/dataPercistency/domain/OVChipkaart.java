package nl.hu.dataPercistency.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
public class OVChipkaart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Assuming kaartNummer is auto-generated
    private int kaartNummer;

    private Date geldigTot;
    private int klasse;
    private double saldo;

    @ManyToOne
    @JoinColumn(name = "reiziger_id", nullable = false) // Foreign key column in OVChipkaart table
    private Reiziger reiziger; // Change from int reizigerId to Reiziger reiziger

    @ManyToOne
    @JoinColumn(name = "product_id") // Foreign key column in OVChipkaart table
    private Product product;

    //
    // getters and setters
    //

    public int getKaartNummer() {
        return kaartNummer;
    }

    public void setKaartNummer(int kaartNummer) {
        this.kaartNummer = kaartNummer;
    }

    public Date getGeldigTot() {
        return geldigTot;
    }

    public void setGeldigTot(Date geldigTot) {
        this.geldigTot = geldigTot;
    }

    public int getKlasse() {
        return klasse;
    }

    public void setKlasse(int klasse) {
        this.klasse = klasse;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Reiziger getReiziger() { // Changed return type from int to Reiziger
        return reiziger;
    }

    public void setReiziger(Reiziger reiziger) { // Change parameter from int to Reiziger
        this.reiziger = reiziger;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "kaart " + this.kaartNummer + " heeft een saldo van €" + this.saldo;
    }
}
