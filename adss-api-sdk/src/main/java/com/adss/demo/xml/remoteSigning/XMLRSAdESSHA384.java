package com.adss.demo.xml.remoteSigning;

import com.adss.demo.xml.constant.ErrorDesc;
import com.adss.demo.xml.constant.MessageCode;
import com.adss.demo.xml.dto.CertificateUtil;
import com.adss.demo.xml.dto.ServiceResponse;
import com.adss.demo.xml.dto.SignRSXmlReq;
import com.adss.demo.xml.dto.SignXmlReq;
import com.ascertia.adss.client.api.signing.SigningRequest;
import com.ascertia.adss.client.api.signing.SigningResponse;
import com.ascertia.adss.client.api.signing.StatusRequest;
import com.ascertia.adss.client.api.signing.StatusResponse;
import com.ascertia.adss.client.api.util.Util;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class XMLRSAdESSHA384 {


	public static ServiceResponse<String> signRSAdESSHA384(SignRSXmlReq req) throws Exception {
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
//	          String str_cert = req.getUserSigningCert();

//	          CertificateFactory obj_certFactory = CertificateFactory.getInstance("X.509");
	          //String str_cert = officeRequest.getCertificateAlias();
	      //Create Signig Certificate and Chain
	      //byte byte_userCert[] = Util.readFile(obj_propConfigurations.getProperty("USER_SIGNING_CERT_PATH"));
//	      String cert = req.getUserSigningCert();
//	      byte byte_userCert[]= Base64.getDecoder().decode(cert);
//	      String cert = req.getUserSigningCert();
//	      CertificateFactory obj_certFactory = CertificateFactory.getInstance("X.509");
//	      X509Certificate obj_signingCert = (X509Certificate) obj_certFactory.generateCertificate(new ByteArrayInputStream(byte_userCert));

	      // Constructing request for XML signing
	      SigningRequest obj_signingRequest = new SigningRequest(req.getClientId(), inputFilePath, req.getSignatureMimetype());
//	      for (int i = 1; i < str_arrInputFilesPath.length; i++) {
//	          obj_signingRequest.addDocument(Util.readFile(str_arrInputFilesPath[i]));
//	      }
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
	      
//	      
//	      if (obj_propConfigurations.getProperty("CLIENT_AUTH_PFX_PATH") != null) {
//	          obj_signingRequest.setSslClientCredentials(obj_propConfigurations.getProperty("CLIENT_AUTH_PFX_PATH"), obj_propConfigurations.getProperty("CLIENT_AUTH_PFX_PASSWORD"));
//	      }
//	      
	      
//	      if (str_arrInputFilesPath.length == 1) {
//	          obj_signingRequest.setDocumentID(obj_propConfigurations.getProperty("DOCUMENT_ID"));
//	          obj_signingRequest.setDocumentName(obj_propConfigurations.getProperty("DOCUMENT_NAME"));
//	      }

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
	              
	              
//	              if (obj_propConfigurations.getProperty("CLIENT_AUTH_PFX_PATH") != null) {
//	                  statusRequest.setSslClientCredentials(obj_propConfigurations.getProperty("CLIENT_AUTH_PFX_PATH"), obj_propConfigurations.getProperty("CLIENT_AUTH_PFX_PASSWORD"));
//	              }
	              
	              
	              
	              String data = null;
	              int i = 0;
	              while (true) {
	                  StatusResponse statusResponse = (StatusResponse) statusRequest.send(req.getUrl());
	                  if (statusResponse.getStatus().equalsIgnoreCase("SIGNED") || statusResponse.getStatus().equalsIgnoreCase("SUCCESS")) {
	                  	obj_signingResponse.embedSignatures(statusResponse.getSignedDocuments());
//	                  	byte[] baos=statusResponse.getSignedDocuments().get(0);
	                  	data = Base64.getEncoder().encodeToString((byte[]) obj_signingResponse.getDocuments().get(0));
	                  	
//	                  	byte bytes[]= statusResponse.getSignedDocuments();
//	                  	String encode = Base64.getEncoder().encodeToString(bytes);
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
//	          String str_outputFilePath = obj_propConfigurations.getProperty("SIGNED_OUTPUT_FILE_PATH_SHA256");
//	          String str_arrOutputFilesPath[] = str_outputFilePath.split(",");
//	          int i_index = 0;
//	          for (String str_outputFilesPath : str_arrOutputFilesPath) {
//	              Util.writeToFile((byte[]) obj_signingResponse.getDocuments().get(i_index++), str_outputFilesPath);
//	          }
//	          String str_arrOutputFilesPath[] = str_outputFilePath.split(",");
//	          int i_index = 0;
//	          for (String str_outputFilesPath : str_arrOutputFilesPath) {
//	              Util.writeToFile((byte[]) obj_signingResponse.getDocuments().get(i_index++), str_outputFilesPath);
//	          }
				
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
