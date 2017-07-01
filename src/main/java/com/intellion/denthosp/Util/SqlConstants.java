package com.intellion.denthosp.Util;

public interface SqlConstants {
	
	public String APPOINTMENT_SQL = "select Appointment_Id,FK_Patient,(select patient_name from patient where patient_id=fk_patient) as PatientName, "+
									" Appointment_Date,Appointment_Time from Appointment"; 
	

}
