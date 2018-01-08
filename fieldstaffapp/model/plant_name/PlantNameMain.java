package com.fieldstaffapp.fieldstaffapp.model.plant_name;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asi on 1/8/16.
 */
public class PlantNameMain {
    @SerializedName("is_success")
    @Expose
    private Boolean isSuccess;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("plant_info")
    @Expose
    private List<PlantInfo> plantInfo = new ArrayList<PlantInfo>();

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
     * The plantInfo
     */
    public List<PlantInfo> getPlantInfo() {
        return plantInfo;
    }

    /**
     *
     * @param plantInfo
     * The plant_info
     */
    public void setPlantInfo(List<PlantInfo> plantInfo) {
        this.plantInfo = plantInfo;
    }
}
