package com.animalhusbandry.model;

import lombok.Data;

/**
 * Created by grewalshavneet on 6/22/2017.
 */
@Data
public class AddMessageRequest {
    private String petId;

    private String userId;

    private String comment;

}
