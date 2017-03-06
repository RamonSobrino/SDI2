package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.Category;
import uo.sdi.dto.User;
import alb.util.log.Log;

public class CrearCategoriaAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		String resultado="EXITO";

		//Extraer datos de la peticion
		String titulo = request.getParameter("newCategory");
		User user;
		user = (User) request.getSession().getAttribute("user");

		try {
			TaskService taskService = Services.getTaskService();
			Category categoria = new Category();			
			categoria.setUserId(user.getId());
			categoria.setName(titulo);
			
			taskService.createCategory(categoria);
			
			(new ListarTareasInboxAction()).execute(request, response);
			}
			catch (BusinessException b) {
				Log.debug("Algo ha ocurrido actualizando los datos de [%s]: %s", 
						user.getLogin(),b.getMessage());
				request.setAttribute("mensajeParaElUsuario", 
						b.getMessage());
				resultado="FRACASO";
			}
		return resultado;
	}

	@Override
	public String toString() {
		return getClass().getName();
	}
}
