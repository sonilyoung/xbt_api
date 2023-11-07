package egovframework.com.adm.userMgr.service;

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

/**
 * 사용자관리에 관한 인터페이스클래스를 정의한다.
 * 
 * @author 공통서비스 개발팀 조재영
 * @since 2009.04.10
 * @version 1.0
 * @see
 *
 *      <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.04.10  조재영          최초 생성
 *
 *      </pre>
 */
public interface UserMgrService{
	
	public List<UserInfo> selectUserList(UserInfo params);
	
	public List<UserInfo> selectUserListPop(UserInfo params);
	
	public List<UserInfo> selectDuplicationUserList(UserInfo params);
	
	public List<UserBaseline> selectBaselineUserList(UserBaseline params);
	
	public UserBaseline selectBaselineBasicTotalScore(UserBaseline params);
	
	public UserBaseline selectBaselineEvaluation(UserBaseline params);
	
	public UserBaseline selectBaselineTherory(UserBaseline params);
	
	public UserBaseline selectBaselinePractice(UserBaseline params);
	
	public int updateBaselineUser(UserBaseline params);
	
	public UserInfo selectUserCheck(UserInfo params);
	
	public UserInfo selectUser(UserInfo params);
	
	public int insertUser(UserInfo params);
	
	public int insertDuplicationUser(UserInfo params);
	
	public int updateUser(UserInfo params);
	
	public int deleteUser(UserInfo params);
	
	public int deleteDuplicationUser(UserInfo params);	
	
	
	public List<UserBaseline> getUserBaselineList(UserBaseline params);
	
	public List<UserBaselineSub> getUserBaselineSubList(UserBaselineSub params);
	
	public Object getUserBaselineSubDetail(UserBaselineDetail params);

	public List<UserBaselineSubInfo> getUserBaselineSubDetailList(UserBaselineSubInfo params);
	
	public List<TeacherInfo> selectTeacherList(TeacherInfo params);
	
	public TeacherInfo selectTeacherCheck(TeacherInfo params);
	
	public TeacherInfo selectTeacher(TeacherInfo params);
	
	public int insertTeacher(TeacherInfo params);
	
	public int updateTeacher(TeacherInfo params);
	
	public int deleteTeacher(TeacherInfo params);
	
	public int insertUserMaster(TeacherInfo params);
	
	public int updateUserMaster(TeacherInfo params);
	
	public int deleteUserMaster(TeacherInfo params);	
	
	public List<UserCertificate> selectCertificationUserList(UserCertificate params);
	
	public UserCertificateDetail selectCertificationUser(UserCertificateDetail params);
	
	public UserCertificateDetail selectCertNumber(UserCertificateDetail params);
	
	public int insertCertNumber(CertificationInfo params);
}
