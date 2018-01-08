package com.fieldstaffapp.fieldstaffapp.model.auto_update;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by debasmita on 10/8/16.
 */
public class AutoUpdateLocation {
    @SerializedName("is_success")
    @Expose
    private Boolean isSuccess;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("plant_location_info")
    @Expose
    private PlantLocationInfo plantLocationInfo;

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
     * The plantLocationInfo
     */
    public PlantLocationInfo getPlantLocationInfo() {
        return plantLocationInfo;
    }

    /**
     *
     * @param plantLocationInfo
     * The plant_location_info
     */
    public void setPlantLocationInfo(PlantLocationInfo plantLocationInfo) {
        this.plantLocationInfo = plantLocationInfo;
    }
}
