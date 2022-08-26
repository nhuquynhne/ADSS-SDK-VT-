package com.example.adsssapi.office.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class OfficeRSARequest {

    private String adssServerUrl;
    private String clientId;
    private String certificateAlias;
    private String profileId;
    private MultipartFile inputFilePath;
    private MultipartFile outputFilePath;
    private String SigSetupId;
    private String cerPath;
    private byte[] filePath;
    private String contactInfo;
    private String hashAlgorithm;
    private String userId;
    private String userPassword;
    private String documentId;
}
