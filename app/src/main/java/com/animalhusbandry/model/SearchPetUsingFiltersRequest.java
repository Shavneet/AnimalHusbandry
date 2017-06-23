package com.animalhusbandry.model;

import lombok.Data;

/**
 * Created by grewalshavneet on 6/23/2017.
 */
@Data
public class SearchPetUsingFiltersRequest {

    private String breed;

    private String page;

    private String location;

    private String name;

    private String userId;

    private String size;

}
