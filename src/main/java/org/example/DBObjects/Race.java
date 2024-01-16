package org.example.DBObjects;

public class Race {

    private int idRace; //UNIQUE & NOT NULL
    private String cityLocationRace; //UNIQUE & NOT NULL
    private String countryLocationRace; //NOT NULL

    public Race(String cityLocationRace, String countryLocationRace) {
        this.cityLocationRace = cityLocationRace;
        this.countryLocationRace = countryLocationRace;
    }

    public int getIdRace() {
        return idRace;
    }

    public void setIdRace(int idRace) {
        this.idRace = idRace;
    }

    public String getCityLocationRace() {
        return cityLocationRace;
    }

    public void setCityLocationRace(String cityLocationRace) {
        this.cityLocationRace = cityLocationRace;
    }

    public String getCountryLocationRace() {
        return countryLocationRace;
    }

    public void setCountryLocationRace(String countryLocationRace) {
        this.countryLocationRace = countryLocationRace;
    }
}
