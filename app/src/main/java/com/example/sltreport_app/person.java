package com.example.sltreport_app;

public class person {

    private String town;

    // Variable to store data corresponding
    // to lastname keyword in database
    private String vilage;

    // Variable to store data corresponding
    // to age keyword in database
    private String description;


    public String getVilage() {
        return vilage;
    }

    public void setVilage(String vilage) {
        this.vilage = vilage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }
}
