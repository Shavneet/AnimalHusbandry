package com.animalhusbandry.model;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by grewalshavneet on 6/22/2017.
 */
@Data
public class GetAllFavouritePetByUserIdResponse implements Serializable {
    private Response response;
    @Data
    public class Response
    {
        private Result[] result;

        private String status;

        private String code;}
    @Data
    public class Result implements Serializable {
        private String id;

        private String breed;

        private String petId;

        private String imageUrl;

        private String location;

        private String name;

        private String userId;

        private String isFavourite;

    }
    }
