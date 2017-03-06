package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import alb.util.log.Log;

public class EliminarCategoriaAction implements Accion {
	
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado="EXITO";
	

		String id = request.getParameter("id");
		
	
		
		try {
			TaskService taskService = Services.getTaskService();
			
			
			synchronized(request.getServletContext())  {
				taskService.deleteCategory(Long.parseLong(id));
			}
			
			Log.debug("Modificada la categoria "+id );
			
			(new ListarTareasInboxAction()).execute(request, response);
		
		}
		catch (BusinessException b) {
			Log.debug("Algo ha ocurrido eliminar la categoria "+id+" : %s",
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