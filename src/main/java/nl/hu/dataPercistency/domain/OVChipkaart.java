package nl.hu.dataPercistency.domain;

import java.util.Date;

public class OVChipkaart {
    private int kaartNummer;
    private Date geldigTot;
    private int klasse;
    private double saldo;
    private int reizigerId;
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

    public int getReiziger() {
        return reizigerId;
    }
    public void setReiziger(int reiziger) {
        this.reizigerId = reiziger;
    }

    public Product getProduct()   {return product;}
    public void setProduct(Product product) {this.product = product;}



    @Override
    public String toString()    {
        return "kaart " + this.kaartNummer + " heeft een saldo van €" + this.saldo;
    }
}
