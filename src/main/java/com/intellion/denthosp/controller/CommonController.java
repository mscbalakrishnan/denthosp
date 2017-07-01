package com.intellion.denthosp.controller;

import java.util.List;

import org.hibernate.Session;

import com.intellion.denthosp.dao.CommonDAO;
import com.intellion.denthosp.dao.CommonDAOImpl;
import com.intellion.denthosp.model.Appointment;
import com.intellion.denthosp.model.Patient;

import atg.taglib.json.util.JSONObject;

public class CommonController {
	
	public List getAppointments(JSONObject jsonObj)throws Exception{
		CommonDAO commonDAO = CommonDAOImpl.getService();		
		return commonDAO.getAppointments(jsonObj);
	}
	
	public Patient savePatient(Patient pat, Long pk, Session session)throws Exception{
		CommonDAO commonDAO = CommonDAOImpl.getService();
		return commonDAO.savePatient(pat, pk, session);
	}
	
	
	public Appointment saveAppointment(Appointment app,Long pk, Session session)throws Exception{
		CommonDAO commonDAO = CommonDAOImpl.getService();
		return commonDAO.saveAppointment(app, pk, session);
	}

}
