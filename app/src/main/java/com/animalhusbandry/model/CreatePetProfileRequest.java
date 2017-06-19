package com.animalhusbandry.model;

import java.util.ArrayList;

/**
 * Created by grewalshavneet on 5/26/2017.
 */

public class CreatePetProfileRequest {


    private String breed;

    private ArrayList<PetVaccinationsList> petVaccinationsList;

    private String location;

    private String color;

    private String ownerMobileNumber;

    private String microchipNumber;

    private String age;

    private String bloodLine;

    private String name;

    private String userId;

    private String image;

    private String gender;

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public ArrayList<PetVaccinationsList> getPetVaccinationsList() {
        return petVaccinationsList;
    }

    public void setPetVaccinationsList(ArrayList<PetVaccinationsList> petVaccinationsList) {
        this.petVaccinationsList = petVaccinationsList;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String getMicrochipNumber() {
        return microchipNumber;
    }

    public void setMicrochipNumber(String microchipNumber) {
        this.microchipNumber = microchipNumber;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBloodLine() {
        return bloodLine;
    }

    public void setBloodLine(String bloodLine) {
        this.bloodLine = bloodLine;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "ClassPojo [breed = " + breed + ", petVaccinationsList = " + petVaccinationsList + ", location = " + location + ", color = " + color + ", ownerMobileNumber = " + ownerMobileNumber + ", microchipNumber = " + microchipNumber + ", age = " + age + ", bloodLine = " + bloodLine + ", name = " + name + ", userId = " + userId + ", image = " + image + ", gender = " + gender + "]";
    }



}

