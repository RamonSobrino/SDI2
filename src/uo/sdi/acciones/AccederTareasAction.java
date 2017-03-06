package uo.sdi.acciones;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.Task;
import uo.sdi.dto.User;
import alb.util.log.Log;

public class AccederTareasAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		String resultado="EXITO";
		
		List<uo.sdi.dto.Category> listaCategory;
		List<Task> listaTask;
		User user;

		user = (User) request.getSession().getAttribute("user");			

		try {
			TaskService taskService = Services.getTaskService();
			synchronized(request.getServletContext())  {
				listaCategory = taskService.findCategoriesByUserId(user.getId());
			}
			request.setAttribute("titulo", "Tareas sin terminar");
			request.setAttribute("categoria", null);
			request.setAttribute("listaCategory", listaCategory);
			Log.debug("Obtenida lista de categorias del usuario son "
					+ " [%d] categorias", 
					listaCategory.size());

			//Se guardan las tareas
			synchronized(request.getServletContext())  {
				listaTask = taskService.findUnfinishedTasksByUserId(user.getId());
			}
			request.setAttribute("listaTasks", listaTask);
			Log.debug("Obtenida lista de tasks del usuario  no terminadas son "
					+ " [%d] tasks", 
					listaTask.size());

		}
		catch (BusinessException b) {
			Log.debug("Algo ha ocurrido obteniendo lista de categorias"
					+ "o de tareas de %s: %s",
					user,b.getMessage());
			resultado="FRACASO";
		}
		return resultado;
	}

	@Override
	public String toString() {
		return getClass().getName();
	}

}