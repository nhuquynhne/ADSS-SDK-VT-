package com.adss.demo.xml.dto;

public class SignRSXmlReq {
	private byte[] file;
	private String profileId;
	private String url;
	private String clientId;
	private String CertificateAlias;
	private String userSigningCert;
	private String signatureMimetype;
	private String hashAlgorithm;
	private String signatureMode;
	private String requestMode;
	private String authorisedSigning;
	private String userId;
	private String userPassword;
	private String dataDisplayed;
	
	public SignRSXmlReq() {
		// TODO Auto-generated constructor stub
	}

	

	public SignRSXmlReq(byte[] file, String profileId, String url, String clientId, String certificateAlias,
			String userSigningCert, String signatureMimetype, String hashAlgorithm, String signatureMode,
			String requestMode, String authorisedSigning, String userId, String userPassword, String dataDisplayed) {
		super();
		this.file = file;
		this.profileId = profileId;
		this.url = url;
		this.clientId = clientId;
		CertificateAlias = certificateAlias;
		this.userSigningCert = userSigningCert;
		this.signatureMimetype = signatureMimetype;
		this.hashAlgorithm = hashAlgorithm;
		this.signatureMode = signatureMode;
		this.requestMode = requestMode;
		this.authorisedSigning = authorisedSigning;
		this.userId = userId;
		this.userPassword = userPassword;
		this.dataDisplayed = dataDisplayed;
	}



	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getCertificateAlias() {
		return CertificateAlias;
	}

	public void setCertificateAlias(String certificateAlias) {
		CertificateAlias = certificateAlias;
	}

	public String getUserSigningCert() {
		return userSigningCert;
	}

	public void setUserSigningCert(String userSigningCert) {
		this.userSigningCert = userSigningCert;
	}

	public String getSignatureMimetype() {
		return signatureMimetype;
	}

	public void setSignatureMimetype(String signatureMimetype) {
		this.signatureMimetype = signatureMimetype;
	}

	public String getHashAlgorithm() {
		return hashAlgorithm;
	}

	public void setHashAlgorithm(String hashAlgorithm) {
		this.hashAlgorithm = hashAlgorithm;
	}

	public String getSignatureMode() {
		return signatureMode;
	}

	public void setSignatureMode(String signatureMode) {
		this.signatureMode = signatureMode;
	}

	public String getRequestMode() {
		return requestMode;
	}

	public void setRequestMode(String requestMode) {
		this.requestMode = requestMode;
	}

	public String getAuthorisedSigning() {
		return authorisedSigning;
	}

	public void setAuthorisedSigning(String authorisedSigning) {
		this.authorisedSigning = authorisedSigning;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getDataDisplayed() {
		return dataDisplayed;
	}

	public void setDataDisplayed(String dataDisplayed) {
		this.dataDisplayed = dataDisplayed;
	}
}
