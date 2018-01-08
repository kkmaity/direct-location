package com.fieldstaffapp.fieldstaffapp.model.existing_plant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
public class ExistingPlantMain {
    @SerializedName("is_success")
    @Expose
    private Boolean isSuccess;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("geotagging_details_info")
    @Expose
    private List<GeotaggingDetailsInfo> geotaggingDetailsInfo = new ArrayList<GeotaggingDetailsInfo>();

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<GeotaggingDetailsInfo> getGeotaggingDetailsInfo() {
        return geotaggingDetailsInfo;
    }

    public void setGeotaggingDetailsInfo(List<GeotaggingDetailsInfo> geotaggingDetailsInfo) {
        this.geotaggingDetailsInfo = geotaggingDetailsInfo;
    }

}
