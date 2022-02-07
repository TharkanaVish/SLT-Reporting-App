package com.example.sltreport_app;
import java.io.Serializable;
import com.google.firebase.database.Exclude;

public class Report implements Serializable{
    @Exclude
    private String key;

    private String town;
    private String vilage;
    private String description;
    private String location;
    private String imageName;
    private String superViser;
    private String doneimageName;

    public Report(){}




    public Report(String town, String vilage, String description,String superViser){
        this.town=town;
        this.vilage=vilage;
        this.description=description;
        this.location=location;
        this.imageName=imageName;
        this.doneimageName=doneimageName;
        this.superViser=superViser;
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

    public String getLocation(){return location;}

    public String getImageName(){return imageName;}

    public String getSuperViser() {
        return superViser;
    }


    public String getDoneimageName() {
        return doneimageName;
    }


    //setters

    public void setSuperViser(String superViser) {
        this.superViser = superViser;
    }
    public void setDoneimageName(String doneimageName) {
        this.doneimageName = doneimageName;
    }
    public void setTown(String town) {
        this.town = town;
    }

    public void setVilage(String vilage) {
        this.vilage = vilage;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location){this.location=location;}

    public void setImageName(String imageName) { this.imageName = imageName; }
    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }
}