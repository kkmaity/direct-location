package com.fieldstaffapp.fieldstaffapp.model.division;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by asi on 1/8/16.
 */
public class DivisionInfo {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("corporation_id")
    @Expose
    private String corporationId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("no")
    @Expose
    private String no;

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

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
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The no
     */
    public String getNo() {
        return no;
    }

    /**
     *
     * @param no
     * The no
     */
    public void setNo(String no) {
        this.no = no;
    }
}
