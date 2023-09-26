package egovframework.com.adm.login.service;

import egovframework.com.adm.login.vo.User;
import egovframework.com.stu.login.vo.StuLogin;

public interface UserService {

	User getUser(long userId);

	void modifyUser(User parameter);

	void modifyPwd(User parameter);

	Long getUserCount(User parameter);

}
