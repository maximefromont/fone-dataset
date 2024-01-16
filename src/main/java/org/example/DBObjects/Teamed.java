package org.example.DBObjects;

import java.io.Serializable;
import java.util.Objects;

public class Teamed implements Serializable {

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

    private int idDriver; //UNIQUE & NOT NULL
    private int idConstructor; //UNIQUE & NOT NULL
    private String yearTeamed; //NOT NULL

    public Teamed(int idDriver, int idConstructor, String yearTeamed) {
        this.idDriver = idDriver;
        this.idConstructor = idConstructor;
        this.yearTeamed = yearTeamed;
    }

    public Teamed() {

    }

    public int getIdDriver() {
        return idDriver;
    }

    public void setIdDriver(int idDriver) {
        this.idDriver = idDriver;
    }

    public int getIdConstructor() {
        return idConstructor;
    }

    public void setIdConstructor(int idConstructor) {
        this.idConstructor = idConstructor;
    }

    public String getYearTeamed() {
        return yearTeamed;
    }

    public void setYearTeamed(String yearTeamed) {
        this.yearTeamed = yearTeamed;
    }
}
