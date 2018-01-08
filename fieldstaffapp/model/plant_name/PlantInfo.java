package com.fieldstaffapp.fieldstaffapp.model.plant_name;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by asi on 1/8/16.
 */
public class PlantInfo {
    @SerializedName("id")
    @Expose
    private String id;
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
