package egovframework.com.adm.userMgr.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.com.adm.userMgr.dao.UserMgrDAO;
import egovframework.com.adm.userMgr.vo.UserBaseline;
import egovframework.com.adm.userMgr.vo.UserBaselineDetail;
import egovframework.com.adm.userMgr.vo.UserBaselineSub;
import egovframework.com.adm.userMgr.vo.UserBaselineSubInfo;
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
	public List<UserInfo> getUserList(UserInfo params) {
		// TODO Auto-generated method stub
		return (List<UserInfo>) userMgrDAO.getUserList(params);
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

}
