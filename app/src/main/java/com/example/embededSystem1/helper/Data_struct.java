package com.example.embededSystem1.helper;

public class Data_struct {
    private Float data;
    private Long timestamp;

    public Data_struct (Float data, Long timestamp){
        this.data = data;
        this.timestamp = timestamp;
    }

    public Data_struct(){}

    public Float getData() {
        return data;
    }

    public void setData(Float data) {
        this.data = data;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}