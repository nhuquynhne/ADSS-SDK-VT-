package com.adss.demo.xml.constant;

public class MessageCode {
    /**DEFAULT */
    public static final Integer SUCCESS = 200;
    public static final Integer FAILED = 1;
    
    public static final Integer AUTHENTICATION_FAILED = 401;
    public static final String AUTHENTICATION_FAILED_MESSAGE = "Xác thực thất bại";
    
    public static final Integer AUTHENTICATION_WRONG_PASSWORD = 1001;
    public static final String AUTHENTICATION_WRONG_PASSWORD_MESSAGE = "Mật khẩu không trùng khớp";
    public static final Integer SIGNING_CERT_EXPIRED = 501;
    
    public static final int CERT_EXPIRED = 21001;
    public static final int CERT_REVOKED = 21002;
    public static final int CERT_FAILED_TO_CHECK_CRL = 21003;
    public static final int CERT_FAILED_TO_CHECK_OCSP = 21004;

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
