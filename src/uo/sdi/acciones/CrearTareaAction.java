package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.Task;
import uo.sdi.dto.User;
import alb.util.log.Log;

public class CrearTareaAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		String resultado="EXITO";

		//Extraer datos de la peticion
		String categoria = request.getParameter("categoria");
		String titulo = request.getParameter("newTarea");
		User user;
		user = (User) request.getSession().getAttribute("user");

		try {
			TaskService taskService = Services.getTaskService();
			Task task = new Task();
			if(categoria!=null)
			{
				Long category_id = Long.valueOf(categoria);
				task.setCategoryId(category_id);
			}
			task.setUserId(user.getId());
			
			task.setTitle(titulo);
			
			taskService.createTask(task);
			
			(new ListarTareasInboxAction()).execute(request, response);
			}
			catch (BusinessException b) {
				Log.debug("Algo ha ocurrido actualizando los datos de [%s]: %s", 
						user.getLogin(),b.getMessage());
				resultado="FRACASO";
				request.setAttribute("mensajeParaElUsuario", 
						b.getMessage());
			}
		return resultado;
	}

	@Override
	public String toString() {
		return getClass().getName();
	}
}
