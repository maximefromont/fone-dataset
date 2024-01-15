package org.example.DBObjects;

public class Race {

    private int idRace; //UNIQUE & NOT NULL
    private String locationRace; //UNIQUE & NOT NULL

    public Race(String locationRace) {
        this.locationRace = locationRace;
    }

    public Race() {

    }

    public int getIdRace() {
        return idRace;
    }

    public void setIdRace(int idRace) {
        this.idRace = idRace;
    }

    public String getLocationRace() {
        return locationRace;
    }

    public void setLocationRace(String locationRace) {
        this.locationRace = locationRace;
    }
}
