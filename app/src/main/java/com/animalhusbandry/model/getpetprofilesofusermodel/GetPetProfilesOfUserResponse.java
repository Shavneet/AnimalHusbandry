package com.animalhusbandry.model.getpetprofilesofusermodel;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.Data;

/**
 * Created by grewalshavneet on 6/7/2017.
 */
@Data
public class GetPetProfilesOfUserResponse implements Serializable {
    private Response response;
    private ArrayList<Result> userPetArrayList;


    @Data
    public class Response implements Serializable {
        private Result[] result;

        private String status;

        private String code;
    }

    @Data
    public class Result implements Serializable {
        private String breed;

        private String petId;

        private PetVaccinationsList[] petVaccinationsList;

        private String imageUrl;

        private String location;

        private String color;

        private String ownerMobileNumber;

        private String userId;

        private String microchipNumber;

        private String bloodLine;

        private String age;

        private String name;

        private String gender;
    }

    @Data
    public class PetVaccinationsList implements Serializable {

        private String name;

    }
}
