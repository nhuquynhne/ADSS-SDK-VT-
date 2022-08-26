package com.example.adsssapi.pdf.filesigning;

import com.ascertia.adss.client.api.signing.PdfSigningRequest;
import com.ascertia.adss.client.api.signing.PdfSigningResponse;
import com.ascertia.adss.client.api.signing.SigningRequest;

public class PDFAdESSHA384 {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String inputFilePath = "./data/signing/test_input_unsigned.pdf";
		String outputFilePath = "./data/signing/test_output_ades_sha384.pdf";
		String url = "http://125.212.254.26:8777";
		String profleId = "adss:signing:profile:015";

		// Constructing request for pdf signing
		PdfSigningRequest obj_signingRequest = new PdfSigningRequest("samples_test_client", inputFilePath.trim());
		obj_signingRequest.setProfileId(profleId);
		obj_signingRequest.setCertificateAlias("signing-demo");
		obj_signingRequest.setRequestMode(SigningRequest.DSS);
		// Writing request to disk

		System.out.println("\n/**********************************************************************/");
		System.out.println("\nA request has been sent to sign the PDF. Waiting for response...");

		// Sending the above constructed request to the ADSS server
		PdfSigningResponse obj_signingResponse = (PdfSigningResponse) obj_signingRequest
				.send(url.trim() + "/adss/signing/dss");
		// Writing response to disk
		// Parsing the response
		if (obj_signingResponse.isSuccessful()) {
			System.out.println("\nPDF has been signed successfully.");
			System.out.println("Result Major : " + obj_signingResponse.getResultMajor());
			if (obj_signingResponse.getResultMinor() != null && !obj_signingResponse.getResultMinor().equals("")) {
				System.out.println("Result Minor : " + obj_signingResponse.getResultMinor());
			}
			// System.out.println("Writing signed PDF document to a file path
			// '"+args[1].trim()+"'");
			obj_signingResponse.publishDocument(outputFilePath.trim());
		} else {
			System.out.println("Failed to sign the PDF");
			System.out.println("Result Major : " + obj_signingResponse.getResultMajor());
			if (obj_signingResponse.getResultMinor() != null && !obj_signingResponse.getResultMinor().equals("")) {
				System.out.println("Result Minor : " + obj_signingResponse.getResultMinor());
			}
			System.out.println("Reason : " + obj_signingResponse.getErrorMessage());
		}
		System.out.println("\n/**********************************************************************/");
	}

}
