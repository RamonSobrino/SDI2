package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import alb.util.log.Log;
import uo.sdi.business.AdminService;
import uo.sdi.business.Services;
import uo.sdi.business.exception.BusinessException;

public class DeshabilitarUsuarioAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		String resultado = "EXITO";

		AdminService aServ = Services.getAdminService();
		Long userId = Long.parseLong(request.getParameter("id"));

		try {
			aServ.disableUser(userId);
		} catch (BusinessException e) {
			Log.debug(
					"Ha ocurrido un error intentando desactivar el usuario %d: %s",
					userId, e.getMessage());
			resultado = "FRACASO";
		}

		return resultado;

	}

	@Override
	public String toString() {
		return getClass().getName();
	}
	
}
