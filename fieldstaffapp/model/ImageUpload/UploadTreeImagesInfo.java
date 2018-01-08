package com.fieldstaffapp.fieldstaffapp.model.ImageUpload;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by debasmita on 8/8/16.
 */
public class UploadTreeImagesInfo {

    @SerializedName("geotagging_id")
    @Expose
    private String geotaggingId;
    @SerializedName("image_name1")
    @Expose
    private String imageName1;
    @SerializedName("image_name2")
    @Expose
    private String imageName2;
    @SerializedName("image_name3")
    @Expose
    private String imageName3;
    @SerializedName("image_name4")
    @Expose
    private String imageName4;

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
     * The imageName1
     */
    public String getImageName1() {
        return imageName1;
    }

    /**
     *
     * @param imageName1
     * The image_name1
     */
    public void setImageName1(String imageName1) {
        this.imageName1 = imageName1;
    }

    /**
     *
     * @return
     * The imageName2
     */
    public String getImageName2() {
        return imageName2;
    }

    /**
     *
     * @param imageName2
     * The image_name2
     */
    public void setImageName2(String imageName2) {
        this.imageName2 = imageName2;
    }

    /**
     *
     * @return
     * The imageName3
     */
    public String getImageName3() {
        return imageName3;
    }

    /**
     *
     * @param imageName3
     * The image_name3
     */
    public void setImageName3(String imageName3) {
        this.imageName3 = imageName3;
    }

    /**
     *
     * @return
     * The imageName4
     */
    public String getImageName4() {
        return imageName4;
    }

    /**
     *
     * @param imageName4
     * The image_name4
     */
    public void setImageName4(String imageName4) {
        this.imageName4 = imageName4;
    }

}
