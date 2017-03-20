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
import uo.sdi.business.UserService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.User;
import uo.sdi.dto.types.UserStatus;
import uo.sdi.infrastructure.BundleFactorie;

@ManagedBean(name = "login")
@SessionScoped
public class BeanLogin implements Serializable {
	private static final long serialVersionUID = 6L;
	// For login
	private String name = "";
	private String password = "";

	// for register
	private String email = "";
	private String passwordAgain = "";

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

			if (user.getIsAdmin()) {
				return "exitoAdministrador";
			}

			return "exitoUser";
		}
		// setResult("login_form_result_error");
		// XXX: Esto es otra opcion para generar mensajes que me parece que
		// queda mejor la verdad
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle
						.getString("error"), bundle
						.getString("login_form_result_error")));
		return "fallo";
	}

	public String register() {
		FacesContext cont = FacesContext.getCurrentInstance();
		ResourceBundle bundle = BundleFactorie.getMessagesBundle();
		// TODO: Change to hash password
		User u = new User().setLogin(name).setEmail(email).setIsAdmin(false)
				.setPassword(password).setStatus(UserStatus.ENABLED);
		try {
			Services.getUserService().registerUser(u);
			cont.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, bundle
							.getString("success"), bundle
							.getString("success_register")));
		} catch (BusinessException e) {
			cont.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					bundle.getString("error"), e.getMessage()));
			e.printStackTrace();
			return "fallo"; //Volvemos a register
		}
		return "exito"; //Nos vamos a index
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordAgain() {
		return passwordAgain;
	}

	public void setPasswordAgain(String passwordAgain) {
		this.passwordAgain = passwordAgain;
	}

	public void setResult(String result) {
		this.result = result;
	}
}