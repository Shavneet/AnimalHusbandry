package com.animalhusbandry.model;

import lombok.Data;

/**
 * Created by grewalshavneet on 5/11/2017.
 */
@Data
public class SignUpResponse {

    private Response response;
    @Data
    public class Response {
        private String message;

        private Result result;

        private String status;

        private String code;
    }
        @Data
        public class Result
        {
            private String lastName;

            private String phone;

            private String sessionId;

            private String email;

            private String deviceToken;

            private String userId;

            private String userFullName;

            private String firstName;
        }

}
