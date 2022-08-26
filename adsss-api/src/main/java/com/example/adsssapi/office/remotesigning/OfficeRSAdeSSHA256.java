package com.example.adsssapi.office.remotesigning;

import com.ascertia.adss.client.api.signing.*;
import com.ascertia.adss.client.api.util.Util;
import com.ascertia.common.api.pdf.util.ASC_Util;
import com.example.adsssapi.office.controller.ServiceResponseOfficeRSA;
import com.example.adsssapi.office.dto.OfficeRSARequest;
import com.example.adsssapi.pdf.controller.MessageCode;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class OfficeRSAdeSSHA256 {

    public static ServiceResponseOfficeRSA<String> OfficeRSA(OfficeRSARequest officeRSARequest) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        Properties obj_propConfigurations = new Properties();
        obj_propConfigurations.load(new FileInputStream("./conf/AuthorisedSignMsOfficeDocumentWithLocalHash.properties"));
        byte[] inputFilePath = officeRSARequest.getInputFilePath().getBytes();

        //Create Signig Certificate and Chain
        String str_cert = officeRSARequest.getCerPath();
        CertificateFactory obj_certFactory = CertificateFactory.getInstance("X.509");
        X509Certificate obj_signingCert = (X509Certificate) obj_certFactory.generateCertificate(new ByteArrayInputStream(ASC_Util.decode(str_cert)));

        // Constructing request for word document signing
        OfficeSigningRequest obj_officeSigningRequest = new OfficeSigningRequest(officeRSARequest.getClientId(),inputFilePath,OfficeSigningRequest.MIME_TYPE_WORD);
//        for (int i = 1; i < inputFilePath.length; i++) {
        obj_officeSigningRequest.addDocument(inputFilePath);
//        }
        obj_officeSigningRequest.setCertificateAlias(officeRSARequest.getCertificateAlias());
        obj_officeSigningRequest.setProfileId(officeRSARequest.getProfileId());
        obj_officeSigningRequest.setSetupId(officeRSARequest.getSigSetupId());
        obj_officeSigningRequest.setHandSignature(officeRSARequest.getFilePath());
        obj_officeSigningRequest.setDigestAlgorithm(officeRSARequest.getHashAlgorithm());
        obj_officeSigningRequest.setLocalHash(true);
        obj_officeSigningRequest.setSignerCertificate(obj_signingCert);
        obj_officeSigningRequest.setSignerCertificateChain(new X509Certificate[]{obj_signingCert});
        obj_officeSigningRequest.setRequestMode(getRequestMode((obj_propConfigurations.getProperty("REQUEST_MODE"))));

        if (obj_propConfigurations.getProperty("AUTHORISED_SIGNING", "FALSE").equalsIgnoreCase("TRUE")) {
            obj_officeSigningRequest.setUserID(officeRSARequest.getUserId());
            obj_officeSigningRequest.setCertificatePassword(officeRSARequest.getUserPassword());
        }
        obj_officeSigningRequest.setDataToBeDisplayed(obj_propConfigurations.getProperty("DATA_TO_BE_DISPLAYED"));
        if (obj_propConfigurations.getProperty("CLIENT_AUTH_PFX_PATH") != null) {
            obj_officeSigningRequest.setSslClientCredentials(obj_propConfigurations.getProperty("CLIENT_AUTH_PFX_PATH"), obj_propConfigurations.getProperty("CLIENT_AUTH_PFX_PASSWORD"));
        }
        if (inputFilePath.length == 1) {
            obj_officeSigningRequest.setDocumentID(officeRSARequest.getDocumentId());
            obj_officeSigningRequest.setDocumentName(obj_propConfigurations.getProperty("DOCUMENT_NAME"));
        }

        System.out.println("\n/**********************************************************************/");
        System.out.println("\nA request has been sent to sign the office document. Waiting for response...");
        System.out.println("\n/**********************************************************************/");

        OfficeSigningResponse obj_officeSigningResponse = (OfficeSigningResponse) obj_officeSigningRequest.send(officeRSARequest.getAdssServerUrl());

        // Parsing the response
        String data = null;
        if (obj_officeSigningResponse.isSuccessful()) {
            String str_transactionID = obj_officeSigningResponse.getTransactionID();
            System.out.println("Request status: " + obj_officeSigningResponse.getStatus());
            if (str_transactionID != null && !str_transactionID.isEmpty()) {
                System.out.println("Requesting transaction ID: " + str_transactionID);
                StatusRequest statusRequest = new StatusRequest(officeRSARequest.getClientId(), str_transactionID);
                if (obj_propConfigurations.getProperty("CLIENT_AUTH_PFX_PATH") != null) {
                    statusRequest.setSslClientCredentials(obj_propConfigurations.getProperty("CLIENT_AUTH_PFX_PATH"), obj_propConfigurations.getProperty("CLIENT_AUTH_PFX_PASSWORD"));
                }

                int i = 0;
                while (true) {
                    StatusResponse statusResponse = (StatusResponse) statusRequest.send(officeRSARequest.getAdssServerUrl());
                    if (statusResponse.getStatus().equalsIgnoreCase("SIGNED") || statusResponse.getStatus().equalsIgnoreCase("SUCCESS")) {
                        obj_officeSigningResponse.embedSignatures(statusResponse.getSignedDocuments());
//                        obj_officeSigningResponse.publishDocument(outputFilePath.trim());
                        break;
                    } else {
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
                obj_officeSigningResponse.embedSignatures(obj_officeSigningResponse.getDocuments());
            }
            System.out.println("\nOffice Document has been signed successfully.");
//            String str_outputFilePath = obj_propConfigurations.getProperty("SIGNED_OUTPUT_FILE_PATH_SHA1");
//            String str_arrOutputFilesPath[] = str_outputFilePath.split(",");
//            int i_index = 0;
//            for (String str_outputFilesPath : str_arrOutputFilesPath) {
//                Util.writeToFile((byte[]) obj_officeSigningResponse.getDocuments().get(i_index++), str_outputFilesPath);
//
//            }
            data = Base64.getEncoder().encodeToString((byte[]) obj_officeSigningResponse.getDocuments().get(0));
        } else {
            System.out.println("Failed to sign the office document");
            System.out.println("Reason : " + obj_officeSigningResponse.getErrorMessage());
        }
        System.out.println("\n/**********************************************************************/");
        ServiceResponseOfficeRSA<String> response = new ServiceResponseOfficeRSA<>();
        try {
            response.setData(data);
            System.out.println(data);
            if (data == null) {
                response.setCode(MessageCode.ERROR);
//                response.setMessage(ErrorDesc.FAILED);
            } else {
                response.setCode(MessageCode.SUCCESS);
//                response.setMessage(ErrorDesc.SUCCESS);
            }
            return response;

        } catch (Exception e) {
//                   TODO: handle exception
            e.printStackTrace();
            return new ServiceResponseOfficeRSA<String>(MessageCode.ERROR,null);
        }
    }

    private static int getRequestMode(String a_strRequestMode) {
        if ("DSS".equalsIgnoreCase(a_strRequestMode)) {
            return SigningRequest.DSS;
        }
        return SigningRequest.HTTP;
    }
}
