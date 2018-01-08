package com.fieldstaffapp.fieldstaffapp.model.existing_image_upload;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by debasmita on 11/8/16.
 */
public class UploadLetestTreeImagesInfo {
    @SerializedName("geotagging_id")
    @Expose
    private String geotaggingId;
    @SerializedName("letest_image")
    @Expose
    private String letestImage;
    @SerializedName("visit_count")
    @Expose
    private Integer visitCount;

    /**
     *
     * @return
     * The geotaggingId
     */
    public String getGeotaggingId() {
        return geotaggingId;
    }

    /**
     *
     * @param geotaggingId
     * The geotagging_id
     */
    public void setGeotaggingId(String geotaggingId) {
        this.geotaggingId = geotaggingId;
    }

    /**
     *
     * @return
     * The letestImage
     */
    public String getLetestImage() {
        return letestImage;
    }

    /**
     *
     * @param letestImage
     * The letest_image
     */
    public void setLetestImage(String letestImage) {
        this.letestImage = letestImage;
    }

    /**
     *
     * @return
     * The visitCount
     */
    public Integer getVisitCount() {
        return visitCount;
    }

    /**
     *
     * @param visitCount
     * The visit_count
     */
    public void setVisitCount(Integer visitCount) {
        this.visitCount = visitCount;
    }

}
