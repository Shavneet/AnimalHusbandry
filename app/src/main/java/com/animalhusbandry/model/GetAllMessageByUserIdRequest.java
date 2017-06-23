package com.animalhusbandry.model;

import lombok.Data;

/**
 * Created by grewalshavneet on 6/22/2017.
 */
@Data
public class GetAllMessageByUserIdRequest {

    private String page;

    private String userId;

    private String size;

}


