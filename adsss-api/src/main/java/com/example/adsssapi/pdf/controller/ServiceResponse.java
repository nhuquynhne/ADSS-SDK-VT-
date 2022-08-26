package com.example.adsssapi.pdf.controller;

public class ServiceResponse<T> {

    private int code;
    private T data;

    public ServiceResponse(int code, T data) {
        super();
        this.code = code;
        this.data = data;
    }

    public ServiceResponse() {
        super();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
