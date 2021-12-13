package com.example.sltreport_app;

public class Report {

    private String town;
    private String vilage;
    private String description;

    public Report(){}
    public Report(String town,String vilage,String description){
        this.town=town;
        this.vilage=vilage;
        this.description=description;
    }

    //getters

    public String getTown() {
        return town;
    }

    public String getVilage() {
        return vilage;
    }

    public String getDescription() {
        return description;
    }

    //setters

    public void setTown(String town) {
        this.town = town;
    }

    public void setVilage(String vilage) {
        this.vilage = vilage;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}