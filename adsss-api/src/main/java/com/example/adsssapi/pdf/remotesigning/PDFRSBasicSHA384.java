package com.example.adsssapi.pdf.remotesigning;

import com.ascertia.adss.client.api.signing.*;

import java.io.FileOutputStream;
import java.util.concurrent.TimeUnit;

public class PDFRSBasicSHA384 {
    private static String m_adssURL = "http://125.212.254.26:8777";
    private static String m_strClientID = "samples_test_client";
    private static String m_strProfileID = "adss:signing:profile:015";
    private static String m_strUserId = "viettel-rs-signing-002";
    private static String m_strPassword = "password";
    private static String m_strInputPath = "./data/signing/test_input_unsigned.pdf";
    private static String m_strOutputPath = "./data/signing/test_output_rs_basic_sha384.pdf";

    
    /**
    *
    * @param args
    * @throws Exception
    */
   public static void main(String[] args) throws Exception {
       m_adssURL = m_adssURL + "/adss/signing/hdsi";
       populateParameters(args);

       // Constructing request for pdf signing
       PdfSigningRequest obj_signingRequest = new PdfSigningRequest(m_strClientID, m_strInputPath);
       obj_signingRequest.setDataToBeDisplayed("This is a sample document"); //Data to be displayed at signing time on mobile
       obj_signingRequest.setProfileId(m_strProfileID); //Signing Profile ID used for PDF signing
       obj_signingRequest.setUserID(m_strUserId); // User ID generated in the ARS Service
       obj_signingRequest.setCertificateAlias(m_strUserId); // Certificate Alias of the User ID
       obj_signingRequest.setCertificatePassword(m_strPassword); // Signing certificate password
       obj_signingRequest.setRequestMode(SigningRequest.HTTP); // Only HTTP mode is supported
		obj_signingRequest.setSigningReason("I approved this document");
		obj_signingRequest.setSigningLocation("Hanoi, Vietnam");
		obj_signingRequest.setContactInfo("https://savis.vn");
		obj_signingRequest.setSignedBy("Nguyễn Hữu Thanh");
		
		
       System.out.println("\n/**********************************************************************/");
       System.out.println("\nUser ID: " + m_strUserId);
       System.out.println("\nSigning Profile ID: " + m_strProfileID);
       System.out.println("\nA request has been sent to sign the PDF. Waiting for response...");
       PdfSigningResponse obj_signingResponse = (PdfSigningResponse) obj_signingRequest.send(m_adssURL);

       // Parsing the response
       if (obj_signingResponse.isSuccessful()) {
           System.out.println("\nRequest is in pending state, use Go>Sign Mobile App to authorise this transaction");//If signing request added in the queue for user authorisation
           if (obj_signingResponse.getStatus().equalsIgnoreCase("PENDING")) {
               int i_counter = 0;
               //Pending signing request status would be checked after a specific period
               //Transaction ID would be used to track the status
               //Loop the status request until the authorisation completed or its expire
               System.out.println("Transaction ID: " + obj_signingResponse.getTransactionID());
               StatusRequest statusRequest = new StatusRequest(m_strClientID, obj_signingResponse.getTransactionID());
               StatusResponse statusResponse;
               while (true) {
                   if (i_counter < 60) {
                       statusResponse = (StatusResponse) statusRequest.send(m_adssURL);
                       if (statusResponse.getStatus() != null && statusResponse.getStatus().equalsIgnoreCase("SUCCESS")) {
                           //Write the signed file on the output path
                           FileOutputStream obj_fos = new FileOutputStream(m_strOutputPath);
                           obj_fos.write(statusResponse.getSignedDocuments().get(0));
                           obj_fos.close();
                           System.out.println("\nDocument Signed");
                           break;
                       } else if (statusResponse.getStatus() != null && statusResponse.getStatus().equalsIgnoreCase("DECLINED")) {
                           System.out.println("\nSigning request status: " + statusResponse.getStatus());
                           System.out.println("\nSigner decline the signing request");
                           break;
                       } else {
                           //Authorisation request possible status
                           //1. EXPIRE
                           //2. DECLINED
                           //3. UNAUTHORISED                      
                           //4. Failed to verify
                           //5. Internal Server Error
                           System.out.println("\nSigning request status: " + statusResponse.getStatus());
                       }
                   } else {
                       System.out.println("\nFailed to sign the document, time elapsed");
                       break;
                   }
                   i_counter++;
                   TimeUnit.SECONDS.sleep(3);
               }
           }
       } else {
           System.out.println("Failed to sign the PDF document");
           System.out.println("Reason: " + obj_signingResponse.getErrorMessage());
       }
       System.out.println("\n/**********************************************************************/");
   }
   
   public static void populateParameters(String args[]) {
       if (args != null && args.length > 0) {
           if (args[0] != null && !args[0].isEmpty()) {
               m_adssURL = args[0] + "/adss/signing/hdsi";
           }
           if (args[1] != null && !args[1].isEmpty()) {
               m_strUserId = args[1];
           }
           if (args[2] != null && !args[2].isEmpty()) {
               m_strPassword = args[2];
           }
           if (args[3] != null && !args[3].isEmpty()) {
               m_strInputPath = args[3];
           }
           if (args[4] != null && !args[4].isEmpty()) {
               m_strOutputPath = args[4];
           }
           if (args[5] != null && !args[5].isEmpty()) {
               m_strProfileID = args[5];
           }
           if (args[6] != null && !args[6].isEmpty()) {
               m_strClientID = args[6];
           }
       }
   }
}
