package com.animalhusbandry.model;

import lombok.Data;

/**
 * Created by grewalshavneet on 5/18/2017.
 */
@Data
public class LogoutResponse {
    private Response response;


    @Data
    public class Response {
        private String message;

        private String status;

        private String code;
    }
}
