package egovframework.com.adm.userMgr.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import egovframework.com.adm.userMgr.dao.UserMgrDAO;
import egovframework.com.adm.userMgr.vo.CertificationInfo;
import egovframework.com.adm.userMgr.vo.MenuPin;
import egovframework.com.adm.userMgr.vo.TeacherInfo;
import egovframework.com.adm.userMgr.vo.UserBaseline;
import egovframework.com.adm.userMgr.vo.UserBaselineDetail;
import egovframework.com.adm.userMgr.vo.UserBaselineScore;
import egovframework.com.adm.userMgr.vo.UserBaselineSub;
import egovframework.com.adm.userMgr.vo.UserBaselineSubInfo;
import egovframework.com.adm.userMgr.vo.UserCertificate;
import egovframework.com.adm.userMgr.vo.UserCertificateDetail;
import egovframework.com.adm.userMgr.vo.UserInfo;


/**
 * 사용자관리에 관한 비지니스 클래스를 정의한다.
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
 *   2014.12.08	 이기하			암호화방식 변경(OfficeFileScrty.encryptPassword)
 *
 *      </pre>
 */
@Service("UserMgrService")
public class UserMgrServiceImpl implements UserMgrService {

    @Resource(name = "UserMgrDAO")
	private UserMgrDAO userMgrDAO;


	@Override
	@SuppressWarnings("unchecked")
	public List<UserInfo> selectUserList(UserInfo params) {
		
		return (List<UserInfo>) userMgrDAO.selectUserList(params);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<UserInfo> selectUserListPop(UserInfo params) {
		
		return (List<UserInfo>) userMgrDAO.selectUserListPop(params);
	}	
	
	@Override
	@SuppressWarnings("unchecked")
	public List<UserInfo> selectDuplicationUserList(UserInfo params) {
		
		return (List<UserInfo>) userMgrDAO.selectDuplicationUserList(params);
	}		
	
	@Override
	@SuppressWarnings("unchecked")
	public List<UserBaseline> selectBaselineUserList(UserBaseline params) {
		
		return (List<UserBaseline>) userMgrDAO.selectBaselineUserList(params);
	}
	
	@Override
	public UserBaseline selectBaselineBasicTotalScore(UserBaseline params) {
		
		return userMgrDAO.selectBaselineBasicTotalScore(params);
	}	
	
	@Override
	public UserBaseline selectBaselineEvaluation(UserBaseline params) {
		
		return userMgrDAO.selectBaselineEvaluation(params);
	}	
	
	@Override
	public UserBaseline selectBaselineTherory(UserBaseline params) {
		
		return userMgrDAO.selectBaselineTherory(params);
	}		
	
	@Override
	public UserBaseline selectBaselinePractice(UserBaseline params) {
		
		return userMgrDAO.selectBaselinePractice(params);
	}		
	
	@Override
	public int updateBaselineUser(UserBaseline params) {
		
		return userMgrDAO.updateBaselineUser(params);
	}
	
		
	
	@Override
	public UserInfo selectUserCheck(UserInfo params) {
		
		return (UserInfo) userMgrDAO.selectUserCheck(params);
	}	

	@Override
	public UserInfo selectUser(UserInfo params) {
		
		return (UserInfo) userMgrDAO.selectUser(params);
	}


	@Override
	public int insertUser(UserInfo params) {
		
		return userMgrDAO.insertUser(params);
	}
	
	@Override
	public int insertDuplicationUser(UserInfo params) {
		
		return userMgrDAO.insertDuplicationUser(params);
	}


	@Override
	public int updateUser(UserInfo params) {
		
		return userMgrDAO.updateUser(params);
	}


	@Override
	public int deleteUser(UserInfo params) {
		
		return userMgrDAO.deleteUser(params);
	}	
	
	
	@Override
	public int deleteDuplicationUser(UserInfo params) {
		
		return userMgrDAO.deleteDuplicationUser(params);
	}	

	@Override
	@SuppressWarnings("unchecked")
	public List<UserBaseline> getUserBaselineList(UserBaseline params) {
		
		return (List<UserBaseline>) userMgrDAO.getUserBaselineList(params);
	}


	@Override
	@SuppressWarnings("unchecked")
	public List<UserBaselineSub> getUserBaselineSubList(UserBaselineSub params) {
		
		return (List<UserBaselineSub>) userMgrDAO.getUserBaselineSubList(params);
	}


	@Override
	public Object getUserBaselineSubDetail(UserBaselineDetail params) {
		
		return (UserBaselineDetail) userMgrDAO.getUserBaselineSubDetail(params);
	}


	@Override
	@SuppressWarnings("unchecked")
	public List<UserBaselineSubInfo> getUserBaselineSubDetailList(UserBaselineSubInfo params) {
		
		return (List<UserBaselineSubInfo>) userMgrDAO.getUserBaselineSubDetailList(params);
	}


	@Override
	@SuppressWarnings("unchecked")
	public List<TeacherInfo> selectTeacherList(TeacherInfo params) {
		
		return (List<TeacherInfo>) userMgrDAO.selectTeacherList(params);
	}
	
	@Override
	public TeacherInfo selectTeacherCheck(TeacherInfo params) {
		
		return (TeacherInfo) userMgrDAO.selectTeacherCheck(params);
	}	

	@Override
	public TeacherInfo selectTeacher(TeacherInfo params) {
		
		return (TeacherInfo) userMgrDAO.selectTeacher(params);
	}


	@Override
	public int insertTeacher(TeacherInfo params) {
		
		return userMgrDAO.insertTeacher(params);
	}


	@Override
	public int updateTeacher(TeacherInfo params) {
		
		return userMgrDAO.updateTeacher(params);
	}


	@Override
	public int deleteTeacher(TeacherInfo params) {
		
		return userMgrDAO.deleteTeacher(params);
	}

	public int insertUserMaster(TeacherInfo params) {
		
		return userMgrDAO.insertUserMaster(params);
	}


	@Override
	public int updateUserMaster(TeacherInfo params) {
		
		return userMgrDAO.updateUserMaster(params);
	}


	@Override
	public int deleteUserMaster(TeacherInfo params) {
		
		return userMgrDAO.deleteUserMaster(params);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<UserCertificate> selectCertificationUserList(UserCertificate params) {
		
		return (List<UserCertificate>) userMgrDAO.selectCertificationUserList(params);
	}

	@Override
	public UserCertificateDetail selectCertificationUser(UserCertificateDetail params) {
		
		return userMgrDAO.selectCertificationUser(params);
	}
	
	@Override
	public UserCertificateDetail selectCertNumber(UserCertificateDetail params) {
		
		return userMgrDAO.selectCertNumber(params);
	}

	@Override
	public int insertCertNumber(CertificationInfo params) {
		
		return userMgrDAO.insertCertNumber(params);
	}

	@Override
	public int updateFaceYn(UserInfo params) {
		
		return userMgrDAO.updateFaceYn(params);
	}

	@Override
	@Transactional
	public int deleteEvaluationData(UserInfo params) {
		
		userMgrDAO.deleteBaselineEvaluation(params);
		userMgrDAO.deleteEvaluationProgressing(params);
		return userMgrDAO.updateEvaluationStudentInfo(params);
	}

	@Override
	@Transactional
	public int deleteTheoryData(UserInfo params) {
		
		userMgrDAO.updateBaselineTheory(params);
		userMgrDAO.deleteTheoryProgressing(params);
		return userMgrDAO.updateTheoryStudentInfo(params);
	}

	@Override
	@Transactional
	public int deleteDangerData(UserInfo params) {
		
		userMgrDAO.updateBaselineDanger(params);
		userMgrDAO.deleteDangerProgressing(params);
		return userMgrDAO.updateDangerStudentInfo(params);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<UserBaselineScore> selectBaselineStudentScore(UserBaselineScore params) {
		
		return (List<UserBaselineScore>) userMgrDAO.selectBaselineStudentScore(params);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<MenuPin> selectMenuPinList(MenuPin params) {
		
		return (List<MenuPin>) userMgrDAO.selectMenuPinList(params);
	}

	@Override
	public int insertMenuPin(MenuPin params) {
		
		return userMgrDAO.insertMenuPin(params);
	}

	@Override
	public int updateMenuPin(MenuPin params) {
		
		return userMgrDAO.updateMenuPin(params);
	}

	@Override
	public int selectCheckMenuPin(MenuPin params) {
		
		return userMgrDAO.selectCheckMenuPin(params);
	}		

}
