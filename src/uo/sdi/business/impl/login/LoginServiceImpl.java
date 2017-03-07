package uo.sdi.business.impl.login;

import uo.sdi.business.LoginService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.CommandExecutor;
import uo.sdi.business.impl.login.command.LoginCommand;
import uo.sdi.dto.User;

public class LoginServiceImpl implements LoginService {

	@Override
	public User doLogin(String login, String password) throws BusinessException {
		return new CommandExecutor<User>().execute(new LoginCommand(login, password)); 
	}

}
