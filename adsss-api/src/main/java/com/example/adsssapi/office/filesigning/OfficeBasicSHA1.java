package com.example.adsssapi.office.filesigning;

import com.ascertia.adss.client.api.signing.OfficeSigningRequest;
import com.ascertia.adss.client.api.signing.OfficeSigningResponse;
import com.ascertia.adss.client.api.signing.SigningRequest;
import com.ascertia.adss.client.api.util.Util;
import com.ascertia.common.api.pdf.util.ASC_Util;
import com.example.adsssapi.office.dto.OfficeRequest;
import com.example.adsssapi.pdf.controller.MessageCode;
import com.example.adsssapi.office.controller.ServiceResponseOffice;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;

public class OfficeBasicSHA1 {
//file main đau?
    public static ServiceResponseOffice<String> Office(OfficeRequest officeRequest) throws Exception {
        // TODO Auto-generated method stub


        String urlOffice = officeRequest.getUrl();
        String profileOfficeId = officeRequest.getProfileId();

        if (officeRequest.getFileInput() == null) {
            return new ServiceResponseOffice<String>(MessageCode.FILE_IS_REQUIRED, null);
        }
        InputStream inputFilePath =  new BufferedInputStream(officeRequest.getFileInput().getInputStream());
        System.out.println(inputFilePath);

        //Create Signig Certificate and Chain
//        String str_cert = "MIID+zCCAuOgAwIBAgIUIjhLQTwhOsGibfnxNfX3km+ID/wwDQYJKoZIhvcNAQELBQAwcDELMAkGA1UEBhMCR0IxGTAXBgNVBAoTEEFzY2VydGlhIExpbWl0ZWQxJzAlBgNVBAsTHkFzY2VydGlhIFNvZnR3YXJlIERpc3RyaWJ1dGlvbjEdMBsGA1UEAxMUQURTUyBTYW1wbGVzIFRlc3QgQ0EwHhcNMjIwODEyMDIxMzA5WhcNMjMwODEyMDIxMzA5WjATMREwDwYDVQQDDAh0ZXN0XzAwMTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAJGVkKug9QYlTloEPLy4EHAJJ/3AfY484xfBOshE2oQEFDODLQ6HO1zuG6hM7N5Np9WOy++RMDHTIav39UAwlOqQvhG9iqzS0KaR3VkNUGKJxSHGNWka/CDg/Ympx0h+13H8aGEjJfZhc+tVCcaH21eGhEcJNM0MRPqhdA44HZZqClNYsyMv4Q78QgUBogx7PCgVKZKq9dUnoQrzr9F92UJAPzuh3jQ4v3jK+BAs3r23m18kv+fanmU6rMpQula38bMn36sgndKavA+NnlESvGGycrRh2r+mlPQN2zJVC/HzfbFoXNXyhDm9rDtLDqg/ScUTKJEBbWk6rdE2WIg5fKkCAwEAAaOB6TCB5jAOBgNVHQ8BAf8EBAMCBsAwDAYDVR0TAQH/BAIwADA7BggrBgEFBQcBAQQvMC0wKwYIKwYBBQUHMAGGH2h0dHA6Ly9sb2NhbGhvc3Q6ODc3Ny9hZHNzL29jc3AwHwYDVR0jBBgwFoAUOvEFwQwH+SWPgHK3leqTUIl98BcwSQYDVR0fBEIwQDA+oDygOoY4aHR0cDovL2xvY2FsaG9zdDo4Nzc3L2Fkc3MvY3Jscy9hZHNzX3NhbXBsZXNfdGVzdF9jYS5jcmwwHQYDVR0OBBYEFGLyUHWNtPe2Ff5jRrdysZtjEDUXMA0GCSqGSIb3DQEBCwUAA4IBAQCE92XGTt2aCUHfKUv5Mej7SJ8KRS0K2I+xSgkshjgN+yu4xHT7nHn6MZyte9PPBbBAII3ptiDMAgAnucyY/mfRfQcTXJ/DzFoJaqw8g8kx7G5otgElKrg6U1s3Ab8cjoPZ7wxEwHrisImoOFj6bQqCErvnmVi+QJxW74bfLxNtIzLOgqWwOByn3W4mUbGPvf+HIEyN6Yuyixt2kPX746V2/7lRp8J2DJnEVOQJxwU+J4JNAjjTYixjL8FOmHpQMRGzVdqaQdyoiZjRheaDa2Er5l72Y3QtclryHMkP033LhjNMDCVrqGUlzdW489Ab7GOLbpYjEew+RezbmRQTxFS1";

        String str_cert = officeRequest.getCertificateAlias();

        CertificateFactory obj_certFactory = CertificateFactory.getInstance("X.509");
        X509Certificate obj_signingCert = (X509Certificate) obj_certFactory.generateCertificate(new ByteArrayInputStream(ASC_Util.decode(str_cert)));

        //Signing Certificate Chain
        X509Certificate[] obj_signingCertChain = new X509Certificate[1];
        obj_signingCertChain[0] = obj_signingCert;

        // Constructing request for word document signing
        OfficeSigningRequest obj_officeSigningRequest = new OfficeSigningRequest(officeRequest.getSamples_test_client(), inputFilePath, OfficeSigningRequest.MIME_TYPE_EXCEL);
        obj_officeSigningRequest.setProfileId(profileOfficeId);
        obj_officeSigningRequest.setSetupId(officeRequest.getSetupId());

        obj_officeSigningRequest.setHandSignature(officeRequest.getReadFile());

//        obj_officeSigningRequest.setLocalHash(true);
        obj_officeSigningRequest.setDigestAlgorithm(OfficeSigningRequest.SHA1);
        obj_officeSigningRequest.setSignerCertificate(obj_signingCert);
        obj_officeSigningRequest.setSignerCertificateChain(new X509Certificate[]{obj_signingCert});
        obj_officeSigningRequest.setRequestMode(SigningRequest.DSS);
        // Writing request to disk
//        obj_officeSigningRequest.writeTo("../data/signing/SignMsOfficeDocumentWithLocalHash-request.xml");

        System.out.println("\n/**********************************************************************/");
        System.out.println("\nA request has been sent to sign the office document. Waiting for response...");

        OfficeSigningResponse obj_officeSigningResponse = (OfficeSigningResponse) obj_officeSigningRequest.send(officeRequest.getUrl().trim() + "/adss/signing/dss");
        // Writing response to disk
//        obj_officeSigningResponse.writeTo("../data/signing/SignMsOfficeDocumentWithLocalHash-response.xml");


        // Parsing the response
        if (obj_officeSigningResponse.isSuccessful()) {
            System.out.println("\nOffice Document has been signed successfully.");
            System.out.println("Result Major : " + obj_officeSigningResponse.getResultMajor());
            if (obj_officeSigningResponse.getResultMinor() != null && !obj_officeSigningResponse.getResultMinor().equals("")) {
                System.out.println("Result Minor : " + obj_officeSigningResponse.getResultMinor());
            }
//            obj_officeSigningResponse.publishDocument(outputFilePath.trim());
            String res = null;
            try {
                res = Base64.getEncoder().encodeToString(obj_officeSigningResponse.getDocument());
                System.out.println(res);
                if (res != null) {
                    System.out.println(res);
                    return new ServiceResponseOffice<String>(MessageCode.SUCCESS, res);
                }
                return new ServiceResponseOffice<String>(MessageCode.ERROR,null);
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
//            obj_officeSigningResponse.publishDocument(outputFilePath.trim());


        } else {
            System.out.println("Failed to sign the office document");
            System.out.println("Result Major : " + obj_officeSigningResponse.getResultMajor());
            if (obj_officeSigningResponse.getResultMinor() != null && !obj_officeSigningResponse.getResultMinor().equals("")) {
                System.out.println("Result Minor : " + obj_officeSigningResponse.getResultMinor());
            }
            System.out.println("Reason : " + obj_officeSigningResponse.getErrorMessage());
        }
        System.out.println("\n/**********************************************************************/");

        return null;
    }
    public static String encodeFileToBase64Binary(String filePath) {
        try {
            File file = new File(filePath);
            return Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(file));
        } catch (Exception e) {
            return null;
        }
    }
    public static String decodedFileToBase64Binary(String fileDecode) {
        try {
            File file = new File(fileDecode);
            return Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(file));
        } catch (Exception e) {
            return null;
        }
    }
}
