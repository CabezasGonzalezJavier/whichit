package com.thedeveloperworldisyours.whichit.models;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by javiergonzalezcabezas on 5/5/15.
 */
public class Likes {

    @Expose
    private Integer count;
    @Expose
    private List<Object> data = new ArrayList<Object>();

    /**
     *
     * @return
     * The count
     */
    public Integer getCount() {
        return count;
    }

    /**
     *
     * @param count
     * The count
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     *
     * @return
     * The data
     */
    public List<Object> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<Object> data) {
        this.data = data;
    }

}
