package com.adss.demo.xml.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.adss.demo.xml.constant.MessageCode;
import com.adss.demo.xml.dto.ServiceResponse;
import com.adss.demo.xml.dto.SignXmlReq;
import com.adss.demo.xml.fileSigning.XMLAdESSHA1;
import com.adss.demo.xml.fileSigning.XMLAdESSHA256;
import com.adss.demo.xml.fileSigning.XMLAdESSHA384;
import com.adss.demo.xml.fileSigning.XMLBasicSHA1;
import com.adss.demo.xml.fileSigning.XMLBasicSHA256;
import com.adss.demo.xml.fileSigning.XMLBasicSHA384;

@RestController
@RequestMapping("api/xml")
public class SignFileController {
	
	@Autowired
	XMLAdESSHA1 xmlAdESSHA1;
	
	@Autowired
	XMLAdESSHA256 xmlAdESSHA256;
	
	@Autowired
	XMLAdESSHA384 xmlAdESSHA384;

	@PostMapping("/XMLAdESSHA1")
    public ResponseEntity<ServiceResponse<String>> signAdESSHA1(@RequestParam(value = "file", required = false) MultipartFile file, 
    															@RequestParam(value = "profileId", required = false) String profileId,
    															@RequestParam(value = "url", required = false) String url,
    															@RequestParam(value = "clientId", required = false) String clientId,
    															@RequestParam(value = "CertificateAlias") String CertificateAlias) throws Exception{
//		SignXmlReq req = new SignXmlReq(file.getBytes(), profileId, url, clientId, CertificateAlias);
		
		ServiceResponse<String> response = new ServiceResponse<>();
//		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		try {
			response = xmlAdESSHA1.signAdESSHA1(file, profileId,url,clientId,CertificateAlias);
			if (response.getCode() == MessageCode.SUCCESS) {
				return new ResponseEntity<ServiceResponse<String>>(response, HttpStatus.OK);
			} else {
				return new ResponseEntity<ServiceResponse<String>>(response, HttpStatus.BAD_REQUEST);
			} 
		}catch (Exception e) {
			
		}
		return new ResponseEntity<ServiceResponse<String>>(response, HttpStatus.BAD_REQUEST);
    }
	
	@PostMapping("/XMLAdESSHA256")
	public ResponseEntity<ServiceResponse<String>> signAdESSHA256(@RequestParam(value = "file", required = false) MultipartFile file, 
																@RequestParam(value = "profileId", required = false) String profileId,
																@RequestParam(value = "url", required = false) String url,
																@RequestParam(value = "clientId", required = false) String clientId,
																@RequestParam(value = "CertificateAlias") String CertificateAlias) throws Exception {
		SignXmlReq req = new SignXmlReq(file.getBytes(), profileId, url, clientId, CertificateAlias);

		ServiceResponse<String> response = new ServiceResponse<>();
//		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		try {
			response = xmlAdESSHA256.signAdESSHA256(req);
			if (response.getCode() == MessageCode.SUCCESS) {
				return new ResponseEntity<ServiceResponse<String>>(response, HttpStatus.OK);
			} else {
				return new ResponseEntity<ServiceResponse<String>>(response, HttpStatus.BAD_REQUEST);
			} 
		}catch (Exception e) {
			
		}
		return new ResponseEntity<ServiceResponse<String>>(response, HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/XMLAdESSHA384")
	public ResponseEntity<ServiceResponse<String>> signAdESSHA384(@RequestParam(value = "file", required = false) MultipartFile file, 
																@RequestParam(value = "profileId", required = false) String profileId,
																@RequestParam(value = "url", required = false) String url,
																@RequestParam(value = "clientId", required = false) String clientId,
																@RequestParam(value = "CertificateAlias") String CertificateAlias) throws Exception {
		SignXmlReq req = new SignXmlReq(file.getBytes(), profileId, url, clientId, CertificateAlias);
		
		ServiceResponse<String> response = new ServiceResponse<>();
//		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		try {
			response = xmlAdESSHA384.signAdESSHA384(req);
			if (response.getCode() == MessageCode.SUCCESS) {
				return new ResponseEntity<ServiceResponse<String>>(response, HttpStatus.OK);
			} else {
				return new ResponseEntity<ServiceResponse<String>>(response, HttpStatus.BAD_REQUEST);
			} 
		}catch (Exception e) {
			
		}
		return new ResponseEntity<ServiceResponse<String>>(response, HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/XMLBasicSHA1")
	public ResponseEntity<ServiceResponse<String>> signBasicSHA1(@RequestParam(value = "file", required = false) MultipartFile file, 
																@RequestParam(value = "profileId", required = false) String profileId,
																@RequestParam(value = "url", required = false) String url,
																@RequestParam(value = "clientId", required = false) String clientId,
																@RequestParam(value = "CertificateAlias") String CertificateAlias) throws Exception {
		
		SignXmlReq req = new SignXmlReq(file.getBytes(), profileId, url, clientId, CertificateAlias);
		
		ServiceResponse<String> response = new ServiceResponse<>();
//		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		try {
			response = XMLBasicSHA1.signBasicSHA1(req);
			if (response.getCode() == MessageCode.SUCCESS) {
				return new ResponseEntity<ServiceResponse<String>>(response, HttpStatus.OK);
			} else {
				return new ResponseEntity<ServiceResponse<String>>(response, HttpStatus.BAD_REQUEST);
			} 
		}catch (Exception e) {
			
		}
		return new ResponseEntity<ServiceResponse<String>>(response, HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/XMLBasicSHA256")
	public ResponseEntity<ServiceResponse<String>> signBasicSHA256(@RequestParam(value = "file", required = false) MultipartFile file, 
																	@RequestParam(value = "profileId", required = false) String profileId,
																	@RequestParam(value = "url", required = false) String url,
																	@RequestParam(value = "clientId", required = false) String clientId,
																	@RequestParam(value = "CertificateAlias") String CertificateAlias) throws Exception {
		SignXmlReq req = new SignXmlReq(file.getBytes(), profileId, url, clientId, CertificateAlias);
		
		ServiceResponse<String> response = new ServiceResponse<>();
//		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		try {
			response = XMLBasicSHA256.signBasicSHA256(req);
			if (response.getCode() == MessageCode.SUCCESS) {
				return new ResponseEntity<ServiceResponse<String>>(response, HttpStatus.OK);
			} else {
				return new ResponseEntity<ServiceResponse<String>>(response, HttpStatus.BAD_REQUEST);
			} 
		}catch (Exception e) {
			
		}
		return new ResponseEntity<ServiceResponse<String>>(response, HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/XMLBasicSHA384")
	public ResponseEntity<ServiceResponse<String>> signBasicSHA384(@RequestParam(value = "file", required = false) MultipartFile file, 
																	@RequestParam(value = "profileId", required = false) String profileId,
																	@RequestParam(value = "url", required = false) String url,
																	@RequestParam(value = "clientId", required = false) String clientId,
																	@RequestParam(value = "CertificateAlias") String CertificateAlias) throws Exception {
		SignXmlReq req = new SignXmlReq(file.getBytes(), profileId, url, clientId, CertificateAlias);
		
		ServiceResponse<String> response = new ServiceResponse<>();
//		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		try {
			response = XMLBasicSHA384.signBasicSHA384(req);
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
