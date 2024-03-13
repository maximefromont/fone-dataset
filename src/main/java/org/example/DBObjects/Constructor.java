package org.example.DBObjects;

public class Constructor {

    private int idConstructor; //UNIQUE & NOT NULL
    private String nameConstructor; //UNIQUE & NOT NULL
    private String nationalityConstructor; //NOT NULL

    public Constructor(String nameConstructor, String nationalityConstructor) {
        this.nameConstructor = nameConstructor;
        this.nationalityConstructor = nationalityConstructor;
    }

    //Default constructors for queries
    public Constructor(int idConstructor, String nameConstructor, String nationalityConstructor) {
        this.idConstructor = idConstructor;
        this.nameConstructor = nameConstructor;
        this.nationalityConstructor = nationalityConstructor;
    }

    public Constructor() {
    }

    public int getIdConstructor() {
        return idConstructor;
    }

    public void setIdConstructor(int idConstructor) {
        this.idConstructor = idConstructor;
    }

    public String getNameConstructor() {
        return nameConstructor;
    }

    public void setNameConstructor(String nameConstructor) {
        this.nameConstructor = nameConstructor;
    }

    public String getNationalityConstructor() {
        return nationalityConstructor;
    }

    public void setNationalityConstructor(String nationalityConstructor) {
        this.nationalityConstructor = nationalityConstructor;
    }
}
