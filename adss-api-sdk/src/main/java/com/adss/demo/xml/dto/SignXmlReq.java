package com.adss.demo.xml.dto;


public class SignXmlReq {
	private byte[] file;
	private String profileId;
	private String url;
	private String clientId;
	private String CertificateAlias;
	
	public SignXmlReq() {
		// TODO Auto-generated constructor stub
	}

	public SignXmlReq(byte[] file, String profileId, String url, String clientId, String certificateAlias) {
		super();
		this.file = file;
		this.profileId = profileId;
		this.url = url;
		this.clientId = clientId;
		CertificateAlias = certificateAlias;
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

	
}
