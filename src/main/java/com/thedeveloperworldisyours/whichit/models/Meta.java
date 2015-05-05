package com.thedeveloperworldisyours.whichit.models;

import com.google.gson.annotations.Expose;

/**
 * Created by javiergonzalezcabezas on 5/5/15.
 */
public class Meta {

    @Expose
    private Integer code;

    /**
     *
     * @return
     * The code
     */
    public Integer getCode() {
        return code;
    }

    /**
     *
     * @param code
     * The code
     */
    public void setCode(Integer code) {
        this.code = code;
    }

}
