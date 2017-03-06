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

public class ObtenerTareaAction implements Accion {
	
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado="EXITO";
		
		User user;
		user = (User) request.getSession().getAttribute("user");
		String id = (String) request.getParameter("Id");
		Long IdTrue = Long.valueOf(id);
		try {
			TaskService taskService = Services.getTaskService();
			Task task;
			List<Category> categorias;
			
			synchronized(request.getServletContext())  {
				task = taskService.findTaskById(IdTrue);
				categorias = taskService.findCategoriesByUserId(user.getId());
			
			}
			
			
			
			request.setAttribute("tarea", task);
			request.setAttribute("categorias", categorias);
			
			Log.debug("Obtenida tarea numero [%d]", 
					task.getId());
			
					}
		catch (BusinessException b) {
			Log.debug("Algo ha ocurrido obteniendo la tarea de tasks de hoy: %s",
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