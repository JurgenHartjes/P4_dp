package nl.hu.dataPercistency.domain;

import javax.persistence.*;

@Entity
public class Adres {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Assuming adres_id is auto-generated
    private int adres_id;

    private String postcode;
    private String huisnummer;
    private String straat;
    private String woonplaats;

    //
    // getters and setters
    //

    public int getAdresId() {
        return adres_id;
    }

    public void setAdresId(int adres_id) {
        this.adres_id = adres_id;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getHuisnummer() {
        return huisnummer;
    }

    public void setHuisnummer(String huisnummer) {
        this.huisnummer = huisnummer;
    }

    public String getStraat() {
        return straat;
    }

    public void setStraat(String straat) {
        this.straat = straat;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }

    public String getAdres() {
        return getStraat() + " " + getHuisnummer();
    }

    @Override
    public String toString() {
        return getAdres() + ", " + getWoonplaats() + " met postcode " + getPostcode();
    }
}
