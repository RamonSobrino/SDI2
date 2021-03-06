package uo.sdi.business.impl.login.command;

import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.Command;
import uo.sdi.dto.User;
import uo.sdi.dto.types.UserStatus;
import uo.sdi.persistence.Persistence;

public class LoginCommand implements Command<User> {
	private String login;
	private String password;

	public LoginCommand(String login, String password) {
		this.login = login;
		this.password = password;
	}

	@Override
	public User execute() throws BusinessException {
		// FIXME: Uso contraseñas no encriptadas para facilitar pruebas, cambiar
		User user = Persistence.getUserDao().findByLoginAndPassword(login,
				password);
		return (user != null && user.getStatus().equals(UserStatus.ENABLED)) ? user
				: null;

		/*
		 * User user = Persistence.getUserDao().findByLogin(login);
		 * 
		 * return (user != null && user.getStatus().equals(UserStatus.ENABLED)
		 * && user .checkPassword(password)) ? user : null;
		 */
	}

}
