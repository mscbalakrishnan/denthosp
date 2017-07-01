package com.intellion.denthosp.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.hql.internal.ast.QuerySyntaxException;

import com.intellion.denthosp.Util.HibernateUtil;
import com.intellion.denthosp.model.Appointment;
import com.intellion.denthosp.model.Patient;

import atg.taglib.json.util.JSONArray;
import atg.taglib.json.util.JSONObject;

public class CommonDAOImpl implements CommonDAO {
	public static CommonDAO commonService; 
	SessionFactory sf;
	
	/*public CommonDAOImpl(SessionFactory sf){
		this.sf = sf; 
	}

	
	@Override
	public User getUserByCredentials(String userId, String password) {
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("from User where id=:id and pwd=:pwd");
		query.setString("id", userId); query.setString("pwd", password);
		User user = (User) query.uniqueResult();
		if(user != null){
			System.out.println("User Retrieved from DB::"+user);
		}
		tx.commit();session.close();
		return user;
	}*/
	
	
	
	
	public static CommonDAO getService() throws Exception{
		try{
			if(commonService == null){
				commonService =(CommonDAO)new CommonDAOImpl(); 
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return commonService;
	}
	
	public List getAppointments(JSONObject dataObj){
		JSONObject jsonObject = new JSONObject();
		List returnList = new ArrayList();
		try {
			
			if(dataObj.has("type") && dataObj.has("query") && dataObj.getString("type")=="SQL"){
				
				String qry = dataObj.getString("query"); 
				
				returnList = CommonDAOImpl.getDataFromSQLQuery(qry, null);
				
			}
			if(dataObj.has("type") && dataObj.getString("type")=="HSQL"){
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return returnList;
	}
	
	
	
	public static JSONObject executeSQLQueries(JSONObject jsonObject)throws Exception{
		Session	session = null;
		System.out.println(" Save method in velos helper *******");
			
				try{
					//session = HibernateUtil.getSessionFactory().getCurrentSession();
					session = HibernateUtil.getSessionFactory().openSession();
					
					
					// queries 
					String query;
					if(jsonObject.has("queries") ){
						JSONArray queryArray = jsonObject.getJSONArray("queries");
						if(queryArray!=null && queryArray.length()>0 ){
							for(int i=0;i<queryArray.length();i++){
								query = queryArray.getString(i);
								session.beginTransaction();
								System.out.println(" query in helper " + query);
								Query sqlQuery = session.createSQLQuery(query);
								 int count=sqlQuery.executeUpdate();
								 session.getTransaction().commit();	
								 System.out.println(" query execute count " + count);
							}	
						}
					}
					//session.getTransaction().commit();	
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					 if(session !=null && session.isOpen()){
						 session.close();
					 }
				}
				
				return jsonObject;
	}
	
	public static JSONObject executeHQLQueries(JSONObject jsonObject)throws Exception{
		Session	session = null;
		System.out.println(" Save method in velos helper *******");
			
				try{
					session = HibernateUtil.getSessionFactory().getCurrentSession();
					session.beginTransaction();
					
					// queries 
					String query;
					if(jsonObject.has("queries") ){
						JSONArray queryArray = jsonObject.getJSONArray("queries");
						if(queryArray!=null && queryArray.length()>0 ){
							for(int i=0;i<queryArray.length();i++){
								query = queryArray.getString(i);
								CommonDAOImpl.executeQueryString(query, session);
							}	
						}
					}
					session.getTransaction().commit();	
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					 if(session !=null && session.isOpen()){
						 session.close();
					 }
				}
				
				return jsonObject;
	}

	
	public static int executeQueryString(String queryBuffer, Session session)throws Exception {
		int updateflag = 0;
		try{
			//System.out.println(" execute query");
			Query query = session.createQuery(queryBuffer);
			//System.out.println("Query : " + query);
			updateflag = query.executeUpdate();
		}catch(QuerySyntaxException qse){
			//System.out.println(" inside  query exception");
			 try{
				Query query = session.createSQLQuery(queryBuffer);
				//System.out.println("SQL Query : " + query);
				updateflag = query.executeUpdate();
			 }catch(Exception e){
				 e.printStackTrace();
			 }
		}catch(Exception e){
			//System.out.println(" main catch " + e);
			e.printStackTrace();
		}
		return updateflag;
	}
	
	
	public static List getDataFromSQLQuery(String mainQuery,Session session) throws Exception{
		System.out.println("CommonDAOImpl.getDataFromSQLQuery() start");
		List returnList = new ArrayList();
		boolean sessionFlag = false;
		try{
			if(session== null){
				session = HibernateUtil.getSessionFactory().getCurrentSession();
				sessionFlag = true;
				session.beginTransaction();
			}
			System.out.println("mainQuery :: "+mainQuery);
			if(mainQuery!=null && mainQuery.length()>0){
			Query query = session.createSQLQuery(mainQuery);
			returnList = query.list();
			}
			
		}catch(Exception e){
			System.out.println("Exception in CommonDAOImpl.getDataFromSQLQuery() :: "+e);
			e.printStackTrace();
		}finally{
			if(sessionFlag){
				session.close();
			}
		}
		System.out.println("CommonDAOImpl.getDataFromSQLQuery() end");
		return returnList;
	}
	
	
	public static List getDataFromHQLQuery(String mainQuery,Session session) throws Exception{
		System.out.println("CommonDAOImpl.getDataFromHQLQuery() start");
		List returnList = new ArrayList();
		boolean sessionFlag = false;
		try{
			if(session== null){
				session = HibernateUtil.getSessionFactory().getCurrentSession();
				sessionFlag = true;
				session.beginTransaction();
			}
			if(mainQuery!=null && mainQuery.length()>0){
			Query query = session.createQuery(mainQuery);
			returnList = query.list();
			}
			
		}catch(Exception e){
			System.out.println("Exception in CommonDAOImpl.getDataFromHQLQuery() :: "+e);
			e.printStackTrace();
		}finally{
			if(sessionFlag){
				session.close();
			}
		}
		System.out.println("CommonDAOImpl.getDataFromHQLQuery() end");
		return returnList;
	}
	
	
	public static Object saveObject(Object object, Long pkId, Session session)throws Exception{
		System.out.println("CommonDAOImpl saveObject Start");
		try{
			if(pkId != null && pkId != 0){
				//?GGN session.update(object);
				 session.merge(object);
				
			}else{
				object=session.save(object);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("CommonDAOImpl saveObject End");
		return object;
	}
	
	public Patient savePatient(Patient pat, Long pkId, Session session){
		System.out.println("CommonDAOImpl savePatient Start");
		Object object = new Object();
		if(pat!=null){
			object = pat;
		}
		boolean sessionFlag = false;
		try{
				if(session== null){
					session = HibernateUtil.getSessionFactory().getCurrentSession();
					sessionFlag = true;
					session.beginTransaction();
				}
			
			if(pkId != null && pkId != 0){
				//?GGN session.update(object);
				 session.merge(object);
				
			}else{
				object=session.save(object);
			}
			session.getTransaction().commit();	
			if(object!=null){
				
				pat.setPatientId((Long)object);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			if(session.isOpen()){
				session.close();
			}	
		}
		System.out.println("CommonDAOImpl savePatient End");
		return pat;
	}
	
	public Appointment saveAppointment(Appointment appointment, Long pkId, Session session){
		System.out.println("CommonDAOImpl saveAppointment Start");
		Object object = new Object();
		if(appointment!=null){
			object = appointment;
		}
		boolean sessionFlag = false;
		try{
				if(session== null){
					session = HibernateUtil.getSessionFactory().getCurrentSession();
					sessionFlag = true;
					session.beginTransaction();
				}
			
			if(pkId != null && pkId != 0){
				//?GGN session.update(object);
				 session.merge(object);
				
			}else{
				System.out.println("appointment FK"+appointment.getFkPatient());
				object=session.save(object);
			}
			if(object!=null){
				appointment.setAppointId((Long)object);
			}
			
			session.getTransaction().commit();	
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			if(session.isOpen()){
				session.close();
			}	
		}
		System.out.println("CommonDAOImpl saveAppointment End");
		return appointment;
	}
	
}
