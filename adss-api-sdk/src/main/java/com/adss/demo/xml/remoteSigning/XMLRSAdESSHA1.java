package com.adss.demo.xml.remoteSigning;

import com.adss.demo.xml.constant.ErrorDesc;
import com.adss.demo.xml.constant.MessageCode;
import com.adss.demo.xml.dto.CertificateUtil;
import com.adss.demo.xml.dto.ServiceResponse;
import com.adss.demo.xml.dto.SignRSXmlReq;
import com.ascertia.adss.client.api.signing.SigningRequest;
import com.ascertia.adss.client.api.signing.SigningResponse;
import com.ascertia.adss.client.api.signing.StatusRequest;
import com.ascertia.adss.client.api.signing.StatusResponse;

import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

@Service
public class XMLRSAdESSHA1 {
	public static ServiceResponse<String> signRSAdESSHA1(SignRSXmlReq req) throws Exception {
        InputStream inputFilePath = new ByteArrayInputStream(req.getFile());
        
        if(req.getUrl() == null || req.getProfileId() == null || req.getCertificateAlias() == null || req.getClientId() == null
				|| req.getSignatureMimetype() == null || req.getHashAlgorithm() == null|| req.getSignatureMode() == null
				|| req.getRequestMode() == null|| req.getUserId() == null|| req.getUserPassword() == null|| req.getDataDisplayed()== null) {
		      inputFilePath = new ByteArrayInputStream(req.getFile());
		      System.out.println(inputFilePath);
		      return new ServiceResponse<String>(MessageCode.FAILED, "Lack of information" , null);  
        }
        X509Certificate cert = null;
            try {
            	cert = (X509Certificate) CertificateUtil.getCertificate(req.getUserSigningCert());
            } catch (Exception e) {
            	System.out.println("Failed Cert");
            }  
        // Constructing request for XML signing
        SigningRequest obj_signingRequest = new SigningRequest(req.getClientId(), inputFilePath, req.getSignatureMimetype());

        // Compute Hash locally
        obj_signingRequest.setLocalHash(true);
        obj_signingRequest.setLocalDigestAlgorithm(req.getHashAlgorithm());
        //obj_signingRequest.setSignatureHash(true);
        obj_signingRequest.setSignatureMode(req.getSignatureMode());
        obj_signingRequest.setSignerCertificate(cert);
        obj_signingRequest.setSignerCertificateChain(new X509Certificate[]{cert});
        obj_signingRequest.setCertificateAlias(req.getCertificateAlias());

        obj_signingRequest.setProfileId(req.getProfileId());
        obj_signingRequest.setRequestMode(getRequestMode(req.getRequestMode()));

        if (req.getAuthorisedSigning().equalsIgnoreCase("TRUE")) {
            obj_signingRequest.setUserID(req.getUserId());
            obj_signingRequest.setCertificatePassword(req.getUserPassword());
        }
        obj_signingRequest.setDataToBeDisplayed(req.getDataDisplayed());
        

        System.out.println("\n/**********************************************************************/");
        System.out.println("\nA request has been sent to sign the XML. Waiting for response...");

        // Sending the above constructed request to the ADSS server hdsi
        SigningResponse obj_signingResponse = (SigningResponse) obj_signingRequest.send(req.getUrl());

        // Parsing the response
        if (obj_signingResponse.isSuccessful()) {
            String str_transactionID = obj_signingResponse.getTransactionID();
            System.out.println("Request status: " + obj_signingResponse.getStatus());
            if (str_transactionID != null && !str_transactionID.isEmpty()) {
                System.out.println("Requesting transaction ID: " + str_transactionID);
                StatusRequest statusRequest = new StatusRequest(req.getClientId(), str_transactionID);
                
                String data = null;
                int i = 0;
                while (true) {
                    StatusResponse statusResponse = (StatusResponse) statusRequest.send(req.getUrl());
                    if (statusResponse.getStatus().equalsIgnoreCase("SIGNED") || statusResponse.getStatus().equalsIgnoreCase("SUCCESS")) {
                    	obj_signingResponse.embedSignatures(statusResponse.getSignedDocuments());

                    	data = Base64.getEncoder().encodeToString((byte[]) obj_signingResponse.getDocuments().get(0));
                    	System.out.println("\n XML document has been signed successfully.");
                        return new ServiceResponse<String>(MessageCode.SUCCESS, ErrorDesc.SUCCESS, data); 
                    } 
                    
                    else {
                        System.out.println("Transaction ID: " + str_transactionID);
                        System.out.println("Request status: " + statusResponse.getStatus());
                    }
                    TimeUnit.SECONDS.sleep(5);
                    if (i++ == 20) {
                        System.out.println("Application stopping after a number of status attempts: " + i);
                        break;
                    }
                }
            } else {
                obj_signingResponse.embedSignatures(obj_signingResponse.getDocuments());
            }

        } else {
            System.out.println("Failed to sign the XML document");
            System.out.println("Reason : " + obj_signingResponse.getErrorMessage());
            String message= obj_signingResponse.getErrorMessage();
			return new ServiceResponse<String>(MessageCode.FAILED, message, null);
        }

        System.out.println("\n/**********************************************************************/");

		return null;

	}

	private static int getRequestMode(String a_strRequestMode) {
		if ("DSS".equalsIgnoreCase(a_strRequestMode)) {
			return SigningRequest.DSS;
		}
		return SigningRequest.HTTP;
	}
}
