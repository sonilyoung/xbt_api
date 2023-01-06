package egovframework.com.adm.userMgr.dao;

import java.util.List;

import egovframework.com.adm.userMgr.vo.UserInfo;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("UserMgrDAO")
public class UserMgrDAO extends EgovAbstractMapper{
	
	private static final String Namespace = "egovframework.com.adm.userMgr.dao.UserMgrDAO";
	
	public List<?> getUserList(UserInfo params) {
		return (List<?>)selectList(Namespace + ".getUserList", params);
	}
	
}
