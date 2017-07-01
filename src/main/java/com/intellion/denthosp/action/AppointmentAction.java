package com.intellion.denthosp.action;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.intellion.denthosp.Util.SqlConstants;
import com.intellion.denthosp.controller.CommonController;
import com.intellion.denthosp.model.Appointment;
import com.intellion.denthosp.model.Patient;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import atg.taglib.json.util.JSONException;
import atg.taglib.json.util.JSONObject;

public class AppointmentAction extends ActionSupport{
	
	ActionContext ac = ActionContext.getContext();
	HttpServletRequest request=(HttpServletRequest)ac.get(ServletActionContext.HTTP_REQUEST);
	HttpServletResponse response=(HttpServletResponse)ac.get(ServletActionContext.HTTP_RESPONSE);
	private List returnList;
	
	/*@Override
	public String execute() throws Exception {
		System.out.println("AppointmentAction execute method");
			System.out.println("--"+request.getParameter("patientName"));
			System.out.println("--"+request.getParameter("patientEmail"));
			return SUCCESS;
	}*/
	
	private static Patient createPatientObj(HttpServletRequest req)throws Exception{
		Patient p = new Patient();
		try {
			p.setPatientName(req.getParameter("patientName"));
			p.setPatientEmail(req.getParameter("patientEmail"));
			p.setPatientTelephone(req.getParameter("patientPhone"));
			//p.setDeletedFlag(0);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return p;
	}
	
	private static Appointment createAppointmentObj(HttpServletRequest req)throws Exception{
		Appointment app = new Appointment();
		try {
			String dateString = req.getParameter("appointDate");
			
			//String sDate1="31/12/1998";  
			java.util.Date date1=new SimpleDateFormat("MM/dd/yyyy").parse(dateString);  
			System.out.println(dateString+"\t"+date1);
			    
			Date d = new Date(date1.getYear(), date1.getMonth(), date1.getDate());
			app.setAppointmentDate(d);
			app.setAppointTime(req.getParameter("appointTime"));
			//app.setDeletedFlag(0);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return app;
	}
	
	public String saveAppointment()throws Exception{
	
		System.out.println("saveAppointment  method");
		System.out.println("1--"+request.getParameter("patientName"));
		System.out.println("2--"+request.getParameter("patientEmail"));
		System.out.println("3--"+request.getParameter("patientPhone"));
		System.out.println("4--"+request.getParameter("appointDate"));
		System.out.println("5--"+request.getParameter("appointTime"));
		System.out.println("6--"+request.getParameter("allmonths"));
		System.out.println("7--"+request.getParameter("alternative"));
		System.out.println("8--"+request.getParameter("daily"));
		
		String patientName = request.getParameter("patientName");
		String patientEmail = request.getParameter("patientEmail");
		String patientPhone = request.getParameter("patientPhone");
		String appDate = request.getParameter("appointDate");
		String appTime = request.getParameter("appointTime");
		String allMonths = request.getParameter("allmonths");
		String alternativeWeeks = request.getParameter("alternative");
		String daily = request.getParameter("daily");
		
		if(patientName!=null && patientPhone!=null && appDate!=null && appTime!=null 
				&& !patientName.trim().isEmpty() && !patientPhone.trim().isEmpty() && !appDate.trim().isEmpty() && !appTime.trim().isEmpty()){
			Patient p = AppointmentAction.createPatientObj(request);
			Long patientPk = 0L;
			CommonController commonController = new CommonController();
			p = commonController.savePatient(p, patientPk, null);
			
			System.out.println("Patient Id "+p.getPatientId());
			Long fkPatient = p.getPatientId();
			Appointment app = AppointmentAction.createAppointmentObj(request);
			app.setFkPatient(fkPatient);
			Long appointmentPk = 0L;
			
			commonController.saveAppointment(app, appointmentPk, null);
		}
		return SUCCESS;
		
	}

	private JSONObject createAlternativeWeeks(){
		JSONObject jsonObject = new JSONObject();
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return jsonObject;
	}
	
	public String loadSchedule(){
		
		System.out.println("Load Schedule Start");
		JSONObject jsonObj = new JSONObject();
		try {
			jsonObj.put("type", "SQL");
			jsonObj.put("query", SqlConstants.APPOINTMENT_SQL);
			
			CommonController commonController = new CommonController();
			try {
				returnList = commonController.getAppointments(jsonObj);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}


	public List getReturnList() {
		return returnList;
	}


	public void setReturnList(List returnList) {
		this.returnList = returnList;
	}
	
	/*@Override
	public Appointment getModel() {
		System.out.println("Start getModel");
		return appointment;
	}
	
	private Appointment appointment = new Appointment();
	
	private ServletContext ctx;

	@Override
	public void setServletContext(ServletContext sc) {
		this.ctx = sc;
	}
	*/
	
	
	
	
}
