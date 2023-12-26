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
import lombok.extern.log4j.Log4j2;


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
@Log4j2
@Service("UserMgrService")
public class UserMgrServiceImpl implements UserMgrService {

    @Resource(name = "UserMgrDAO")
	private UserMgrDAO userMgrDAO;


	@Override
	@SuppressWarnings("unchecked")
	public List<UserInfo> selectUserList(UserInfo params) {
		// TODO Auto-generated method stub
		return (List<UserInfo>) userMgrDAO.selectUserList(params);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<UserInfo> selectUserListPop(UserInfo params) {
		// TODO Auto-generated method stub
		return (List<UserInfo>) userMgrDAO.selectUserListPop(params);
	}	
	
	@Override
	@SuppressWarnings("unchecked")
	public List<UserInfo> selectDuplicationUserList(UserInfo params) {
		// TODO Auto-generated method stub
		return (List<UserInfo>) userMgrDAO.selectDuplicationUserList(params);
	}		
	
	@Override
	@SuppressWarnings("unchecked")
	public List<UserBaseline> selectBaselineUserList(UserBaseline params) {
		// TODO Auto-generated method stub
		return (List<UserBaseline>) userMgrDAO.selectBaselineUserList(params);
	}
	
	@Override
	public UserBaseline selectBaselineBasicTotalScore(UserBaseline params) {
		// TODO Auto-generated method stub
		return userMgrDAO.selectBaselineBasicTotalScore(params);
	}	
	
	@Override
	public UserBaseline selectBaselineEvaluation(UserBaseline params) {
		// TODO Auto-generated method stub
		return userMgrDAO.selectBaselineEvaluation(params);
	}	
	
	@Override
	public UserBaseline selectBaselineTherory(UserBaseline params) {
		// TODO Auto-generated method stub
		return userMgrDAO.selectBaselineTherory(params);
	}		
	
	@Override
	public UserBaseline selectBaselinePractice(UserBaseline params) {
		// TODO Auto-generated method stub
		return userMgrDAO.selectBaselinePractice(params);
	}		
	
	@Override
	public int updateBaselineUser(UserBaseline params) {
		// TODO Auto-generated method stub
		return userMgrDAO.updateBaselineUser(params);
	}
	
		
	
	@Override
	public UserInfo selectUserCheck(UserInfo params) {
		// TODO Auto-generated method stub
		return (UserInfo) userMgrDAO.selectUserCheck(params);
	}	

	@Override
	public UserInfo selectUser(UserInfo params) {
		// TODO Auto-generated method stub
		return (UserInfo) userMgrDAO.selectUser(params);
	}


	@Override
	public int insertUser(UserInfo params) {
		// TODO Auto-generated method stub
		return userMgrDAO.insertUser(params);
	}
	
	@Override
	public int insertDuplicationUser(UserInfo params) {
		// TODO Auto-generated method stub
		return userMgrDAO.insertDuplicationUser(params);
	}


	@Override
	public int updateUser(UserInfo params) {
		// TODO Auto-generated method stub
		return userMgrDAO.updateUser(params);
	}


	@Override
	public int deleteUser(UserInfo params) {
		// TODO Auto-generated method stub
		return userMgrDAO.deleteUser(params);
	}	
	
	
	@Override
	public int deleteDuplicationUser(UserInfo params) {
		// TODO Auto-generated method stub
		return userMgrDAO.deleteDuplicationUser(params);
	}	

	@Override
	public List<UserBaseline> getUserBaselineList(UserBaseline params) {
		// TODO Auto-generated method stub
		return (List<UserBaseline>) userMgrDAO.getUserBaselineList(params);
	}


	@Override
	public List<UserBaselineSub> getUserBaselineSubList(UserBaselineSub params) {
		// TODO Auto-generated method stub
		return (List<UserBaselineSub>) userMgrDAO.getUserBaselineSubList(params);
	}


	@Override
	public Object getUserBaselineSubDetail(UserBaselineDetail params) {
		// TODO Auto-generated method stub
		return (UserBaselineDetail) userMgrDAO.getUserBaselineSubDetail(params);
	}


	@Override
	public List<UserBaselineSubInfo> getUserBaselineSubDetailList(UserBaselineSubInfo params) {
		// TODO Auto-generated method stub
		return (List<UserBaselineSubInfo>) userMgrDAO.getUserBaselineSubDetailList(params);
	}


	@Override
	public List<TeacherInfo> selectTeacherList(TeacherInfo params) {
		// TODO Auto-generated method stub
		return (List<TeacherInfo>) userMgrDAO.selectTeacherList(params);
	}
	
	@Override
	public TeacherInfo selectTeacherCheck(TeacherInfo params) {
		// TODO Auto-generated method stub
		return (TeacherInfo) userMgrDAO.selectTeacherCheck(params);
	}	

	@Override
	public TeacherInfo selectTeacher(TeacherInfo params) {
		// TODO Auto-generated method stub
		return (TeacherInfo) userMgrDAO.selectTeacher(params);
	}


	@Override
	public int insertTeacher(TeacherInfo params) {
		// TODO Auto-generated method stub
		return userMgrDAO.insertTeacher(params);
	}


	@Override
	public int updateTeacher(TeacherInfo params) {
		// TODO Auto-generated method stub
		return userMgrDAO.updateTeacher(params);
	}


	@Override
	public int deleteTeacher(TeacherInfo params) {
		// TODO Auto-generated method stub
		return userMgrDAO.deleteTeacher(params);
	}

	public int insertUserMaster(TeacherInfo params) {
		// TODO Auto-generated method stub
		return userMgrDAO.insertUserMaster(params);
	}


	@Override
	public int updateUserMaster(TeacherInfo params) {
		// TODO Auto-generated method stub
		return userMgrDAO.updateUserMaster(params);
	}


	@Override
	public int deleteUserMaster(TeacherInfo params) {
		// TODO Auto-generated method stub
		return userMgrDAO.deleteUserMaster(params);
	}

	@Override
	public List<UserCertificate> selectCertificationUserList(UserCertificate params) {
		// TODO Auto-generated method stub
		return (List<UserCertificate>) userMgrDAO.selectCertificationUserList(params);
	}

	@Override
	public UserCertificateDetail selectCertificationUser(UserCertificateDetail params) {
		// TODO Auto-generated method stub
		return userMgrDAO.selectCertificationUser(params);
	}
	
	@Override
	public UserCertificateDetail selectCertNumber(UserCertificateDetail params) {
		// TODO Auto-generated method stub
		return userMgrDAO.selectCertNumber(params);
	}

	@Override
	public int insertCertNumber(CertificationInfo params) {
		// TODO Auto-generated method stub
		return userMgrDAO.insertCertNumber(params);
	}

	@Override
	public int updateFaceYn(UserInfo params) {
		// TODO Auto-generated method stub
		return userMgrDAO.updateFaceYn(params);
	}

	@Override
	@Transactional
	public int deleteEvaluationData(UserInfo params) {
		// TODO Auto-generated method stub
		userMgrDAO.deleteBaselineEvaluation(params);
		userMgrDAO.deleteEvaluationProgressing(params);
		return userMgrDAO.updateEvaluationStudentInfo(params);
	}

	@Override
	@Transactional
	public int deleteTheoryData(UserInfo params) {
		// TODO Auto-generated method stub
		userMgrDAO.updateBaselineTheory(params);
		userMgrDAO.deleteTheoryProgressing(params);
		return userMgrDAO.updateTheoryStudentInfo(params);
	}

	@Override
	@Transactional
	public int deleteDangerData(UserInfo params) {
		// TODO Auto-generated method stub
		userMgrDAO.updateBaselineDanger(params);
		userMgrDAO.deleteDangerProgressing(params);
		return userMgrDAO.updateDangerStudentInfo(params);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<UserBaselineScore> selectBaselineStudentScore(UserBaselineScore params) {
		// TODO Auto-generated method stub
		return (List<UserBaselineScore>) userMgrDAO.selectBaselineStudentScore(params);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<MenuPin> selectMenuPinList(MenuPin params) {
		// TODO Auto-generated method stub
		return (List<MenuPin>) userMgrDAO.selectMenuPinList(params);
	}

	@Override
	public int insertMenuPin(MenuPin params) {
		// TODO Auto-generated method stub
		return userMgrDAO.insertMenuPin(params);
	}

	@Override
	public int updateMenuPin(MenuPin params) {
		// TODO Auto-generated method stub
		return userMgrDAO.updateMenuPin(params);
	}		

}
