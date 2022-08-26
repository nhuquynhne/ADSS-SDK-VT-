package com.example.adsssapi.office.controller;

import com.example.adsssapi.office.dto.OfficeRSARequest;
import com.example.adsssapi.office.dto.OfficeRequest;
import com.example.adsssapi.office.filesigning.OfficeBasicSHA1;
import com.example.adsssapi.office.remotesigning.OfficeRSAdeSSHA1;
import com.example.adsssapi.office.remotesigning.OfficeRSAdeSSHA256;
import com.example.adsssapi.office.remotesigning.OfficeRSAdeSSHA384;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "api/office")
public class OfficeBasicSHA1Resource {


    OfficeBasicSHA1 officeBasicSHA1 = new OfficeBasicSHA1();
    OfficeRSAdeSSHA1 officeRSAdeSSHA1 = new OfficeRSAdeSSHA1();
    OfficeRSAdeSSHA256 officeRSAdeSSHA256 = new OfficeRSAdeSSHA256();
    OfficeRSAdeSSHA384 officeRSAdeSSHA384 = new OfficeRSAdeSSHA384();

    @PostMapping(value = "/officeSHA1")
    public ResponseEntity<ServiceResponseOffice<String>> signOffice(
            @RequestParam(value = "file") MultipartFile inputFile,
//            @RequestParam(value = "fileOutput") MultipartFile fileOutput,
            @RequestParam(value = "url") String url,
            @RequestParam(value = "profileId") String profileId,
            @RequestParam(value = "certificateAlias") String certificateAlias,
            @RequestParam(value = "readFile") MultipartFile readFile,
            @RequestParam(value = "samples_test_client") String samples_test_client,
            @RequestParam(value = "setupId") String setupId
    ) throws Exception {
        OfficeRequest officeRequest = new OfficeRequest(inputFile ,url,profileId,certificateAlias,readFile.getBytes(),samples_test_client,setupId);
        ServiceResponseOffice<String> fileData = officeBasicSHA1.Office(officeRequest);
        return new ResponseEntity<ServiceResponseOffice<String>>(fileData, HttpStatus.OK);

    }
    @PostMapping(value = "/officeRSAdeSSHA1")
    public ResponseEntity<ServiceResponseOfficeRSA<String>> officeSHA1(
            @RequestParam(value = "adssServerUrl") String adssServerUrl,
            @RequestParam(value = "clientId") String clientId,
            @RequestParam(value = "certificateAlias") String certificateAlias,
            @RequestParam(value = "profileId") String profileId,
            @RequestParam(value = "inputFilePath") MultipartFile inputFilePath,
            @RequestParam(value = "SigSetupId") String SigSetupId,
            @RequestParam(value = "cerPath") String cerPath,
            @RequestParam(value = "filePath") MultipartFile filePath,
            @RequestParam(value = "contactInfo") String contactInfo,
            @RequestParam(value = "hashAlgorithm") String hashAlgorithm,
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "userPassword") String userPassword,
            @RequestParam(value = "documentId") String documentId

            ) throws Exception{
        OfficeRSARequest officeRSARequest = new OfficeRSARequest(adssServerUrl,clientId,certificateAlias,profileId
                ,inputFilePath,null,SigSetupId
                ,cerPath,filePath.getBytes(),contactInfo,hashAlgorithm,userId
                ,userPassword,documentId);

            ServiceResponseOfficeRSA<String> fileName = officeRSAdeSSHA1.OfficeRSA(officeRSARequest);

            return new ResponseEntity<ServiceResponseOfficeRSA<String>>(fileName, HttpStatus.OK);
//        return new ResponseEntity<ServiceResponseOfficeRSA<String>>((MultiValueMap<String, String>) fileName,HttpStatus.OK);
    }

    //office 256
    @PostMapping(value = "/officeRSAdeSSHA256")
    public ResponseEntity<ServiceResponseOfficeRSA<String>> officeSHA256(
            @RequestParam(value = "adssServerUrl") String adssServerUrl,
            @RequestParam(value = "clientId") String clientId,
            @RequestParam(value = "certificateAlias") String certificateAlias,
            @RequestParam(value = "profileId") String profileId,
            @RequestParam(value = "inputFilePath") MultipartFile inputFilePath,
            @RequestParam(value = "SigSetupId") String SigSetupId,
            @RequestParam(value = "cerPath") String cerPath,
            @RequestParam(value = "filePath") MultipartFile filePath,
            @RequestParam(value = "contactInfo") String contactInfo,
            @RequestParam(value = "hashAlgorithm") String hashAlgorithm,
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "userPassword") String userPassword,
            @RequestParam(value = "documentId") String documentId

    ) throws Exception{
        OfficeRSARequest officeRSARequest = new OfficeRSARequest(adssServerUrl,clientId,certificateAlias,profileId
                ,inputFilePath,null,SigSetupId
                ,cerPath,filePath.getBytes(),contactInfo,hashAlgorithm,userId
                ,userPassword,documentId);

        ServiceResponseOfficeRSA<String> fileName = officeRSAdeSSHA256.OfficeRSA(officeRSARequest);
        return new ResponseEntity<ServiceResponseOfficeRSA<String>>(fileName, HttpStatus.OK);
//        return new ResponseEntity<ServiceResponseOfficeRSA<String>>((MultiValueMap<String, String>) fileName,HttpStatus.OK);
    }

    //office348
    @PostMapping(value = "/officeRSAdeSSHA348")
    public ResponseEntity<ServiceResponseOfficeRSA<String>> officeSHA348(
            @RequestParam(value = "adssServerUrl") String adssServerUrl,
            @RequestParam(value = "clientId") String clientId,
            @RequestParam(value = "certificateAlias") String certificateAlias,
            @RequestParam(value = "profileId") String profileId,
            @RequestParam(value = "inputFilePath") MultipartFile inputFilePath,
            @RequestParam(value = "SigSetupId") String SigSetupId,
            @RequestParam(value = "cerPath") String cerPath,
            @RequestParam(value = "filePath") MultipartFile filePath,
            @RequestParam(value = "contactInfo") String contactInfo,
            @RequestParam(value = "hashAlgorithm") String hashAlgorithm,
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "userPassword") String userPassword,
            @RequestParam(value = "documentId") String documentId

    ) throws Exception{
        OfficeRSARequest officeRSARequest = new OfficeRSARequest(adssServerUrl,clientId,certificateAlias,profileId
                ,inputFilePath,null,SigSetupId
                ,cerPath,filePath.getBytes(),contactInfo,hashAlgorithm,userId
                ,userPassword,documentId);

        ServiceResponseOfficeRSA<String> fileName = officeRSAdeSSHA384.OfficeRSA(officeRSARequest);
        return new ResponseEntity<ServiceResponseOfficeRSA<String>>(fileName, HttpStatus.OK);
//        return new ResponseEntity<ServiceResponseOfficeRSA<String>>((MultiValueMap<String, String>) fileName,HttpStatus.OK);
    }
}
