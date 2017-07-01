package com.intellion.denthosp.model;

public class Patient extends BaseDomain{

	private Long patientId;
	private String patientName;
	private String patientEmail;
	private String patientTelephone;
	
	
	public Long getPatientId() {
		return patientId;
	}
	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getPatientEmail() {
		return patientEmail;
	}
	public void setPatientEmail(String patientEmail) {
		this.patientEmail = patientEmail;
	}
	public String getPatientTelephone() {
		return patientTelephone;
	}
	public void setPatientTelephone(String patientTelephone) {
		this.patientTelephone = patientTelephone;
	}
	

}
