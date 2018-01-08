package com.fieldstaffapp.fieldstaffapp.model.existing_geotagging;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by debasmita on 11/8/16.
 */
public class ExistingGeotaggingInfo {
    @SerializedName("plant_height")
    @Expose
    private String plantHeight;
    @SerializedName("plant_condition")
    @Expose
    private String plantCondition;
    @SerializedName("plant_protection")
    @Expose
    private String plantProtection;
    @SerializedName("geotagging_id")
    @Expose
    private String geotaggingId;
    @SerializedName("plant_id")
    @Expose
    private String plantId;

    /**
     *
     * @return
     * The plantHeight
     */
    public String getPlantHeight() {
        return plantHeight;
    }

    /**
     *
     * @param plantHeight
     * The plant_height
     */
    public void setPlantHeight(String plantHeight) {
        this.plantHeight = plantHeight;
    }

    /**
     *
     * @return
     * The plantCondition
     */
    public String getPlantCondition() {
        return plantCondition;
    }

    /**
     *
     * @param plantCondition
     * The plant_condition
     */
    public void setPlantCondition(String plantCondition) {
        this.plantCondition = plantCondition;
    }

    /**
     *
     * @return
     * The plantProtection
     */
    public String getPlantProtection() {
        return plantProtection;
    }

    /**
     *
     * @param plantProtection
     * The plant_protection
     */
    public void setPlantProtection(String plantProtection) {
        this.plantProtection = plantProtection;
    }

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
     * The plantId
     */
    public String getPlantId() {
        return plantId;
    }

    /**
     *
     * @param plantId
     * The plant_id
     */
    public void setPlantId(String plantId) {
        this.plantId = plantId;
    }

}
