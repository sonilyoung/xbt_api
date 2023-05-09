package egovframework.com.stu.main.dao;

import java.util.List;

import egovframework.com.adm.userMgr.vo.UserInfo;
import egovframework.com.stu.main.vo.Schedule;
import egovframework.com.stu.main.vo.UserStInfo;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("MainDAO")
public class MainDAO extends EgovAbstractMapper{
	
	private static final String Namespace = "egovframework.com.stu.main.dao.MainDAO";
	
	
	public UserStInfo selectBaselineUserInfo(UserStInfo params) {
		return selectOne(Namespace + ".selectBaselineUserInfo", params);
	}				
	
	public List<?> selectScheduleList(Schedule params) {
		return (List<?>)selectList(Namespace + ".selectScheduleList", params);
	}			
}
