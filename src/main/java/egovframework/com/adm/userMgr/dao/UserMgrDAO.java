package egovframework.com.adm.userMgr.dao;

import java.util.List;

import egovframework.com.adm.userMgr.vo.CertificationInfo;
import egovframework.com.adm.userMgr.vo.TeacherInfo;
import egovframework.com.adm.userMgr.vo.UserBaseline;
import egovframework.com.adm.userMgr.vo.UserBaselineDetail;
import egovframework.com.adm.userMgr.vo.UserBaselineSub;
import egovframework.com.adm.userMgr.vo.UserBaselineSubInfo;
import egovframework.com.adm.userMgr.vo.UserCertificate;
import egovframework.com.adm.userMgr.vo.UserCertificateDetail;
import egovframework.com.adm.userMgr.vo.UserInfo;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("UserMgrDAO")
public class UserMgrDAO extends EgovAbstractMapper{
	
	private static final String Namespace = "egovframework.com.adm.userMgr.dao.UserMgrDAO";
	
	public List<?> selectUserList(UserInfo params) {
		return (List<?>)selectList(Namespace + ".selectUserList", params);
	}
	
	public List<?> selectUserListPop(UserInfo params) {
		return (List<?>)selectList(Namespace + ".selectUserListPop", params);
	}
	
	public List<?> selectDuplicationUserList(UserInfo params) {
		return (List<?>)selectList(Namespace + ".selectDuplicationUserList", params);
	}		
		
	public List<?> selectBaselineUserList(UserBaseline params) {
		return (List<?>)selectList(Namespace + ".selectBaselineUserList", params);
	}	
	
	public UserBaseline selectBaselineBasicTotalScore(UserBaseline params) {
		return selectOne(Namespace + ".selectBaselineBasicTotalScore", params);
	}		
	
	public UserBaseline selectBaselineEvaluation(UserBaseline params) {
		return selectOne(Namespace + ".selectBaselineEvaluation", params);
	}		
	
	public UserBaseline selectBaselineTherory(UserBaseline params) {
		return selectOne(Namespace + ".selectBaselineTherory", params);
	}	
	
	public UserBaseline selectBaselinePractice(UserBaseline params) {
		return selectOne(Namespace + ".selectBaselinePractice", params);
	}		
	
	public int updateBaselineUser(UserBaseline params) {
		return update(Namespace + ".updateBaselineUser", params);
	}		
	
	public UserInfo selectUserCheck(UserInfo params) {
		return selectOne(Namespace + ".selectUserCheck", params);
	}		
	
	public UserInfo selectUser(UserInfo params) {
		return selectOne(Namespace + ".selectUser", params);
	}
	
	public int insertUser(UserInfo params) {
		return insert(Namespace + ".insertUser", params);
	}
	
	
	public int insertDuplicationUser(UserInfo params) {
		return insert(Namespace + ".insertDuplicationUser", params);
	}	
	
	public int updateUser(UserInfo params) {
		return update(Namespace + ".updateUser", params);
	}	
	
	public int deleteUser(UserInfo params) {
		return delete(Namespace + ".deleteUser", params);
	}	
	
	public int deleteDuplicationUser(UserInfo params) {
		return delete(Namespace + ".deleteDuplicationUser", params);
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
	
	
	
	public List<?> selectTeacherList(TeacherInfo params) {
		return (List<?>)selectList(Namespace + ".selectTeacherList", params);
	}
	
	public TeacherInfo selectTeacherCheck(TeacherInfo params) {
		return selectOne(Namespace + ".selectTeacherCheck", params);
	}		
	
	public TeacherInfo selectTeacher(TeacherInfo params) {
		return selectOne(Namespace + ".selectTeacher", params);
	}	
	
	public int insertTeacher(TeacherInfo params) {
		return insert(Namespace + ".insertTeacher", params);
	}
	
	public int updateTeacher(TeacherInfo params) {
		return update(Namespace + ".updateTeacher", params);
	}	
	
	public int deleteTeacher(TeacherInfo params) {
		return delete(Namespace + ".deleteTeacher", params);
	}		
	
	public int insertUserMaster(TeacherInfo params) {
		return insert(Namespace + ".insertUserMaster", params);
	}
	
	public int updateUserMaster(TeacherInfo params) {
		return update(Namespace + ".updateUserMaster", params);
	}	
	
	public int deleteUserMaster(TeacherInfo params) {
		return delete(Namespace + ".deleteUserMaster", params);
	}		
	
	public List<?> selectCertificationUserList(UserCertificate params) {
		return (List<?>)selectList(Namespace + ".selectCertificationUserList", params);
	}	
	
	public UserCertificateDetail selectCertificationUser(UserCertificateDetail params) {
		return selectOne(Namespace + ".selectCertificationUser", params);
	}		
	
	public UserCertificateDetail selectCertNumber(UserCertificateDetail params) {
		return selectOne(Namespace + ".selectCertNumber", params);
	}		
	
	public int insertCertNumber(CertificationInfo params) {
		return insert(Namespace + ".insertCertNumber", params);
	}
		
}
