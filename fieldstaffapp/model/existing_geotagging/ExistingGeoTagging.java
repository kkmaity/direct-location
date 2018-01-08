package com.fieldstaffapp.fieldstaffapp.model.existing_geotagging;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by debasmita on 11/8/16.
 */
public class ExistingGeoTagging {
    @SerializedName("is_success")
    @Expose
    private Boolean isSuccess;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("existing_geotagging_info")
    @Expose
    private ExistingGeotaggingInfo existingGeotaggingInfo;

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
     * The existingGeotaggingInfo
     */
    public ExistingGeotaggingInfo getExistingGeotaggingInfo() {
        return existingGeotaggingInfo;
    }

    /**
     *
     * @param existingGeotaggingInfo
     * The existing_geotagging_info
     */
    public void setExistingGeotaggingInfo(ExistingGeotaggingInfo existingGeotaggingInfo) {
        this.existingGeotaggingInfo = existingGeotaggingInfo;
    }

}
