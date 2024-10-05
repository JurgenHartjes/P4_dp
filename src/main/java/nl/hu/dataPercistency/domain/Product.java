package nl.hu.dataPercistency.domain;

import java.util.ArrayList;

public class Product {
    private int productNr;
    private String naam;
    private String beschrijving;
    private double prijs;
    private ArrayList<Integer> eigenaren = new ArrayList<>();

    public void voegEigenaarToe(int eigenaarKaartNr)    {eigenaren.add(eigenaarKaartNr);}
    public void haalEigenaarWeg(int eigenaarKaartNr)    {eigenaren.remove(eigenaren.indexOf(eigenaarKaartNr));}


    public int getProductNr(){return productNr;}
    public void setProductNr(int productNr) {this.productNr = productNr;}

    public String getNaam(){return naam;}
    public void  setNaam(String naam) {this.naam = naam;}

    public String getBeschrijving(){return beschrijving;}
    public void  setBeschrijving(String beschrijving) {this.beschrijving = beschrijving;}

    public double getPrijs()    {return prijs;}
    public void setPrijs(double prijs)  {this.prijs = prijs;}

    public ArrayList<Integer> getEigenaren()    {return eigenaren;}
    public void setEigenaren(ArrayList<Integer> eigenaren)  {this.eigenaren = eigenaren;}
}
