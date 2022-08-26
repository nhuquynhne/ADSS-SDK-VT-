package com.adss.demo.xml.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.adss.demo.xml.constant.MessageCode;
import com.adss.demo.xml.dto.ServiceResponse;
import com.adss.demo.xml.dto.SignRSXmlReq;
import com.adss.demo.xml.remoteSigning.XMLRSAdESSHA1;
import com.adss.demo.xml.remoteSigning.XMLRSAdESSHA256;
import com.adss.demo.xml.remoteSigning.XMLRSAdESSHA384;

@RestController
@RequestMapping("api/xml")
public class SignRemoteController {
	
	@PostMapping("/XMLRSAdESSHA1")
    public ResponseEntity<ServiceResponse<String>> signRSAdESSHA1(@RequestParam("file") MultipartFile file,
																	@RequestParam(value = "profileId", required = false) String profileId, 
																	@RequestParam(value = "url", required = false) String url,
																	@RequestParam(value = "clientId", required = false) String clientId,
																	@RequestParam(value = "CertificateAlias", required = false) String CertificateAlias,
																	@RequestParam(value = "userSigningCert", required = false) String userSigningCert,
																	@RequestParam(value = "signatureMimetype", required = false) String signatureMimetype,
																	@RequestParam(value = "hashAlgorithm", required = false) String hashAlgorithm,
																	@RequestParam(value = "signatureMode", required = false) String signatureMode,
																	@RequestParam(value = "requestMode", required = false) String requestMode,
																	@RequestParam(value = "authorisedSigning") String authorisedSigning,
																	@RequestParam(value = "userId", required = false) String userId,
																	@RequestParam(value = "userPassword", required = false) String userPassword,
																	@RequestParam(value = "dataDisplayed", required = false) String dataDisplayed) 
																			throws Exception{
		SignRSXmlReq req = new SignRSXmlReq(file.getBytes(), profileId, url, clientId, CertificateAlias, userSigningCert, signatureMimetype, hashAlgorithm, signatureMode, requestMode, authorisedSigning, userId, userPassword, dataDisplayed);
		
		ServiceResponse<String> response = new ServiceResponse<>();
//		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		try {
			response = XMLRSAdESSHA1.signRSAdESSHA1(req);
			if (response.getCode() == MessageCode.SUCCESS) {
				return new ResponseEntity<ServiceResponse<String>>(response, HttpStatus.OK);
			} else {
				return new ResponseEntity<ServiceResponse<String>>(response, HttpStatus.BAD_REQUEST);
			} 
		}catch (Exception e) {
			
		}
		return new ResponseEntity<ServiceResponse<String>>(response, HttpStatus.BAD_REQUEST);
    }
	
	@PostMapping("/XMLRSAdESSHA256")
    public ResponseEntity<ServiceResponse<String>> signRSAdESSHA256(@RequestParam(value = "file", required = false) MultipartFile file,
																	@RequestParam(value = "profileId", required = false) String profileId, 
																	@RequestParam(value = "url", required = false) String url,
																	@RequestParam(value = "clientId", required = false) String clientId,
																	@RequestParam(value = "CertificateAlias", required = false) String CertificateAlias,
																	@RequestParam(value = "userSigningCert", required = false) String userSigningCert,
																	@RequestParam(value = "signatureMimetype", required = false) String signatureMimetype,
																	@RequestParam(value = "hashAlgorithm", required = false) String hashAlgorithm,
																	@RequestParam(value = "signatureMode", required = false) String signatureMode,
																	@RequestParam(value = "requestMode", required = false) String requestMode,
																	@RequestParam(value = "authorisedSigning") String authorisedSigning,
																	@RequestParam(value = "userId", required = false) String userId,
																	@RequestParam(value = "userPassword", required = false) String userPassword,
																	@RequestParam(value = "dataDisplayed", required = false) String dataDisplayed) 
																			throws Exception{
		SignRSXmlReq req = new SignRSXmlReq(file.getBytes(), profileId, url, clientId, CertificateAlias, userSigningCert, signatureMimetype, hashAlgorithm, signatureMode, requestMode, authorisedSigning, userId, userPassword, dataDisplayed);

		
		ServiceResponse<String> response = new ServiceResponse<>();
//		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		try {
			response = XMLRSAdESSHA256.signRSAdESSHA256(req);
			if (response.getCode() == MessageCode.SUCCESS) {
				return new ResponseEntity<ServiceResponse<String>>(response, HttpStatus.OK);
			} else {
				return new ResponseEntity<ServiceResponse<String>>(response, HttpStatus.BAD_REQUEST);
			} 
		}catch (Exception e) {
			
		}
		return new ResponseEntity<ServiceResponse<String>>(response, HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/XMLRSAdESSHA384")
    public ResponseEntity<ServiceResponse<String>> signRSAdESSHA384(@RequestParam(value = "file", required = false) MultipartFile file,
																	@RequestParam(value = "profileId", required = false) String profileId, 
																	@RequestParam(value = "url", required = false) String url,
																	@RequestParam(value = "clientId", required = false) String clientId,
																	@RequestParam(value = "CertificateAlias", required = false) String CertificateAlias,
																	@RequestParam(value = "userSigningCert", required = false) String userSigningCert,
																	@RequestParam(value = "signatureMimetype", required = false) String signatureMimetype,
																	@RequestParam(value = "hashAlgorithm", required = false) String hashAlgorithm,
																	@RequestParam(value = "signatureMode", required = false) String signatureMode,
																	@RequestParam(value = "requestMode", required = false) String requestMode,
																	@RequestParam(value = "authorisedSigning") String authorisedSigning,
																	@RequestParam(value = "userId", required = false) String userId,
																	@RequestParam(value = "userPassword", required = false) String userPassword,
																	@RequestParam(value = "dataDisplayed", required = false) String dataDisplayed) 
																			throws Exception{
		
		
		SignRSXmlReq req = new SignRSXmlReq(file.getBytes(), profileId, url, clientId, CertificateAlias, userSigningCert, signatureMimetype, hashAlgorithm, signatureMode, requestMode, authorisedSigning, userId, userPassword, dataDisplayed);
		ServiceResponse<String> response = new ServiceResponse<>();
//		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		try {
			response = XMLRSAdESSHA384.signRSAdESSHA384(req);
			if (response.getCode() == MessageCode.SUCCESS) {
				return new ResponseEntity<ServiceResponse<String>>(response, HttpStatus.OK);
			} else {
				return new ResponseEntity<ServiceResponse<String>>(response, HttpStatus.BAD_REQUEST);
			} 
		}catch (Exception e) {
			
		}
		return new ResponseEntity<ServiceResponse<String>>(response, HttpStatus.BAD_REQUEST);
    }

}
