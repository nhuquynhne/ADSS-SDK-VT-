package com.example.adsssapi.pdf.controller;

public class MessageCode {
    public static final int SUCCESS = 200;
    public static final int ERROR = 500;

    /**
     * Key and cert
     */
    public static final int CERT_EXPIRED = 21001;
    public static final int CERT_REVOKED = 21002;
    public static final int CERT_FAILED_TO_CHECK_CRL = 21003;
    public static final int CERT_FAILED_TO_CHECK_OCSP = 21004;


    /**
     * Signing
     */
    // Hash
    public static final int HASH_IS_REQUIRED = 31001;


    //Msg
    public static final int MSG_IS_REQUIRED = 32001;
    public static final int MSG_FAILED_TO_SIGN = 32002;

    //Binary
    public static final int FILE_IS_REQUIRED = 33001;

    //PDF
    public static final int DETECT_STRING_NOT_FOUND = 34001;
    public static final int FILE_FAILED_TO_READ = 34002;
    public static final int FILE_FAILED_TO_DETECT = 34003;
    public static final int IMAGE_IS_REQUIRED = 34004;
    public static final int DETAIL_INFO_IS_REQUIRED = 34005;
    public static final int SIGNATURE_POSITION_INVALID = 34006;
    public static final int PAGE_NUMBER_INVALID = 34007;
    public static final int FAILED_TO_GET_TIMESTAMP_TOKEN = 34008;


}
