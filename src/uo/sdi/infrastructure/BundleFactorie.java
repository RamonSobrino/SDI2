package uo.sdi.infrastructure;

import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

public class BundleFactorie {
	public static ResourceBundle getMessagesBundle(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ResourceBundle bundle = facesContext.getApplication()
				.getResourceBundle(facesContext, "msgs");
		return bundle;
	}
}
