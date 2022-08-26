package com.example.adsssapi.cms.remotesigning;

import com.ascertia.adss.client.api.signing.SigningRequest;
import com.ascertia.adss.client.api.signing.SigningResponse;
import com.ascertia.adss.client.api.signing.StatusRequest;
import com.ascertia.adss.client.api.signing.StatusResponse;
import com.ascertia.adss.client.api.util.Util;
import com.ascertia.common.api.pdf.ASC_PdfSignatureAppearance;
import com.ascertia.common.api.pdf.ASC_PdfSigner;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.FileInputStream;
import java.security.Security;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class CMSRSAdESSHA384 {
    public static void main(String[] args) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        Properties obj_propConfigurations = new Properties();
        obj_propConfigurations.load(new FileInputStream(".\\conf\\authorised_signing_with_local_hash.properties"));
        byte byte_arrUnsignedDocument[] = Util.readFile(obj_propConfigurations.getProperty("UNSIGNED_INPUT_FILE_PATH"));
        ASC_PdfSigner obj_pdfSigner = buildPdfSigner(obj_propConfigurations, byte_arrUnsignedDocument);
        byte byte_arrDocumentHash[] = obj_pdfSigner.getHash();

        SigningRequest obj_singingRequest = new SigningRequest(obj_propConfigurations.getProperty("CLIENT_ID"), byte_arrDocumentHash, SigningRequest.MIME_TYPE_HASH);
        obj_singingRequest.setCertificateAlias(obj_propConfigurations.getProperty("CERTIFICATE_ALIAS"));
        obj_singingRequest.setProfileId(obj_propConfigurations.getProperty("PROFILE_ID"));
        obj_singingRequest.setRequestMode(getRequestMode(obj_propConfigurations.getProperty("REQUEST_MODE")));

        //Start: RAS/SAM settings
        if ("TRUE".equalsIgnoreCase(obj_propConfigurations.getProperty("AUTHORISED_SIGNING"))) {
            System.out.println("Authorised Signing for user: " + obj_propConfigurations.getProperty("USER_ID"));
            obj_singingRequest.setUserID(obj_propConfigurations.getProperty("USER_ID"));
            obj_singingRequest.setCertificatePassword(obj_propConfigurations.getProperty("USER_PASSWORD"));
            obj_singingRequest.setDocumentID(obj_propConfigurations.getProperty("DOCUMENT_ID"));
            obj_singingRequest.setDocumentName(obj_propConfigurations.getProperty("DOCUMENT_NAME"));
            obj_singingRequest.setDataToBeDisplayed(obj_propConfigurations.getProperty("DATA_TO_BE_DISPLAYED"));
        }
        //End: RAS/SAM settings

        SigningResponse obj_signingResponse = (SigningResponse) obj_singingRequest.send(obj_propConfigurations.getProperty("ADSS_SERVER_URL"));

        if ("TRUE".equalsIgnoreCase(obj_propConfigurations.getProperty("AUTHORISED_SIGNING"))) {
            String str_transactionID = null;
            if (obj_signingResponse.isSuccessful()) {
                str_transactionID = obj_signingResponse.getTransactionID();
            }
            System.out.println("Requesting transaction status: " + str_transactionID);
            StatusRequest statusRequest = new StatusRequest(obj_propConfigurations.getProperty("CLIENT_ID"), str_transactionID);
            int i = 0;
            while (true) {
                StatusResponse statusResponse = (StatusResponse) statusRequest.send(obj_propConfigurations.getProperty("ADSS_SERVER_URL"));
                if (statusResponse.getStatus().equalsIgnoreCase("SIGNED") || statusResponse.getStatus().equalsIgnoreCase("SUCCESS")) {
                    System.out.println("Document signed using Authorised signing via ADSS SAM Server");
                    obj_pdfSigner.embedSignature(statusResponse.getSignedDocuments().get(0));
                    Util.writeToFile(obj_pdfSigner.getSignedDocument(), obj_propConfigurations.getProperty("SIGNED_OUTPUT_FILE_PATH_SHA384"));
                    System.out.println("Document signed successfully");
                    break;
                } else if (statusResponse.getStatus().equalsIgnoreCase("DECLINED")) {
                    System.out.println("Transaction ID: " + str_transactionID);
                    System.out.println("Request status: " + statusResponse.getStatus());
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
            System.out.println("Document signed on the ADSS Signing Server");
            obj_pdfSigner.embedSignature(obj_signingResponse.getDocument());
            Util.writeToFile(obj_pdfSigner.getSignedDocument(), obj_propConfigurations.getProperty("SIGNED_OUTPUT_FILE_PATH_SHA384"));
            System.out.println("Document signed successfully");
        }

    }

    private static ASC_PdfSigner buildPdfSigner(Properties a_propConfigurations, byte a_byteArrContent[]) throws Exception {
        byte a_byteArrAppr[] = Util.readFile(a_propConfigurations.getProperty("SIGNATURE_APPEARANCE"));
        ASC_PdfSignatureAppearance obj_signApp = new ASC_PdfSignatureAppearance(a_byteArrAppr);
        ASC_PdfSigner obj_pdfSigner = new ASC_PdfSigner(a_byteArrContent);
        obj_pdfSigner.setSignatureAppearance(obj_signApp);
        obj_pdfSigner.setSignatureFieldName(a_propConfigurations.getProperty("SIGNATURE_FIELD_NAME"));
        obj_pdfSigner.setSigningArea(2);
        obj_pdfSigner.setSigningPage(1);
        obj_pdfSigner.setContentSize(40960);
        byte a_byteArrCompanyLogo[] = Util.readFile(a_propConfigurations.getProperty("COMPANY_LOGO_FILE_PATH"));
        obj_pdfSigner.setCompanyLogo(Util.encode(a_byteArrCompanyLogo));
        obj_pdfSigner.setContactInfo(a_propConfigurations.getProperty("CONTACT_INFO"));
        a_byteArrCompanyLogo = Util.readFile(a_propConfigurations.getProperty("HAND_SIGNATURE_FILE_PATH"));
        obj_pdfSigner.setHandSignature(Util.encode(a_byteArrCompanyLogo));
        obj_pdfSigner.setHashAlgorithm(a_propConfigurations.getProperty("HASH_ALGORITHM_SHA256"));
        obj_pdfSigner.setSignedBy(a_propConfigurations.getProperty("SIGNED_BY"));
        obj_pdfSigner.setSigningLocation(a_propConfigurations.getProperty("SIGNING_LOCATION"));
        obj_pdfSigner.setSigningReason(a_propConfigurations.getProperty("SIGNING_REASON"));
        obj_pdfSigner.setSignatureMode(a_propConfigurations.getProperty("SIGNATURE_MODE"));
        return obj_pdfSigner;
    }

    private static int getRequestMode(String a_strRequestMode) {
        if ("DSS".equalsIgnoreCase(a_strRequestMode)) {
            return SigningRequest.DSS;
        }
        return SigningRequest.HTTP;
    }

}
