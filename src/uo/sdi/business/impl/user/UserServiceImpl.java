package uo.sdi.business.impl.user;

import uo.sdi.business.UserService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.CommandExecutor;
import uo.sdi.business.impl.user.command.AddUserCommand;
import uo.sdi.business.impl.user.command.FindLoggableUSerCommand;
import uo.sdi.business.impl.user.command.FindUserCommand;
import uo.sdi.business.impl.user.command.RegisterUserCommand;
import uo.sdi.business.impl.user.command.UpdateUserDetailsCommand;
import uo.sdi.dto.User;

public class UserServiceImpl implements UserService {

	@Override
	public Long registerUser(User user) throws BusinessException {
		return new CommandExecutor<Long>().execute( 
				new RegisterUserCommand( user ) 
		);
	}

	@Override
	public void updateUserDetails(User user) throws BusinessException {
		new CommandExecutor<Void>().execute( 
				new UpdateUserDetailsCommand( user ) 
		);
	}

	@Override
	public User findLoggableUser(final String login, final String password) 
			throws BusinessException {
		
		return new CommandExecutor<User>().execute( 
				new FindLoggableUSerCommand<User>(login, password) 
		);
	}

	@Override
	public User findUser(Long id) throws BusinessException {
		return new CommandExecutor<User>().execute(new FindUserCommand<User>(id));
	}

	@Override
	public Long addUser(User user) throws BusinessException {
		return new CommandExecutor<Long>().execute( 
				new AddUserCommand( user ) 
		);
	}

	
	
	

}
