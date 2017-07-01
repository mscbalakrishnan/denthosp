package com.intellion.denthosp.dao;

import com.intellion.denthosp.model.Appointment;
import com.intellion.denthosp.model.Patient;
import com.intellion.denthosp.model.User;

public interface UserDAO {
 
	User getUserByCredentials(String userId, String password);
	//Appointment addApppointment(Appointment appointment);
	//Appointment addPatient(Patient patient);
}
