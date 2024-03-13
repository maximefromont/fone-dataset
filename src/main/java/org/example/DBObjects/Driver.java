package org.example.DBObjects;

public class Driver {

    private int idDriver; //UNIQUE & NOT NULL
    private String lastnameDriver; //NOT NULL
    private String firstnameDriver; //NOT NULL
    private String nationalityDriver; //NOT NULL

    //A driver has to have a lastname and a firstname
    public Driver(String lastnameDriver, String firstnameDriver, String nationalityDriver) {
        this.lastnameDriver = lastnameDriver;
        this.firstnameDriver = firstnameDriver;
        this.nationalityDriver = nationalityDriver;
    }

    //Default constructors for queries
    public Driver(int idDriver, String lastnameDriver, String firstnameDriver, String nationalityDriver) {
        this.idDriver = idDriver;
        this.lastnameDriver = lastnameDriver;
        this.firstnameDriver = firstnameDriver;
        this.nationalityDriver = nationalityDriver;
    }

    public Driver() {
    }

    public int getIdDriver() {
        return idDriver;
    }

    public void setIdDriver(int idDriver) { this.idDriver = idDriver; }

    public String getLastnameDriver() {
        return lastnameDriver;
    }

    public void setLastnameDriver(String lastnameDriver) {
        this.lastnameDriver = lastnameDriver;
    }

    public String getFirstnameDriver() {
        return firstnameDriver;
    }

    public void setFirstnameDriver(String firstnameDriver) {
        this.firstnameDriver = firstnameDriver;
    }

    public String getNationalityDriver() {
        return nationalityDriver;
    }

    public void setNationalityDriver(String nationalityDriver) {
        this.nationalityDriver = nationalityDriver;
    }
}