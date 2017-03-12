package uo.sdi.presentation;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.mail.Session;
import javax.servlet.http.HttpSession;

import uo.sdi.business.AdminService;
import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.UserService;
import uo.sdi.dto.Task;
import uo.sdi.dto.User;

@ManagedBean(name = "controller")
@SessionScoped
public class BeanUsers implements Serializable {
	private static final long serialVersionUID = 55555L;
	// Se añade este atributo de entidad para recibir el User concreto
	// selecionado de la tabla o de un formulario
	// Es necesario inicializarlo para que al entrar desde el formulario de
	// AltaForm.xml se puedan
	// dejar los avalores en un objeto existente.

	// uso de inyección de dependencia
	@ManagedProperty(value = "#{user}")
	private BeanUser user;

	//private User[] users = null;
	private List<User> users =null;
	//private Task[] tasks =null;
	private List<Task> tasks =null;

	/*public Task[] getTasks() {
		return tasks;
	}

	public void setTasks(Task[] tasks) {
		this.tasks = tasks;
	}*/

	public BeanUser getUser() {
		return user;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public void setUser(BeanUser user) {
		this.user = user;
	}

	public List<User> getUsers() {
		return (users);
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	// Se inicia correctamente el MBean inyectado si JSF lo hubiera crea
	// y en caso contrario se crea. (hay que tener en cuenta que es un Bean de
	// sesión)
	// Se usa @PostConstruct, ya que en el contructor no se sabe todavía si el
	// Managed Bean
	// ya estaba construido y en @PostConstruct SI.
	@PostConstruct
	public void init() {
		System.out.println("BeanUsers - PostConstruct");
		// Buscamos el User en la sesión. Esto es un patrón factoría
		// claramente.
		//FIXME:
		//user = Factories.beans.createBeanUser();
	}

	@PreDestroy
	public void end() {
		System.out.println("BeanUsers - PreDestroy");
	}

	public void iniciaUser(ActionEvent event) {
		user.initUser(event);
	}

	public String listado() {
		AdminService service;
		try {
			// Acceso a la implementacion de la capa de negocio
			// a trav��s de la factor��a
			service = Services.getAdminService();
			// De esta forma le damos informaci��n a toArray para poder hacer el
			// casting a User[]
			//users = (User[]) service.findAllUsers().toArray(new User[0]);
			users =  service.findAllUsers();
			return "exito"; // Nos vamos a la vista listado.xhtml

		} catch (Exception e) {
			e.printStackTrace();
			return "error"; // Nos vamos la vista de error
		}

	}
	
	public String listadoTareas() {
		TaskService service;
		try {
			// Acceso a la implementacion de la capa de negocio
			// a trav��s de la factor��a
			service = Services.getTaskService();
			// De esta forma le damos informaci��n a toArray para poder hacer el
			// casting a User[]
			FacesContext context = javax.faces.context.FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
			User user =(User) session.getAttribute("LOGGEDIN_USER");
			
			List<Task> lista = service.findInboxTasksByUserId(user.getId());
			tasks = lista;//(Task[]) lista.toArray(new Task[0]);
			
			return "exito"; // Nos vamos a la vista listado.xhtml

		} catch (Exception e) {
			e.printStackTrace();
			return "error"; // Nos vamos la vista de error
		}

	}

	public String baja(User vuser) {
		AdminService service;
		try {
			// Acceso a la implementacion de la capa de negocio
			// a trav��s de la factor��a
			service = Services.getAdminService();
			// Aliminamos el User seleccionado en la tabla
			service.deepDeleteUser(user.getId());
			// Actualizamos el javabean de Users inyectado en la tabla.
			//users = (User[]) service.findAllUsers().toArray(new User[0]);
			users = service.findAllUsers();
			return "exito"; // Nos vamos a la vista de listado.

		} catch (Exception e) {
			e.printStackTrace();
			return "error"; // Nos vamos a la vista de error
		}

	}

	public String edit() {
		UserService service;
		try {
			// Acceso a la implementacion de la capa de negocio
			// a trav��s de la factor��a
			service = Services.getUserService();
			// Recargamos el User seleccionado en la tabla de la base de datos
			// por si hubiera cambios.
			user = (BeanUser) service.findUser(user.getId());
			return "exito"; // Nos vamos a la vista de Edición.

		} catch (Exception e) {
			e.printStackTrace();
			return "error"; // Nos vamos a la vista de error.
		}

	}

	public String salva() {
		UserService service;
		try {
			// Acceso a la implementacion de la capa de negocio
			// a trav��s de la factor��a
			service = Services.getUserService();
			// Salvamos o actualizamos el User segun sea una operacion de alta
			// o de edici��n
			if (user.getId() == null) {
				service.registerUser(user);
			} else {
				service.updateUserDetails(user);
			}
			return "exito"; // Nos vamos a la vista de detalles usuario

		} catch (Exception e) {
			e.printStackTrace();
			return "error"; // Nos vamos a la vista de error.
		}

	}
	
	public String reiniciar()
	{
		return "exito";
	}
	
	public String cerrar()
	{
		return "exito";
	}
	
	
	public String login()
	{
		return "exito";
	}
	
}
