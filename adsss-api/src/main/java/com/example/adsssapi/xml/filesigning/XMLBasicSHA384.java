package com.example.adsssapi.xml.filesigning;

import com.ascertia.adss.client.api.signing.SigningRequest;
import com.ascertia.adss.client.api.signing.SigningResponse;

public class XMLBasicSHA384 {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String inputFilePath = "./data/signing/test_input_unsigned.xml";
		String outputFilePath = "./data/signing/test_output_basic_sha384.xml";
		String url = "http://125.212.254.26:8777";
		String profleId = "adss:signing:profile:015";

		// Constructing request for pdf signing
		SigningRequest obj_signingRequest = new SigningRequest("samples_test_client", inputFilePath.trim(),
				SigningRequest.MIME_TYPE_XML);
		obj_signingRequest.setProfileId(profleId);
		obj_signingRequest.setCertificateAlias("signing-demo");
		obj_signingRequest.setRequestMode(SigningRequest.DSS);
		// Writing request to disk

		System.out.println("\n/**********************************************************************/");
		System.out.println("\nA request has been sent to sign the XML document. Waiting for response...");

		// Sending the above constructed request to the ADSS server
		SigningResponse obj_signingResponse = (SigningResponse) obj_signingRequest
				.send(url.trim() + "/adss/signing/dss");
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
		} else {
			System.out.println("Failed to sign XML document");
			System.out.println("Result Major : " + obj_signingResponse.getResultMajor());
			if (obj_signingResponse.getResultMinor() != null && !obj_signingResponse.getResultMinor().equals("")) {
				System.out.println("Result Minor : " + obj_signingResponse.getResultMinor());
			}
			System.out.println("Reason : " + obj_signingResponse.getErrorMessage());
			// System.out.println(obj_signingResponse.getErrorMessage());
		}
		System.out.println("\n/**********************************************************************/");
	}

}
