package com.intellion.denthosp.model;

import java.sql.Date;

public class Appointment extends BaseDomain{

	private Long appointId;
	private Long fkPatient;
	private Date appointmentDate;
	private String appointTime;
	
	
	public Long getAppointId() {
		return appointId;
	}
	public void setAppointId(Long appointId) {
		this.appointId = appointId;
	}
	public Long getFkPatient() {
		return fkPatient;
	}
	public void setFkPatient(Long fkPatient) {
		this.fkPatient = fkPatient;
	}
	public Date getAppointmentDate() {
		return appointmentDate;
	}
	public void setAppointmentDate(Date appointmentDate) {
		this.appointmentDate = appointmentDate;
	}
	public String getAppointTime() {
		return appointTime;
	}
	public void setAppointTime(String appointTime) {
		this.appointTime = appointTime;
	}

	

}
