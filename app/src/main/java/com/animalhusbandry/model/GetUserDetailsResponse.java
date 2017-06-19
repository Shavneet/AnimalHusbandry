package com.animalhusbandry.model;

import lombok.Data;

/**
 * Created by grewalshavneet on 5/24/2017.
 */
@Data
public class GetUserDetailsResponse {
    private Response response;

    @Data
    public class Response
    {
        private Result result;

        private String status;

        private String code;}

    @Data
    public class Result {
        private String lastName;

        private String phone;

        private String email;

        private String userId;

        private String userFullName;

        private String firstName;
    }
    }
