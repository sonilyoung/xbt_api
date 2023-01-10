package egovframework.com.adm.userMgr.dao;

import java.util.List;

import egovframework.com.adm.userMgr.vo.UserBaseline;
import egovframework.com.adm.userMgr.vo.UserBaselineDetail;
import egovframework.com.adm.userMgr.vo.UserBaselineSub;
import egovframework.com.adm.userMgr.vo.UserBaselineSubInfo;
import egovframework.com.adm.userMgr.vo.UserInfo;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("UserMgrDAO")
public class UserMgrDAO extends EgovAbstractMapper{
	
	private static final String Namespace = "egovframework.com.adm.userMgr.dao.UserMgrDAO";
	
	public List<?> getUserList(UserInfo params) {
		return (List<?>)selectList(Namespace + ".getUserList", params);
	}
	
	public List<?> getUserBaselineList(UserBaseline params) {
		return (List<?>)selectList(Namespace + ".getUserBaselineList", params);
	}			
	
	public List<?> getUserBaselineSubList(UserBaselineSub params) {
		return (List<?>)selectList(Namespace + ".getUserBaselineSubList", params);
	}		
	
	public Object getUserBaselineSubDetail(UserBaselineDetail params) {
		return selectOne(Namespace + ".getUserBaselineSubDetail", params);
	}		

	public List<?> getUserBaselineSubDetailList(UserBaselineSubInfo params) {
		return (List<?>)selectList(Namespace + ".getUserBaselineSubDetailList", params);
	}	
}
