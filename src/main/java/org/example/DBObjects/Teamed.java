package org.example.DBObjects;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Teamed implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teamed teamed = (Teamed) o;
        return idDriver == teamed.idDriver &&
                idConstructor == teamed.idConstructor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDriver, idConstructor);
    }

    private int idTeam; //UNIQUE & NOT NULL
    private Driver idDriver; //NOT NULL
    private Constructor idConstructor; //NOT NULL
    private String yearTeamed; //NOT NULL

    public Teamed(Driver idDriver, Constructor idConstructor, String yearTeamed) {
        this.idDriver = idDriver;
        this.idConstructor = idConstructor;
        this.yearTeamed = yearTeamed;
    }

    public Teamed() {

    }

    public int getIdTeam() { return  idTeam; }
    public void setIdTeam(int idTeam) { this.idTeam = idTeam; }

    public Driver getIdDriver() {
        return idDriver;
    }
    public void setIdDriver(Driver idDriver) {
        this.idDriver = idDriver;
    }

    public Constructor getIdConstructor() {
        return idConstructor;
    }
    public void setIdConstructor(Constructor idConstructor) {
        this.idConstructor = idConstructor;
    }

    public String getYearTeamed() {
        return yearTeamed;
    }
    public void setYearTeamed(String yearTeamed) {
        this.yearTeamed = yearTeamed;
    }
}
