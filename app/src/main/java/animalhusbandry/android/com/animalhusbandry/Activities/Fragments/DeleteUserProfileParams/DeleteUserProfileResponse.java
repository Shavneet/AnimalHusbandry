package animalhusbandry.android.com.animalhusbandry.Activities.Fragments.DeleteUserProfileParams;

import lombok.Data;

/**
 * Created by grewalshavneet on 5/25/2017.
 */
@Data
public class DeleteUserProfileResponse {
    private Response response;

    @Data
    public class Response {
        private String message;

        private String status;

        private String code;
    }
}
