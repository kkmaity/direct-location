package com.fieldstaffapp.fieldstaffapp.model.auto_update;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by debasmita on 10/8/16.
 */
public class PlantLocationInfo {
    @SerializedName("msg")
    @Expose
    private String msg;

    /**
     *
     * @return
     * The msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     *
     * @param msg
     * The msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
