package com.fieldstaffapp.fieldstaffapp.model.valid_user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by debasmita on 29/7/16.
 */
public class ValidUserMain {

    @SerializedName("is_success")
    @Expose
    private Boolean isSuccess;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("imei_Info")
    @Expose
    private ImeiInfo imeiInfo;

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
     * The imeiInfo
     */
    public ImeiInfo getImeiInfo() {
        return imeiInfo;
    }

    /**
     *
     * @param imeiInfo
     * The imei_Info
     */
    public void setImeiInfo(ImeiInfo imeiInfo) {
        this.imeiInfo = imeiInfo;
    }

}
