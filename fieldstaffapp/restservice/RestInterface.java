package com.fieldstaffapp.fieldstaffapp.restservice;


import com.fieldstaffapp.fieldstaffapp.model.ImageUpload.ImageUploadMain;
import com.fieldstaffapp.fieldstaffapp.model.auto_update.AutoUpdateLocation;
import com.fieldstaffapp.fieldstaffapp.model.existing_geotagging.ExistingGeoTagging;
import com.fieldstaffapp.fieldstaffapp.model.existing_image_upload.ExistingImageUp;
import com.fieldstaffapp.fieldstaffapp.model.existing_plant.ExistingPlantMain;
import com.fieldstaffapp.fieldstaffapp.model.valid_user.ValidUserMain;
import com.fieldstaffapp.fieldstaffapp.model.corporation.CorporationMain;
import com.fieldstaffapp.fieldstaffapp.model.division.DivisionMain;
import com.fieldstaffapp.fieldstaffapp.model.new_tagging.NewTaggingMamin;
import com.fieldstaffapp.fieldstaffapp.model.plant_name.PlantNameMain;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RestInterface {

    String BASE_URL = "http://www.mydevfactory.com/";

  /*  @Multipart
    @POST("~mousumi/Iservices/index/registration")
    Call<Registration> createUser(@Part("fname") RequestBody firstName,
                                  @Part("lname") RequestBody lastName,
                                  @Part("email") RequestBody email,
                                  @Part("pwd") RequestBody password,
                                  @Part("user_type") RequestBody userType,
                                  @Part("ph") RequestBody phnNo,
                                  @Part("device_type") RequestBody deviceType,
                                  @Part("regid") RequestBody regId,
                                  @Part("address") RequestBody address,
                                  @Part("add_lat") RequestBody addressLat,
                                  @Part("add_long") RequestBody addressLng,
                                  @Part MultipartBody.Part image);
*/

    @FormUrlEncoded
    @POST("~shreya/FieldStaffApp/WebServices/func_imei")
    Call<ValidUserMain> checkValidUser(@Field("fromApp") String fromApp,
                                       @Field("apptype") String appType,
                                       @Field("pagetype") String pageType,
                                       @Field("imei_number") String imeiNo,
                                       @Field("lat") String latitude,
                                       @Field("long") String longitude);
    @FormUrlEncoded
    @POST("~shreya/FieldStaffApp/WebServices/func_corporation")
    Call<CorporationMain> getCorporationName(@Field("fromApp") String fromApp,
                                             @Field("apptype") String appType,
                                             @Field("pagetype") String pageType);
    @FormUrlEncoded
    @POST("~shreya/FieldStaffApp/WebServices/func_division")
    Call<DivisionMain> getDivisionName(@Field("fromApp") String fromApp,
                                       @Field("apptype") String appType,
                                       @Field("pagetype") String pageType,
                                       @Field("corporation_id") String corporationId);
    @FormUrlEncoded
    @POST("~shreya/FieldStaffApp/WebServices/func_plant")
    Call<PlantNameMain> getPlantName(@Field("fromApp") String fromApp,
                                     @Field("apptype") String appType,
                                     @Field("pagetype") String pageType);
    @FormUrlEncoded
    @POST("~shreya/FieldStaffApp/WebServices/add_newGeotagging")
    Call<NewTaggingMamin> postNewTaggingOne(@Field("fromApp") String fromApp,
                                            @Field("apptype") String appType,
                                            @Field("pagetype") String pageType,
                                            @Field("imei_id") String imeiNo,
                                            @Field("corporation_id") String corpId,
                                            @Field("division_id") String divId,
                                            @Field("plant_id") String plantId,
                                            @Field("plant_height") String plantHeight,
                                            @Field("plant_condition") String plantCondition,
                                            @Field("plant_protection") String plantProtection,
                                            @Field("plantation_type") String plantType,
                                            @Field("plant_street_address") String plantStreetAdd,
                                            @Field("plant_adopter") String plantAdopter,
                                            @Field("adopter_phone") String adopterPhone,
                                            @Field("c_m_type") String cMType,
                                            @Field("d_w_type") String dWType,
                                            @Field("block_plantation") String blockPlantation);

    @Multipart
    @POST("~shreya/FieldStaffApp/WebServices/uploadTreeImages")
    Call<ImageUploadMain> uploadTreeImages(@Part("fromApp") RequestBody fromApp,
                                           @Part("apptype") RequestBody appType,
                                           @Part("pagetype") RequestBody pageType,
                                           @Part("plant_no") RequestBody plant_no,
                                           @Part("plant_id") RequestBody plant_id,
                                           @Part("imei_number") RequestBody imei_number,
                                           @Part("lat1") RequestBody lat1,
                                           @Part("long1") RequestBody long1,
                                           @Part("geotagging_id") RequestBody geotagging_id,
                                           @Part("lat2") RequestBody lat2,
                                           @Part("long2") RequestBody long2,
                                           @Part("lat3") RequestBody lat3,
                                           @Part("long3") RequestBody long3,
                                           @Part("lat4") RequestBody lat4,
                                           @Part("long4") RequestBody long4,
                                           @Part MultipartBody.Part image1,
                                           @Part MultipartBody.Part image2,
                                           @Part MultipartBody.Part image3,
                                           @Part MultipartBody.Part image4);

    @FormUrlEncoded
    @POST("~shreya/FieldStaffApp/WebServices/getGeotaggingList")
    Call<ExistingPlantMain> getExistingPlantList(@Field("fromApp") String fromApp,
                                                 @Field("apptype") String appType,
                                                 @Field("pagetype") String pageType,
                                                 @Field("imei_number") String imeiNo,
                                                 @Field("lat") String latitude,
                                                 @Field("long") String longitude);
    @FormUrlEncoded
    @POST("~shreya/FieldStaffApp/WebServices/plant_location")
    Call<AutoUpdateLocation> autoUpdateLocation(@Field("fromApp") String fromApp,
                                                @Field("apptype") String appType,
                                                @Field("pagetype") String pageType,
                                                @Field("imei_number") String imeiNo,
                                                @Field("lat") String latitude,
                                                @Field("long") String longitude);
    @FormUrlEncoded
    @POST("~shreya/FieldStaffApp/WebServices/exsistingGeotagging")
    Call<ExistingGeoTagging> existingGeotagging(@Field("fromApp") String fromApp,
                                                @Field("apptype") String appType,
                                                @Field("pagetype") String pageType,
                                                @Field("plant_no") String plantNo,
                                                @Field("plant_height") String plantHeight,
                                                @Field("plant_condition") String plantCondition,
                                                @Field("plant_protection") String plantProtection,
                                                @Field("geotagging_id") String geoTaggingId);
    @Multipart
    @POST("~shreya/FieldStaffApp/WebServices/uploadImageForExistingTree")
    Call<ExistingImageUp> uploadImageForExistingTree(@Part("fromApp") RequestBody fromApp,
                                                     @Part("apptype") RequestBody appType,
                                                     @Part("pagetype") RequestBody pageType,
                                                     @Part("plant_no") RequestBody plant_no,
                                                     @Part("plant_id") RequestBody plant_id,
                                                     @Part("lat") RequestBody lati,
                                                     @Part("long") RequestBody longi,
                                                     @Part("geotagging_id") RequestBody geotagging_id,
                                                     @Part("tree_image_id") RequestBody tree_image_id,
                                                     @Part MultipartBody.Part image);
}
