package uo.sdi.acciones;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.Category;
import uo.sdi.dto.Task;
import uo.sdi.dto.User;
import alb.util.log.Log;

public class ListarTareasInboxAction implements Accion {
	
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado="EXITO";
		
		List<Task> listaTasks;
		User user;
		user = (User) request.getSession().getAttribute("user");
		try {
			TaskService taskService = Services.getTaskService();
			synchronized(request.getServletContext())  {
				listaTasks = taskService.findInboxTasksByUserId(user.getId());
			}
			request.setAttribute("listaTasks", listaTasks);
			request.setAttribute("titulo", "Tareas del inbox");
			request.setAttribute("categoria", null);
			Log.debug("Obtenida lista de tasks de hoy conteniendo [%d] tasks", 
					listaTasks.size());
			
			List<Category> listaCategory;
			synchronized(request.getServletContext())  {

				listaCategory = taskService.findCategoriesByUserId(user.getId());
			} 
			request.setAttribute("titulo", "Tareas sin terminar");
			request.setAttribute("categoria", null);
			request.setAttribute("listaCategory", listaCategory);
			Log.debug("Obtenida lista de categorias del usuario son "
					+ " [%d] categorias", 
					listaCategory.size());
		}
		catch (BusinessException b) {
			Log.debug("Algo ha ocurrido obteniendo lista de tasks de hoy: %s",
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