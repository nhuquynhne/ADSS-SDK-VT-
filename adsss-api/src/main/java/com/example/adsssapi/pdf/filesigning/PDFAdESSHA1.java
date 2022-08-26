package com.example.adsssapi.pdf.filesigning;

import com.ascertia.adss.client.api.signing.PdfSigningRequest;
import com.ascertia.adss.client.api.signing.PdfSigningResponse;
import com.ascertia.adss.client.api.signing.SigningRequest;
import com.example.adsssapi.pdf.controller.MessageCode;
import com.example.adsssapi.pdf.controller.ServiceResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
public class PDFAdESSHA1 {

	public static ServiceResponse<String> PDF( MultipartFile file) throws Exception {
		// TODO Auto-generated method stub
		if (file == null) {
			return new ServiceResponse<String>(MessageCode.FILE_IS_REQUIRED, null);
		}

		// TODO Auto-generated method stub
//		String inputFilePath = "./data/signing/test_input_unsigned.pdf";
		String outputFilePath = "./data/signing/test_output_ades_sha1000.pdf";
		String url = "https://125.212.254.26:8774";
		String profleId = "adss:signing:profile:015";


		InputStream inputFilePath =  new BufferedInputStream(file.getInputStream());

		System.out.println(inputFilePath);

		// Constructing request for pdf signing
		PdfSigningRequest obj_signingRequest = new PdfSigningRequest("samples_test_client", inputFilePath);
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

		return null;
	}

}
