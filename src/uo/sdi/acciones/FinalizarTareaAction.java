package uo.sdi.acciones;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import alb.util.log.Log;

public class FinalizarTareaAction implements Accion {
	
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado="EXITO";
		
		String id = (String) request.getParameter("Id");
		Long IdTrue = Long.valueOf(id);
		try {
			TaskService taskService = Services.getTaskService();
		

			synchronized(request.getServletContext())  {
				taskService.markTaskAsFinished(IdTrue);
			
			}
		
			Log.debug("Se finalizado la tarea numero [%d]", 
					IdTrue);
			(new ListarTareasInboxAction()).execute(request, response);
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