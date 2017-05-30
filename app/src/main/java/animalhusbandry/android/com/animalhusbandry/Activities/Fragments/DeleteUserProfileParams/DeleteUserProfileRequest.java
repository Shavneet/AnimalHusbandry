package animalhusbandry.android.com.animalhusbandry.Activities.Fragments.DeleteUserProfileParams;

/**
 * Created by grewalshavneet on 5/25/2017.
 */

public class DeleteUserProfileRequest {

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ClassPojo [userId = " + userId + "]";
    }
}
