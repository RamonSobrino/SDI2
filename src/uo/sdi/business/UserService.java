package uo.sdi.business;

import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.User;

public interface UserService {

	public Long registerUser(User user) throws BusinessException;
	public void updateUserDetails(User user) throws BusinessException;
	public User findLoggableUser(String login, String password) throws BusinessException;
	public User findUser(Long id) throws BusinessException;

	public Long addUser(User user) throws BusinessException;
}
