package com.fieldstaffapp.fieldstaffapp.model.ImageUpload;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by debasmita on 8/8/16.
 */
public class ImageUploadMain {
    @SerializedName("is_success")
    @Expose
    private Boolean isSuccess;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("upload_tree_images_info")
    @Expose
    private UploadTreeImagesInfo uploadTreeImagesInfo;

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
     * The uploadTreeImagesInfo
     */
    public UploadTreeImagesInfo getUploadTreeImagesInfo() {
        return uploadTreeImagesInfo;
    }

    /**
     *
     * @param uploadTreeImagesInfo
     * The upload_tree_images_info
     */
    public void setUploadTreeImagesInfo(UploadTreeImagesInfo uploadTreeImagesInfo) {
        this.uploadTreeImagesInfo = uploadTreeImagesInfo;
    }
}
