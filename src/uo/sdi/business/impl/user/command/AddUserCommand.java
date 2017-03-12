package uo.sdi.business.impl.user.command;

import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.Command;
import uo.sdi.business.impl.util.UserCheck;
import uo.sdi.dto.User;
import uo.sdi.persistence.Persistence;

public class AddUserCommand implements Command<Long> {

	private User user;

	public AddUserCommand(User user) {
		this.user = user;
	}

	@Override
	public Long execute() throws BusinessException {
		//UserCheck.isNotAdmin( user );
		//UserCheck.notRepeatedLogin( user );
		//UserCheck.minLoginLength( user );
		UserCheck.isValidEmailSyntax( user );
		
		return Persistence.getUserDao().save( user );
	}

}
