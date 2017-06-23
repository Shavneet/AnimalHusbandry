package com.animalhusbandry.model;

/**
 * Created by grewalshavneet on 6/21/2017.
 */

public class AddFavouritePetRequest {

    private String petId;

    private String userId;

    public String getPetId ()
    {
        return petId;
    }

    public void setPetId (String petId)
    {
        this.petId = petId;
    }

    public String getUserId ()
    {
        return userId;
    }

    public void setUserId (String userId)
    {
        this.userId = userId;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [petId = "+petId+", userId = "+userId+"]";
    }
}
