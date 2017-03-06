package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.business.Services;
import uo.sdi.business.UserService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.User;
import alb.util.log.Log;

public class CrearUsuarioAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		String resultado="EXITO";

		//Extraer datos de la peticion
		String usuario=request.getParameter("usuario");
		String email=request.getParameter("email");
		String newPass = request.getParameter("newPass");
		String newPassAgain = request.getParameter("newPassAgain");
		User user= new User();

		try {
			UserService userService = Services.getUserService();

			HttpSession session=request.getSession();

			user.setLogin(usuario);

			user.setEmail(email);

			if(!newPass.equals(newPassAgain))
			{
				throw new BusinessException("Las contraseñas no coinciden.");
			}
			user.setAndHashPassword(newPass);

			synchronized(request.getServletContext())  {
				userService.registerUser(user);
			}
			request.setAttribute("mensajeParaElUsuario", "Cuenta " + usuario + " creada con exito");
			session.invalidate();
		}
		catch (BusinessException b) {
			Log.debug("Algo ha ocurrido creando usuarios los datos de [%s]: %s", 
					user.getLogin(),b.getMessage());
			request.setAttribute("mensajeParaElUsuario", b.getMessage());
			resultado="FRACASO";
		}
		return resultado;
	}

	@Override
	public String toString() {
		return getClass().getName();
	}
}
