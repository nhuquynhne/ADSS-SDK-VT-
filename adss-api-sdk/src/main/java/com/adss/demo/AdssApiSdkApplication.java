package com.adss.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ascertia.adss.client.api.ra.RegistrationRequest;
import com.ascertia.adss.client.api.ra.RegistrationResponse;

@SpringBootApplication
public class AdssApiSdkApplication {

	private static String m_adssURL = "http://10.0.21.99:8777";
    private static String m_strClientID = "samples_test_client";
    private static String m_strProfileID = "minhdd_01";
    private static String m_strUserId = "minhdd_01";
    private static String m_strUserEmail = m_strUserId + "@ascertia.com";
    private static String m_strUserMobile = "00923324012015";
    private static String m_strPassword = "password";
    
    public static void main(String[] args) {


        SpringApplication.run(AdssApiSdkApplication.class, args);



        m_adssURL = m_adssURL + "/adss/ra/cri";
        populateParameters(args);
        System.out.println("\n/**********************************************************************/");
        //Step 1: Register user
        System.out.println("Step 1 ---------------------------");
        System.out.println("User Registration");
//        if (registerUser()) {
//            System.out.println("User registered successfully");
             //Step 2: Create user signing certificate
//            System.out.println("Step 2 ---------------------------");
//            System.out.println("Create user's signing certificate");
//            if (createCertificate()) {
//                System.out.println("Signing certificate generated successfully");
//            } else {
//                System.out.println("Failed to generate certificate");
//
//            }
//        }
        System.out.println("\n/**********************************************************************/");


    }
    
    public static boolean registerUser() {
        try {
            System.out.println("User ID: " + m_strUserId);
            System.out.println("RA Profile ID: " + m_strProfileID);
            //RegistrationRequest constructor with mandatory parameters (Client ID, Request Type, User ID and User Mobile)
            RegistrationRequest userRegistrationRequest = new RegistrationRequest(m_strClientID, RegistrationRequest.REQUEST_TYPE_REGISTER_USER, m_strUserId, m_strUserMobile);
            userRegistrationRequest.setRequestId("request-001"); //Business applicatoin request ID
            userRegistrationRequest.setUserName(m_strUserId);
            userRegistrationRequest.setEmailAddress(m_strUserEmail);
            userRegistrationRequest.setProfileId(m_strProfileID); //RA profile used to register the user in ARS Service
            userRegistrationRequest.setPkcs12Password(m_strPassword); //User Password which is then used authenticate it
            userRegistrationRequest.setEmailAddress(m_strUserEmail); //
            System.out.println("Sending request at: " + m_adssURL);
            RegistrationResponse userRegistrationResponse = (RegistrationResponse) userRegistrationRequest.send(m_adssURL);
            if (userRegistrationResponse.isSuccessful()) {
                return true;
            } else {
                System.out.println("Faild to register user");
                System.out.println("Error Message: " + userRegistrationResponse.getErrorMessage());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    public static boolean createCertificate() {
        try {
            System.out.println("Generating signing certificate alias: " + m_strUserId);
            RegistrationRequest createSigningCertRequest = new RegistrationRequest(m_strClientID, RegistrationRequest.REQUEST_TYPE_CREATE_CERTIFICATE, m_strUserId);
            createSigningCertRequest.setRequestId("request-003"); //Business applicatoin request ID
            createSigningCertRequest.setUserID(m_strUserId); //User ID used to identify the user for key pair generation
            createSigningCertRequest.setUserName(m_strUserId); //User name used to identify the user for key pair generation
            createSigningCertRequest.setEmailAddress(m_strUserEmail); //User Email Address
            createSigningCertRequest.setSubjectDN("CN=" + m_strUserId); //Subject DN to be used in the Signing Certificate
            createSigningCertRequest.setPkcs12Password(m_strPassword); //User Password used to access the signing key
            createSigningCertRequest.setProfileId(m_strProfileID); // RA Profile used to generate certificate for registered user
            System.out.println("Sending request at: " + m_adssURL);
            RegistrationResponse createSigningCertResponse = (RegistrationResponse) createSigningCertRequest.send(m_adssURL);
            if (createSigningCertResponse.isSuccessful()) {
                return true;
            } else {
                System.out.println("Failed to generate certificate");
                System.out.println("Error Message: " + createSigningCertResponse.getErrorMessage());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static void populateParameters(String args[]) {

        if (args != null && args.length > 0) {
            if (args[0] != null && !args[0].isEmpty()) {
                m_adssURL = args[0] + "/adss/ra/cri";
            }
            if (args[1] != null && !args[1].isEmpty()) {
                m_strUserId = args[1];
            }
            if (args[2] != null && !args[2].isEmpty()) {
                m_strUserEmail = args[2];
            }
            if (args[3] != null && !args[3].isEmpty()) {
                m_strUserMobile = args[3];
            }
            if (args[4] != null && !args[4].isEmpty()) {
                m_strPassword = args[4];
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
