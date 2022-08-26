package com.adss.demo.xml.dto;

import java.io.ByteArrayInputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;

public class CertificateUtil {
	public static X509Certificate getCertificate(String certificate) {
		byte encodedCert[] = Base64.getDecoder().decode(certificate);
		ByteArrayInputStream inputStream = new ByteArrayInputStream(encodedCert);
		CertificateFactory certFactory = null;
		X509Certificate cert = null;
		try {
			certFactory = CertificateFactory.getInstance("X.509");
			cert = (X509Certificate) certFactory.generateCertificate(inputStream);
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cert;
	}
}
