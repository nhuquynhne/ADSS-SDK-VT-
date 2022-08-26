package com.example.adsssapi.pdf.controller;


import com.example.adsssapi.pdf.filesigning.PDFAdESSHA1;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping(value = "api/pdf")
public class SingningController {


    @PostMapping(value = "PDFAdESSHA1")
    public ResponseEntity<ServiceResponse<String>> signBinary(@RequestParam("file") MultipartFile file) throws Exception {
        ServiceResponse<String> signature = PDFAdESSHA1.PDF(file);
        if (signature.getCode() == MessageCode.SUCCESS) {
            return new ResponseEntity<ServiceResponse<String>>(signature, HttpStatus.OK);
        } else {
            return new ResponseEntity<ServiceResponse<String>>(signature, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

