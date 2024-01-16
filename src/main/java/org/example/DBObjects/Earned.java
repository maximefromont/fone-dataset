package org.example.DBObjects;

import java.io.Serializable;
import java.util.Objects;

public class Earned implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Earned earned = (Earned) o;
        return idDriver == earned.idDriver &&
                idRace == earned.idRace;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDriver, idRace);
    }

    private int idDriver; //UNIQUE & NOT NULL
    private int idRace; //UNIQUE & NOT NULL
    private int pointsEarned; //NOT NULL
    private String yearEarned; //NOT NULL

    public Earned(int idDriver, int idRace, int pointsEarned, String yearEarned) {
        this.idDriver = idDriver;
        this.idRace = idRace;
        this.pointsEarned = pointsEarned;
        this.yearEarned = yearEarned;
    }

    public Earned() {

    }

    public int getIdDriver() {
        return idDriver;
    }

    public void setIdDriver(int idDriver) {
        this.idDriver = idDriver;
    }

    public int getIdRace() {
        return idRace;
    }

    public void setIdRace(int idRace) {
        this.idRace = idRace;
    }

    public int getPointsEarned() {
        return pointsEarned;
    }

    public void setPointsEarned(int pointsEarned) {
        this.pointsEarned = pointsEarned;
    }

    public String getYearEarned() {
        return yearEarned;
    }

    public void setYearEarned(String yearEarned) {
        this.yearEarned = yearEarned;
    }
}
