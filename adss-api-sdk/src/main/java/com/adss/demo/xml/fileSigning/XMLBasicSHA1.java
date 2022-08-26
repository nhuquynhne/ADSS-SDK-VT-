package com.adss.demo.xml.fileSigning;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.springframework.stereotype.Service;

import com.adss.demo.xml.constant.ErrorDesc;
import com.adss.demo.xml.constant.MessageCode;
import com.adss.demo.xml.dto.ServiceResponse;
import com.adss.demo.xml.dto.SignXmlReq;
import com.adss.demo.xml.util.CommonUtils;
import com.ascertia.adss.client.api.signing.SigningRequest;
import com.ascertia.adss.client.api.signing.SigningResponse;

@Service
public class XMLBasicSHA1 {

	public static ServiceResponse<String> signBasicSHA1(SignXmlReq req) throws Exception {
		// TODO Auto-generated method stub
		//String inputFilePath = "./data/signing/test_input_unsigned.xml";
		InputStream inputFilePath = new ByteArrayInputStream(req.getFile());
		String outputFilePath = "./data/signing/test_output_basic_sha1.xml";
//		String url = "http://10.0.21.99:8777/";
//		String profleId = "adss:signing:profile:013";
		if(req.getUrl() == null || req.getClientId() == null) {
		     
		      System.out.println(inputFilePath);
		      return new ServiceResponse<String>(MessageCode.FAILED, ErrorDesc.LACK_INFORMATION , null);  
		    }
//		byte [] byteArr=file.getBytes();
//		InputStream inputFilePath = new ByteArrayInputStream(req.getFile());

		// Constructing request for pdf signing
		SigningRequest obj_signingRequest = new SigningRequest(req.getClientId(), inputFilePath,
				SigningRequest.MIME_TYPE_XML);
		obj_signingRequest.setProfileId(req.getProfileId());
		obj_signingRequest.setCertificateAlias(req.getCertificateAlias());
		obj_signingRequest.setRequestMode(SigningRequest.DSS);
		// Writing request to disk

		System.out.println("\n/**********************************************************************/");
		System.out.println("\nA request has been sent to sign the XML document. Waiting for response...");

		// Sending the above constructed request to the ADSS server
		SigningResponse obj_signingResponse = (SigningResponse) obj_signingRequest
				.send(req.getUrl().trim());
		// Writing response to disk

		// Parsing the response
		if (obj_signingResponse.isSuccessful()) {
			System.out.println("\nXML document has been signed successfully.");
			// System.out.println("Writing signed XML document to a file path
			// '"+args[1].trim()+"'");
			if (obj_signingResponse.getStatus() != null) {
				System.out.println("\nStatus : " + obj_signingResponse.getStatus());
			}
			System.out.println("Result Major : " + obj_signingResponse.getResultMajor());
			if (obj_signingResponse.getResultMinor() != null && !obj_signingResponse.getResultMinor().equals("")) {
				System.out.println("Result Minor : " + obj_signingResponse.getResultMinor());
			}
			if (obj_signingResponse.getRequestId() != null && !obj_signingResponse.getRequestId().equals("")) {
				System.out.println("\nRequest ID : " + obj_signingResponse.getRequestId());
			}
			obj_signingResponse.publishDocument(outputFilePath.trim());
			
			String res = null;
			try {
                res = CommonUtils.encodeFileToBase64Binary(outputFilePath);
                System.out.println(res);
//                response.setData(res);
//                response.setCode(0;)
                if (res != null) {
                	System.out.println(res);
                    return new ServiceResponse<String>(MessageCode.SUCCESS, ErrorDesc.SUCCESS, res);
                }

                return new ServiceResponse<String>(MessageCode.FAILED, ErrorDesc.FAILED, null);
                
            
            } catch (Exception e) {
                // TODO: handle exception
            	e.printStackTrace();
            }
		} else {
			System.out.println("Failed to sign XML document");
			System.out.println("Result Major : " + obj_signingResponse.getResultMajor());
			if (obj_signingResponse.getResultMinor() != null && !obj_signingResponse.getResultMinor().equals("")) {
				System.out.println("Result Minor : " + obj_signingResponse.getResultMinor());
				String message= obj_signingResponse.getErrorMessage();
				return new ServiceResponse<String>(MessageCode.FAILED, message, null);
			}
			System.out.println("Reason : " + obj_signingResponse.getErrorMessage());
			String message= obj_signingResponse.getErrorMessage();
			return new ServiceResponse<String>(MessageCode.FAILED, message, null);
			// System.out.println(obj_signingResponse.getErrorMessage());
		}
		System.out.println("\n/**********************************************************************/");
		return null;
	}

}
