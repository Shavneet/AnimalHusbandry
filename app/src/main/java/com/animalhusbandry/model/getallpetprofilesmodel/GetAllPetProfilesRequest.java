package com.animalhusbandry.model.getallpetprofilesmodel;

/**
 * Created by grewalshavneet on 6/9/2017.
 */

public class GetAllPetProfilesRequest {
    private int page;

    private int size;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "ClassPojo [page = " + page + ", size = " + size + "]";
    }
}
