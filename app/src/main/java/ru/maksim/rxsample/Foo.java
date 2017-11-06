package ru.maksim.rxsample;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Foo {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("count")
    @Expose
    private Integer count;

    public String getDate() {
        return date == null ? "undefined" : date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "date=" + getDate() + " count=" + getCount();
    }
}