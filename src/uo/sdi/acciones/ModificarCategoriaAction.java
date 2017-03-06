package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.Category;
import uo.sdi.dto.User;
import alb.util.log.Log;

public class ModificarCategoriaAction implements Accion {
	 
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado="EXITO";
		User user;
		user = (User) request.getSession().getAttribute("user");
		
		//Task tareaAntigua = (Task) request.getAttribute("tarea2");

		String id = request.getParameter("id");
		
		String name = request.getParameter("name");
	
		if(name.isEmpty()){ 
			Log.debug("Error la categoria tiene que tener nombre");
			return "FRACASO";}
		Category categoria = new Category();
		try {
			TaskService taskService = Services.getTaskService();
			categoria.setId(Long.parseLong(id));
			categoria.setName(name);
			categoria.setUserId(user.getId());
			
			synchronized(request.getServletContext())  {
				taskService.updateCategory(categoria);
			}
			
			Log.debug("Modificada la categoria [%d] ",categoria.getId());
			
			(new ListarTareasInboxAction()).execute(request, response);
		
		}
		catch (BusinessException b) {
			Log.debug("Algo ha ocurrido modificando la categoria %d: %s",
					categoria.getId(), b.getMessage());
			resultado="FRACASO";
		}
		return resultado;
	}
	
	@Override
	public String toString() {
		return getClass().getName();
	}
	
}