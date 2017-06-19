package com.animalhusbandry.model;

import lombok.Data;

/**
 * Created by grewalshavneet on 6/15/2017.
 */
@Data
public class UpdatePetProfileResponse {

    private Response response;

@Data
public class Response {
    private Result result;

    private String status;

    private String code;
}
@Data
public class Result {
    private String breed;

    private String petId;

    private PetVaccinationsList[] petVaccinationsList;

    private String imageUrl;

    private String location;

    private String color;

    private String ownerMobileNumber;

    private String microchipNumber;

    private String bloodLine;

    private String age;

    private String name;

    private String userId;

    private String gender;
}
@Data
public class PetVaccinationsList {
    private String id;

    private String name;
}
}
