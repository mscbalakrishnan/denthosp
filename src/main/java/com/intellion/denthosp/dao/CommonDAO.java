package com.intellion.denthosp.dao;

import java.util.List;

import org.hibernate.Session;

import com.intellion.denthosp.model.Appointment;
import com.intellion.denthosp.model.Patient;

import atg.taglib.json.util.JSONObject;

public interface CommonDAO {
 
	List getAppointments(JSONObject dataObj);
	
	Patient savePatient(Patient p,Long pk, Session session);
	
	Appointment saveAppointment(Appointment p,Long pk, Session session);
	
}
