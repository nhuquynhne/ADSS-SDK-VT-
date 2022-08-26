package com.example.adsssapi.office.controller;

import java.util.concurrent.CompletableFuture;

public class ServiceResponseOfficeRSA<T>{
    private int code;
    private T data;

    public ServiceResponseOfficeRSA(int code, T data) {
        super();
        this.code = code;
        this.data = data;
    }

    public ServiceResponseOfficeRSA() {
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
