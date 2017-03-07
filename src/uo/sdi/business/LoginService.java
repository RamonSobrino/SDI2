package uo.sdi.business;

import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.User;

public interface LoginService {
	public User doLogin(String login, String password) throws BusinessException;
}
