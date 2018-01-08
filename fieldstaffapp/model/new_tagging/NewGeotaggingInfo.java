package com.fieldstaffapp.fieldstaffapp.model.new_tagging;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by debasmita on 3/8/16.
 */
public class NewGeotaggingInfo {
    @SerializedName("corporation_id")
    @Expose
    private String corporationId;
    @SerializedName("division_id")
    @Expose
    private String divisionId;
    @SerializedName("plant_id")
    @Expose
    private String plantId;
    @SerializedName("plant_height")
    @Expose
    private String plantHeight;
    @SerializedName("plant_condition")
    @Expose
    private String plantCondition;
    @SerializedName("plant_protection")
    @Expose
    private String plantProtection;
    @SerializedName("plantation_type")
    @Expose
    private String plantationType;
    @SerializedName("block_plantation")
    @Expose
    private String blockPlantation;
    @SerializedName("plant_street_address")
    @Expose
    private String plantStreetAddress;
    @SerializedName("plant_adopter")
    @Expose
    private String plantAdopter;
    @SerializedName("adopter_phone")
    @Expose
    private String adopterPhone;
    @SerializedName("plant_no")
    @Expose
    private String plantNo;
    @SerializedName("geotagging_id")
    @Expose
    private String geotaggingId;

    /**
     *
     * @return
     * The corporationId
     */
    public String getCorporationId() {
        return corporationId;
    }

    /**
     *
     * @param corporationId
     * The corporation_id
     */
    public void setCorporationId(String corporationId) {
        this.corporationId = corporationId;
    }

    /**
     *
     * @return
     * The divisionId
     */
    public String getDivisionId() {
        return divisionId;
    }

    /**
     *
     * @param divisionId
     * The division_id
     */
    public void setDivisionId(String divisionId) {
        this.divisionId = divisionId;
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
     * The plantationType
     */
    public String getPlantationType() {
        return plantationType;
    }

    /**
     *
     * @param plantationType
     * The plantation_type
     */
    public void setPlantationType(String plantationType) {
        this.plantationType = plantationType;
    }

    /**
     *
     * @return
     * The blockPlantation
     */
    public String getBlockPlantation() {
        return blockPlantation;
    }

    /**
     *
     * @param blockPlantation
     * The block_plantation
     */
    public void setBlockPlantation(String blockPlantation) {
        this.blockPlantation = blockPlantation;
    }

    /**
     *
     * @return
     * The plantStreetAddress
     */
    public String getPlantStreetAddress() {
        return plantStreetAddress;
    }

    /**
     *
     * @param plantStreetAddress
     * The plant_street_address
     */
    public void setPlantStreetAddress(String plantStreetAddress) {
        this.plantStreetAddress = plantStreetAddress;
    }

    /**
     *
     * @return
     * The plantAdopter
     */
    public String getPlantAdopter() {
        return plantAdopter;
    }

    /**
     *
     * @param plantAdopter
     * The plant_adopter
     */
    public void setPlantAdopter(String plantAdopter) {
        this.plantAdopter = plantAdopter;
    }

    /**
     *
     * @return
     * The adopterPhone
     */
    public String getAdopterPhone() {
        return adopterPhone;
    }

    /**
     *
     * @param adopterPhone
     * The adopter_phone
     */
    public void setAdopterPhone(String adopterPhone) {
        this.adopterPhone = adopterPhone;
    }

    /**
     *
     * @return
     * The plantNo
     */
    public String getPlantNo() {
        return plantNo;
    }

    /**
     *
     * @param plantNo
     * The plant_no
     */
    public void setPlantNo(String plantNo) {
        this.plantNo = plantNo;
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


}
