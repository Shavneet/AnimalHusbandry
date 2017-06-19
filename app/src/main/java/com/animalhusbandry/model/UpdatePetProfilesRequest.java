package com.animalhusbandry.model;

import java.util.ArrayList;

/**
 * Created by grewalshavneet on 6/15/2017.
 */

public class UpdatePetProfilesRequest {
    private String petId;

    private String breed;

    private String location;

    private String microchipNumber;

    private String image;

    private ArrayList<PetVaccinationsList> petVaccinationsList;

    private String color;

    private String ownerMobileNumber;

    private String userId;

    private String name;

    private String bloodLine;

    private String age;

    private String gender;

    public String getPetId() {
        return petId;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMicrochipNumber() {
        return microchipNumber;
    }

    public void setMicrochipNumber(String microchipNumber) {
        this.microchipNumber = microchipNumber;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<PetVaccinationsList> getPetVaccinationsList() {
        return petVaccinationsList;
    }

    public void setPetVaccinationsList(ArrayList<PetVaccinationsList> arrrayListVaccination) {
        this.petVaccinationsList = petVaccinationsList;
    }




    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getOwnerMobileNumber() {
        return ownerMobileNumber;
    }

    public void setOwnerMobileNumber(String ownerMobileNumber) {
        this.ownerMobileNumber = ownerMobileNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBloodLine() {
        return bloodLine;
    }

    public void setBloodLine(String bloodLine) {
        this.bloodLine = bloodLine;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "ClassPojo [petId = " + petId + ", breed = " + breed + ", location = " + location + ", microchipNumber = " + microchipNumber + ", image = " + image + ", petVaccinationsList = " + petVaccinationsList + ", color = " + color + ", ownerMobileNumber = " + ownerMobileNumber + ", userId = " + userId + ", name = " + name + ", bloodLine = " + bloodLine + ", age = " + age + ", gender = " + gender + "]";
    }



}
