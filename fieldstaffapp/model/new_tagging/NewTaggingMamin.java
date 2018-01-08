package com.fieldstaffapp.fieldstaffapp.model.new_tagging;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by debasmita on 3/8/16.
 */
public class NewTaggingMamin {

    @SerializedName("is_success")
    @Expose
    private Boolean isSuccess;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("new_geotagging_info")
    @Expose
    private NewGeotaggingInfo newGeotaggingInfo;

    /**
     *
     * @return
     * The isSuccess
     */
    public Boolean getIsSuccess() {
        return isSuccess;
    }

    /**
     *
     * @param isSuccess
     * The is_success
     */
    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    /**
     *
     * @return
     * The code
     */
    public String getCode() {
        return code;
    }

    /**
     *
     * @param code
     * The code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     *
     * @return
     * The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     * The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     *
     * @return
     * The newGeotaggingInfo
     */
    public NewGeotaggingInfo getNewGeotaggingInfo() {
        return newGeotaggingInfo;
    }

    /**
     *
     * @param newGeotaggingInfo
     * The new_geotagging_info
     */
    public void setNewGeotaggingInfo(NewGeotaggingInfo newGeotaggingInfo) {
        this.newGeotaggingInfo = newGeotaggingInfo;
    }
}
