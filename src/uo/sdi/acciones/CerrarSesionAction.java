package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import alb.util.log.Log;

public class CerrarSesionAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado="EXITO";
		
		HttpSession session = request.getSession();
		session.invalidate();
		Log.debug("Sesion invalidada, sesion cerrada");
		request.setAttribute("mensajeParaElUsuario", 
				"Sesion cerrada correctamente.");
		return resultado;
	}
	
	@Override
	public String toString() {
		return getClass().getName();
	}
	
}
