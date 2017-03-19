package uo.sdi.presentation;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.dto.Category;
import uo.sdi.dto.Task;
import uo.sdi.dto.User;

@ManagedBean(name = "task")
@SessionScoped
public class BeanTask implements Serializable {
	private static final long serialVersionUID = 6L;
	private Long id;
	private String tittle;
	private String comments;

	private Date planned;
	private Date finished;
	
	private Category category;
	
	private Task task;
	@ManagedProperty(value = "#{controller}")
	private BeanUsers bean;
	
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}

	private List<Category> categorias;
	
	 @PostConstruct
	    public void init() {
		 this.listaCategory();
		bean= (BeanUsers) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(new String("controller"));
	 }
	public BeanTask() {
		System.out.println("BeanLogin - No existia");
	}

	public String add() {
		TaskService service;
		try {
			// Acceso a la implementacion de la capa de negocio
			// a trav��s de la factor��a
			service = Services.getTaskService();
			// De esta forma le damos informaci��n a toArray para poder hacer el
			// casting a User[]
			FacesContext context = javax.faces.context.FacesContext
					.getCurrentInstance();
			HttpSession session = (HttpSession) context.getExternalContext()
					.getSession(false);
			User user = (User) session.getAttribute("LOGGEDIN_USER");

			this.task = new Task();
			task.setTitle(tittle);
			task.setComments(comments);
			if(category!=null)
			task.setCategoryId(category.getId());
			task.setPlanned(planned);
			task.setFinished(finished);
			task.setUserId(user.getId());
			
			
			service.createTask(task);
			
			bean.listadoTareas();
			return "exito"; // Nos vamos a la vista listado.xhtml

		} catch (Exception e) {
			e.printStackTrace();
			return "error"; // Nos vamos la vista de error
		}
		
	}


	public String edit() {
		TaskService service;
		try {
			// Acceso a la implementacion de la capa de negocio
			// a trav��s de la factor��a
			FacesContext context = javax.faces.context.FacesContext
					.getCurrentInstance();
			HttpSession session = (HttpSession) context.getExternalContext()
					.getSession(false);
			User user = (User) session.getAttribute("LOGGEDIN_USER");

			service = Services.getTaskService();
			this.task = new Task();
			task.setTitle(tittle);
			task.setComments(comments);
			if(category!=null)
			task.setCategoryId(category.getId());
			task.setPlanned(planned);
			task.setFinished(finished);
			task.setUserId(user.getId());		
			task.setId(id);
			service.updateTask(task);
			
			bean.listadoTareas();
			
			this.resetCampos();
			return "exito"; // Nos vamos a la vista listado.xhtml

		} catch (Exception e) {
			e.printStackTrace();
			return "error"; // Nos vamos la vista de error
		}
		
	}
	private void resetCampos()
	{
		this.id=null;
		this.tittle="";
		this.comments="";
		this.planned=null;
		this.finished=null;
		this.category=null;
	}
	public String listaCategory()
	{
		TaskService service;
		try {
			// Acceso a la implementacion de la capa de negocio
			// a trav��s de la factor��a
			service = Services.getTaskService();
			// De esta forma le damos informaci��n a toArray para poder hacer el
			// casting a User[]
			FacesContext context = javax.faces.context.FacesContext
					.getCurrentInstance();
			HttpSession session = (HttpSession) context.getExternalContext()
					.getSession(false);
			User user = (User) session.getAttribute("LOGGEDIN_USER");

			this.categorias = 	service.findCategoriesByUserId(user.getId());			

			return "exito"; // Nos vamos a la vista listado.xhtml

		} catch (Exception e) {
			e.printStackTrace();
			return "error"; // Nos vamos la vista de error
		}
	}


	public String getTittle() {
		return tittle;
	}

	public void setTittle(String tittle) {
		this.tittle = tittle;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Date getPlanned() {
		return planned;
	}

	public void setPlanned(Date planned) {
		this.planned = planned;
	}

	public Date getFinished() {
		return finished;
	}

	public void setFinished(Date finished) {
		this.finished = finished;
	}

	public List<Category> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Category> categorias) {
		this.categorias = categorias;
	}
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}
	
	public String establecer(Task vtask)
	{
		try {
		this.task= vtask;
		this.tittle=this.task.getTitle();
		for(Category cat : this.getCategorias())
		{
			if(cat.getId()==this.task.getCategoryId())
				this.category =cat;
		}
		id= task.getId();
		planned = task.getPlanned();
		finished = task.getFinished();
		return "exito";
		}catch (Exception e) {
			e.printStackTrace();
			return "error"; // Nos vamos la vista de error
		}
		
	}
	
}