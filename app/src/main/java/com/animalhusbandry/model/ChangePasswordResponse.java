package com.animalhusbandry.model;

import lombok.Data;

/**
 * Created by grewalshavneet on 5/25/2017.
 */
@Data
public class ChangePasswordResponse {
    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    private Response response;

    @Data
    public class Response {
        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        private String message;

        private String status;

        private String code;
    }
}
