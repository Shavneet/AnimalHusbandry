package com.animalhusbandry.model;

/**
 * Created by grewalshavneet on 5/24/2017.
 */

public class GetUserDetailsRequest {

    private String userId;

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
        return "ClassPojo [userId = "+userId+"]";
    }
}
