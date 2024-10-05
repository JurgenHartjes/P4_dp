package nl.hu.dataPercistency.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Reiziger {

    private int reizigerId;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;
    private List<OVChipkaart> ovChipkaarten = new ArrayList<>();

    public Reiziger(int reizigerId, String voorl, String tussenv, String achtern, Date gbdatum) {
        this.reizigerId = reizigerId;
        this.voorletters = voorl;
        this.tussenvoegsel = tussenv;
        this.achternaam = achtern;
        this.geboortedatum = gbdatum;
    }


    //
    //  getters and setters
    //

    public int getReizigerId() {
        return reizigerId;
    }
    public void setReizigerId(int reizigerId) {
        this.reizigerId = reizigerId;
    }

    public String getVoorletters() {
        return voorletters;
    }
    public void setVoorletters(String voorletters) {
        this.voorletters = voorletters;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }
    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }
    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public String getNaam() {
        if (this.tussenvoegsel != null) {
            return this.voorletters + " " + this.achternaam;
        } else {
            return this.voorletters + " " + this. tussenvoegsel + " " + this.achternaam;
        }
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }
    public void setGeboortedatum(Date geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public List<OVChipkaart> getOvChipkaarten() {
        return ovChipkaarten;
    }
    public void addOvChipkaart(OVChipkaart ovChipkaart) {
        ovChipkaarten.add(ovChipkaart);
        ovChipkaart.setReiziger(reizigerId); // Set the back-reference
    }

    @Override
    public String toString() {
        return getNaam() + " is geboren op " + getGeboortedatum() + " en heeft reizigerId " + getReizigerId();
    }
}
