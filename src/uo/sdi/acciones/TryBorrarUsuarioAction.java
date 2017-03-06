package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import alb.util.log.Log;
import uo.sdi.business.AdminService;
import uo.sdi.business.Services;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.User;

public class TryBorrarUsuarioAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		String resultado = "EXITO";

		AdminService aServ = Services.getAdminService();
		Long userId = Long.parseLong(request.getParameter("id"));
		try {
			User u = aServ.findUserById(userId);
			request.setAttribute("userToDelete", u);
		} catch (BusinessException e) {
			Log.debug(
					"Ha ocurrido un error intentando encontrar el usuario %d: %s",
					userId, e.getMessage());
			resultado = "FRACASO";
			request.setAttribute("mensajeParaElUsuario", "Ha ocurrido un error: " +e.getMessage());
		}
		
		return resultado;
	}

}
