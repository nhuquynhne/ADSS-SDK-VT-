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
public class OfficeRequest {
    private MultipartFile fileInput;
    private String url;
    private String profileId;
    private String certificateAlias;
    private byte[] readFile;
    private String samples_test_client;
    private String setupId;
}
