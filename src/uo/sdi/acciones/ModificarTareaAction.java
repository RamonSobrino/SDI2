package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.Task;
import uo.sdi.dto.User;
import alb.util.date.DateUtil;
import alb.util.log.Log;

public class ModificarTareaAction implements Accion {
	
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado="EXITO";
		User user;
		user = (User) request.getSession().getAttribute("user");
		
		//Task tareaAntigua = (Task) request.getAttribute("tarea2");

		String id = request.getParameter("id");
		String title = request.getParameter("title");
		String comment =  request.getParameter("comment").trim();
		String planned =  request.getParameter("planned").trim();
		String categoryId = request.getParameter("categoryId");
		
		Long categoryIdTrue = null;
		if(!categoryId.equals("null")){categoryIdTrue = Long.valueOf(categoryId);}
		Task tarea = new Task();
		try {
			TaskService taskService = Services.getTaskService();
			tarea.setId(Long.valueOf(id));
			tarea.setTitle(title);
			tarea.setUserId(user.getId());
			if(!comment.isEmpty()){tarea.setComments(comment);}
			if(!planned.isEmpty()){tarea.setPlanned(DateUtil.fromString(planned));}
			if(categoryIdTrue!= null){tarea.setCategoryId(categoryIdTrue);}
			
			synchronized(request.getServletContext())  {
				taskService.updateTask(tarea);
			}
			
			Log.debug("Modificada la tarea [%d] ",tarea.getId());
			
			(new ListarTareasInboxAction()).execute(request, response);
		
		}
		catch (BusinessException b) {
			Log.debug("Algo ha ocurrido obteniendo la tarea %d: %s",
					tarea.getId(), b.getMessage());
			resultado="FRACASO";
		}
		return resultado;
	}
	
	@Override
	public String toString() {
		return getClass().getName();
	}
	
}