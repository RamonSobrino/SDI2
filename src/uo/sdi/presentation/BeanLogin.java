package uo.sdi.presentation;

import java.io.Serializable;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import uo.sdi.business.LoginService;
import uo.sdi.business.Services;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.User;
import uo.sdi.infrastructure.BundleFactorie;

@ManagedBean(name = "login")
@SessionScoped
public class BeanLogin implements Serializable {
	private static final long serialVersionUID = 6L;
	private String name = "";
	private String password = "";
	private String result = "login_form_result_valid";

	public BeanLogin() {
		System.out.println("BeanLogin - No existia");
	}

	public String verify() {
		ResourceBundle bundle = BundleFactorie.getMessagesBundle();
		LoginService login = Services.getLoginService();
		User user;
		try {
			user = login.doLogin(name, password);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fallo";
		}
		if (user != null) {
			putUserInSession(user);
			
			if(user.getIsAdmin())
			{
				return "exitoAdministrador";
			}
			
			return "exitoUser";
		}
		// setResult("login_form_result_error");
		//XXX: Esto es otra opcion para generar mensajes que me parece que queda mejor la verdad
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle
						.getString("error"), bundle
						.getString("login_form_result_error")));
		return "fallo";
	}

	private void putUserInSession(User user) {
		Map<String, Object> session = FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap();
		session.put("LOGGEDIN_USER", user);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}