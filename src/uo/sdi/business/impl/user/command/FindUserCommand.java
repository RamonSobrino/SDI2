package uo.sdi.business.impl.user.command;

import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.Command;
import uo.sdi.dto.User;
import uo.sdi.dto.types.UserStatus;
import uo.sdi.persistence.Persistence;

public class FindUserCommand<T> implements Command<User> {

	private Long id;

	public FindUserCommand(Long id) {
		this.id = id;
	}

	@Override
	public User execute() throws BusinessException {
		User user = Persistence.getUserDao().findById(id);

		return (user != null && user.getStatus().equals(UserStatus.ENABLED)) ? user
				: null;
	}

}
