package com.animalhusbandry.model.getpetprofilesofusermodel;

/**
 * Created by grewalshavneet on 6/7/2017.
 */

public class GetPetProfilesOfUserRequest {
    private int page;

    private String userId;

    private int size;

    public int getPage ()
    {
        return page;
    }

    public void setPage (int page)
    {
        this.page = page;
    }

    public String getUserId ()
    {
        return userId;
    }

    public void setUserId (String userId)
    {
        this.userId = userId;
    }

    public int getSize ()
    {
        return size;
    }

    public void setSize (int size)
    {
        this.size = size;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [page = "+page+", userId = "+userId+", size = "+size+"]";
    }
}
