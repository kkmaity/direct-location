package com.fieldstaffapp.fieldstaffapp.model.division;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asi on 1/8/16.
 */
public class DivisionMain {
    @SerializedName("is_success")
    @Expose
    private Boolean isSuccess;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("division_info")
    @Expose
    private List<DivisionInfo> divisionInfo = new ArrayList<DivisionInfo>();

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
     * The divisionInfo
     */
    public List<DivisionInfo> getDivisionInfo() {
        return divisionInfo;
    }

    /**
     *
     * @param divisionInfo
     * The division_info
     */
    public void setDivisionInfo(List<DivisionInfo> divisionInfo) {
        this.divisionInfo = divisionInfo;
    }
}
