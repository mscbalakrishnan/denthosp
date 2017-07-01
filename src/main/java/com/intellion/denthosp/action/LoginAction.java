package com.intellion.denthosp.action;

import javax.servlet.ServletContext;

import org.apache.struts2.util.ServletContextAware;
import org.hibernate.SessionFactory;

import com.intellion.denthosp.dao.UserDAO;
import com.intellion.denthosp.dao.UserDAOImpl;
import com.intellion.denthosp.model.User;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ModelDriven;

public class LoginAction implements Action, ModelDriven<User>, ServletContextAware {

	private User user = new User();

	private ServletContext ctx;

	public void setServletContext(ServletContext sc) {
		this.ctx = sc;

	}

	public User getModel() {
		System.out.println("Start getModel");
		return user;
	}

	public String execute() throws Exception {
		System.out.println("Start");
		SessionFactory sf = (SessionFactory) ctx.getAttribute("SessionFactory");
		UserDAO userDAO = new UserDAOImpl(sf);
		User userDB = userDAO.getUserByCredentials(user.getId(), user.getPwd());
		if (userDB == null)
			return ERROR;
		else {
			user.setEmail(userDB.getEmail());
			user.setName(userDB.getName());
			return SUCCESS;
		}
	}

	
}
