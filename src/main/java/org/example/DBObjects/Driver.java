package org.example.DBObjects;

public class Driver {

    private int idDriver; //UNIQUE & NOT NULL
    private String lastnameDriver; //NOT NULL
    private String firstnameDriver; //NOT NULL

    //A driver has to have a lastname and a firstname
    public Driver(String lastnameDriver, String firstnameDriver) {
        this.lastnameDriver = lastnameDriver;
        this.firstnameDriver = firstnameDriver;
    }

    //Empty constructor (useful ? The tutorial says so...)
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

}