package com.animalhusbandry.retrofit;


import com.animalhusbandry.model.AddFavouritePetRequest;
import com.animalhusbandry.model.AddFavouritePetResponse;
import com.animalhusbandry.model.AddMessageRequest;
import com.animalhusbandry.model.AddMessageResponse;
import com.animalhusbandry.model.ChangePasswordRequest;
import com.animalhusbandry.model.ChangePasswordResponse;
import com.animalhusbandry.model.DeleteUserProfileRequest;
import com.animalhusbandry.model.DeleteUserProfileResponse;
import com.animalhusbandry.model.ForgotPasswordRequest;
import com.animalhusbandry.model.ForgotPasswordResponse;
import com.animalhusbandry.model.GetAllFavouritePetByUserIdRequest;
import com.animalhusbandry.model.GetAllFavouritePetByUserIdResponse;
import com.animalhusbandry.model.GetAllMessageByUserIdRequest;
import com.animalhusbandry.model.GetAllMessageByUserIdResponse;
import com.animalhusbandry.model.GetUserDetailsRequest;
import com.animalhusbandry.model.GetUserDetailsResponse;
import com.animalhusbandry.model.LoginRequest;
import com.animalhusbandry.model.LoginResponse;
import com.animalhusbandry.model.LogoutRequest;
import com.animalhusbandry.model.LogoutResponse;
import com.animalhusbandry.model.SearchPetUsingFiltersRequest;
import com.animalhusbandry.model.SearchPetUsingFiltersResponse;
import com.animalhusbandry.model.SignUpRequest;
import com.animalhusbandry.model.SignUpResponse;
import com.animalhusbandry.model.createpetprofilemodel.CreatePetProfileRequest;
import com.animalhusbandry.model.createpetprofilemodel.CreatePetProfileResponse;
import com.animalhusbandry.model.getallpetprofilesmodel.GetAllPetProfilesRequest;
import com.animalhusbandry.model.getallpetprofilesmodel.GetAllPetProfilesResponse;
import com.animalhusbandry.model.getpetprofilesofusermodel.GetPetProfilesOfUserRequest;
import com.animalhusbandry.model.getpetprofilesofusermodel.GetPetProfilesOfUserResponse;
import com.animalhusbandry.model.updatepetprofile.UpdatePetProfileRequest;
import com.animalhusbandry.model.updatepetprofile.UpdatePetProfileResponse;
import com.animalhusbandry.model.updateuserdetailsmodel.UpdateUserDetailsRequest;
import com.animalhusbandry.model.updateuserdetailsmodel.UpdateUserDetailsResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by grewalshavneet on 5/10/2017.
 */

public interface ApiEndpointInterface {
    public static final String DEV_URL = "http://115.248.100.78:8092";
    public static final String TOKEN = "ADMN17899F5HJ320MSELSYG5FS5R2IJ8K6GEFJ1BK9NZW12ZMBQLT7CU9TFQH80A8N";

    public static final String PRODUCTION_URL = "";
    public static final String URL = DEV_URL;

    @POST("/dogHusbandryApp/ws/user/login")
    Call<LoginResponse> userLogin(@Body LoginRequest payload);

    @POST("/dogHusbandryApp/ws/user/userSignUp")
    Call<SignUpResponse> userSignUp(@Body SignUpRequest payload);

    @POST("/dogHusbandryApp/rest/user/password/forgot")
    Call<ForgotPasswordResponse> userForgotPassword(@Body ForgotPasswordRequest payload);

    @POST("/dogHusbandryApp/ws/user/logout")
    Call<LogoutResponse> userLogout(@Body LogoutRequest payload);

    @POST("/dogHusbandryApp/ws/user/getUserDetail")
    Call<GetUserDetailsResponse> getUserDetails(@Body GetUserDetailsRequest payload);

    @POST("dogHusbandryApp/ws/user/updateUserDetail")
    Call<UpdateUserDetailsResponse> updateUserDetails(@Body UpdateUserDetailsRequest payload);

    @POST("dogHusbandryApp/ws/user/deleteUserProfile")
    Call<DeleteUserProfileResponse> deleteUserProfile(@Body DeleteUserProfileRequest payload);

    @POST("dogHusbandryApp/ws/user/changePassword")
    Call<ChangePasswordResponse> changeUserPassword(@Body ChangePasswordRequest payload);

    @POST("dogHusbandryApp/ws/user/addPetProfile")
    Call<CreatePetProfileResponse> createPetProfile(@Body CreatePetProfileRequest payload);

    @POST("dogHusbandryApp/ws/user/getAllPetProfilesByUserId")
    Call<GetPetProfilesOfUserResponse> getPetProfilesOfUserId(@Body GetPetProfilesOfUserRequest payload);

    @POST("dogHusbandryApp/ws/user/getAllPetProfiles")
    Call<GetAllPetProfilesResponse> getAllPetProfiles(@Body GetAllPetProfilesRequest payload);

    @POST("dogHusbandryApp/ws/user/updatePetProfile")
    Call<UpdatePetProfileResponse> updatePetProfile(@Body UpdatePetProfileRequest payload);

    @POST("dogHusbandryApp/ws/user/addFavouritePet")
    Call<AddFavouritePetResponse> ADD_FAVOURITE_PET_RESPONSE_CALL(@Body AddFavouritePetRequest payload);

    @POST("dogHusbandryApp/ws/user/getAllFavouritePetByUserId")
    Call<GetAllFavouritePetByUserIdResponse> GET_ALL_FAVOURITE_PET_BY_USER_ID_RESPONSE_CALL(@Body GetAllFavouritePetByUserIdRequest payload);

    @POST("dogHusbandryApp/ws/user/getAllMessageByUserId")
    Call<GetAllMessageByUserIdResponse> GET_ALL_MESSAGE_BY_USER_ID_RESPONSE_CALL(@Body GetAllMessageByUserIdRequest payload);

    @POST("dogHusbandryApp/ws/user/addMessage")
    Call<AddMessageResponse> ADD_MESSAGE_RESPONSE_CALL(@Body AddMessageRequest payload);

    @POST("dogHusbandryApp/ws/user/searchPetUsingFilters")
    Call<SearchPetUsingFiltersResponse> SEARCH_PET_USING_FILTERS_RESPONSE_CALL(@Body SearchPetUsingFiltersRequest payload);
}
