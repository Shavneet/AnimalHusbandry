package com.animalhusbandry.model;

import lombok.Data;

/**
 * Created by grewalshavneet on 6/21/2017.
 */
@Data
public class AddFavouritePetResponse {
    private Response response;

    @Data
    public class Response
    {
        private Result result;

        private String status;

        private String code;}

    @Data
    public class Result {
        private String id;

        private String petId;

        private String name;

        private String userId;

        private String isFavourite;
    }
    }
