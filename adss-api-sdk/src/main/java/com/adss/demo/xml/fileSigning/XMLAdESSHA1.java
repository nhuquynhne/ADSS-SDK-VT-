package com.adss.demo.xml.fileSigning;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.adss.demo.xml.constant.ErrorDesc;
import com.adss.demo.xml.constant.MessageCode;
import com.adss.demo.xml.dto.ServiceResponse;
import com.adss.demo.xml.util.CommonUtils;
import com.ascertia.adss.client.api.signing.SigningRequest;
import com.ascertia.adss.client.api.signing.SigningResponse;

@Service
public class XMLAdESSHA1 {

	// @Override
	public ServiceResponse<String> signAdESSHA1(MultipartFile file, String profileId, String url, String clientId, String CertificateAlias) throws Exception {

		byte[] byteArr = file.getBytes();
		InputStream inputFilePath = new ByteArrayInputStream(byteArr);


//		String inputFilePath = "./data/signing/test_input_unsigned.xml";
		String outputFilePath = "./data/signing/test_output_AdES_sha10dddsfsdfsdfsdf.xml";
//		String url = "http://171.224.245.9:8777";
//		String profleId = "adss:signing:profile:02";




		// Constructing request for pdf signing
		SigningRequest obj_signingRequest = new SigningRequest(clientId, inputFilePath, SigningRequest.MIME_TYPE_XML);
		obj_signingRequest.setProfileId(profileId);
		obj_signingRequest.setCertificateAlias(CertificateAlias);
		obj_signingRequest.setRequestMode(SigningRequest.DSS);
		// Writing request to disk

		System.out.println("\n/**********************************************************************/");
		System.out.println("\nA request has been sent to sign the XML document. Waiting for response...");

		// Sending the above constructed request to the ADSS server
		SigningResponse obj_signingResponse = (SigningResponse) obj_signingRequest.send(url.trim() );
		// Writing response to disk

		
		// Parsing the response
		if (obj_signingResponse.isSuccessful()) {
			System.out.println("\nXML document has been signed successfully.");
//			 System.out.println("Writing signed XML document to a file path'"+args[1].trim()+"'");
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
			
//			byte[] bytes = obj_signingResponse.getDocuments();
//			String data = Base64.getEncoder().encodeToString((byte[]) obj_signingResponse.getDocument());
//        	
//            return new ServiceResponse<String>(MessageCode.SUCCESS, ErrorDesc.SUCCESS, data);
			
			
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
			}
			System.out.println("Reason : " + obj_signingResponse.getErrorMessage());
			 System.out.println(obj_signingResponse.getErrorMessage());
			return new ServiceResponse<String>(MessageCode.FAILED, ErrorDesc.FAILED, null);
		}
		System.out.println("\n/**********************************************************************/");
		return null;
	}

}