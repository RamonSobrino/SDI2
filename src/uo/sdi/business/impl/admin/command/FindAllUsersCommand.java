package uo.sdi.business.impl.admin.command;

import java.util.List;

import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.Command;
import uo.sdi.dto.User;
import uo.sdi.persistence.Persistence;
import uo.sdi.persistence.UserDao;

public class FindAllUsersCommand implements Command<List<User>> {

	@Override
	public List<User> execute() throws BusinessException {
		UserDao uDao = Persistence.getUserDao();
		return uDao.findAll();
	}

}
