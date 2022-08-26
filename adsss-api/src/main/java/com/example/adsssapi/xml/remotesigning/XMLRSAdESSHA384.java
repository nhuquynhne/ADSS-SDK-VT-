package com.example.adsssapi.xml.remotesigning;

import com.ascertia.adss.client.api.signing.SigningRequest;
import com.ascertia.adss.client.api.signing.SigningResponse;
import com.ascertia.adss.client.api.signing.StatusRequest;
import com.ascertia.adss.client.api.signing.StatusResponse;
import com.ascertia.adss.client.api.util.Util;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class XMLRSAdESSHA384 {


    public static void main(String[] args) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        Properties obj_propConfigurations = new Properties();
        obj_propConfigurations.load(new FileInputStream(".\\conf\\AuthorisedSignXmlWithLocalHash.properties"));
        String str_inputFilePath = obj_propConfigurations.getProperty("UNSIGNED_INPUT_FILE_PATH");
        String str_arrInputFilesPath[] = str_inputFilePath.split(",");

        //Create Signig Certificate and Chain
        byte byte_userCert[] = Util.readFile(obj_propConfigurations.getProperty("USER_SIGNING_CERT_PATH"));
        CertificateFactory obj_certFactory = CertificateFactory.getInstance("X.509");
        X509Certificate obj_signingCert = (X509Certificate) obj_certFactory.generateCertificate(new ByteArrayInputStream(byte_userCert));

        // Constructing request for XML signing
        SigningRequest obj_signingRequest = new SigningRequest(obj_propConfigurations.getProperty("CLIENT_ID"), str_arrInputFilesPath[0].trim(), obj_propConfigurations.getProperty("SIGNATURE_MIME_TYPE"));
        for (int i = 1; i < str_arrInputFilesPath.length; i++) {
            obj_signingRequest.addDocument(Util.readFile(str_arrInputFilesPath[i]));
        }
        // Compute Hash locally
        obj_signingRequest.setLocalHash(true);
        obj_signingRequest.setLocalDigestAlgorithm(obj_propConfigurations.getProperty("HASH_ALGORITHM_SHA_384"));
        //obj_signingRequest.setSignatureHash(true);
        obj_signingRequest.setSignatureMode(obj_propConfigurations.getProperty("SIGNATURE_MODE"));
        obj_signingRequest.setSignerCertificate(obj_signingCert);
        obj_signingRequest.setSignerCertificateChain(new X509Certificate[]{obj_signingCert});
        obj_signingRequest.setCertificateAlias(obj_propConfigurations.getProperty("CERTIFICATE_ALIAS"));

        obj_signingRequest.setProfileId(obj_propConfigurations.getProperty("PROFILE_ID"));
        obj_signingRequest.setRequestMode(getRequestMode(obj_propConfigurations.getProperty("REQUEST_MODE")));

        if (obj_propConfigurations.getProperty("AUTHORISED_SIGNING", "FALSE").equalsIgnoreCase("TRUE")) {
            obj_signingRequest.setUserID(obj_propConfigurations.getProperty("USER_ID"));
            obj_signingRequest.setCertificatePassword(obj_propConfigurations.getProperty("USER_PASSWORD"));
        }
        obj_signingRequest.setDataToBeDisplayed(obj_propConfigurations.getProperty("DATA_TO_BE_DISPLAYED"));
        if (obj_propConfigurations.getProperty("CLIENT_AUTH_PFX_PATH") != null) {
            obj_signingRequest.setSslClientCredentials(obj_propConfigurations.getProperty("CLIENT_AUTH_PFX_PATH"), obj_propConfigurations.getProperty("CLIENT_AUTH_PFX_PASSWORD"));
        }
        if (str_arrInputFilesPath.length == 1) {
            obj_signingRequest.setDocumentID(obj_propConfigurations.getProperty("DOCUMENT_ID"));
            obj_signingRequest.setDocumentName(obj_propConfigurations.getProperty("DOCUMENT_NAME"));
        }

        System.out.println("\n/**********************************************************************/");
        System.out.println("\nA request has been sent to sign the XML. Waiting for response...");

        // Sending the above constructed request to the ADSS server hdsi
        SigningResponse obj_signingResponse = (SigningResponse) obj_signingRequest.send(obj_propConfigurations.getProperty("ADSS_SERVER_URL"));

        // Parsing the response
        if (obj_signingResponse.isSuccessful()) {
            String str_transactionID = obj_signingResponse.getTransactionID();
            System.out.println("Request status: " + obj_signingResponse.getStatus());
            if (str_transactionID != null && !str_transactionID.isEmpty()) {
                System.out.println("Requesting transaction ID: " + str_transactionID);
                StatusRequest statusRequest = new StatusRequest(obj_propConfigurations.getProperty("CLIENT_ID"), str_transactionID);
                if (obj_propConfigurations.getProperty("CLIENT_AUTH_PFX_PATH") != null) {
                    statusRequest.setSslClientCredentials(obj_propConfigurations.getProperty("CLIENT_AUTH_PFX_PATH"), obj_propConfigurations.getProperty("CLIENT_AUTH_PFX_PASSWORD"));
                }
                int i = 0;
                while (true) {
                    StatusResponse statusResponse = (StatusResponse) statusRequest.send(obj_propConfigurations.getProperty("ADSS_SERVER_URL"));
                    if (statusResponse.getStatus().equalsIgnoreCase("SIGNED") || statusResponse.getStatus().equalsIgnoreCase("SUCCESS")) {
                        obj_signingResponse.embedSignatures(statusResponse.getSignedDocuments());
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
                obj_signingResponse.embedSignatures(obj_signingResponse.getDocuments());
            }

            System.out.println("\n XML document has been signed successfully.");
            String str_outputFilePath = obj_propConfigurations.getProperty("SIGNED_OUTPUT_FILE_PATH_SHA384");
            String str_arrOutputFilesPath[] = str_outputFilePath.split(",");
            int i_index = 0;
            for (String str_outputFilesPath : str_arrOutputFilesPath) {
                Util.writeToFile((byte[]) obj_signingResponse.getDocuments().get(i_index++), str_outputFilesPath);
            }
        } else {
            System.out.println("Failed to sign the XML document");
            System.out.println("Reason : " + obj_signingResponse.getErrorMessage());
        }

        System.out.println("\n/**********************************************************************/");

    }

    private static int getRequestMode(String a_strRequestMode) {
        if ("DSS".equalsIgnoreCase(a_strRequestMode)) {
            return SigningRequest.DSS;
        }
        return SigningRequest.HTTP;
    }
}
