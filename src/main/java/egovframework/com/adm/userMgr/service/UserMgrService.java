package egovframework.com.adm.userMgr.service;

import java.util.List;

import egovframework.com.adm.system.vo.Notice;
import egovframework.com.adm.userMgr.vo.UserBaseline;
import egovframework.com.adm.userMgr.vo.UserBaselineDetail;
import egovframework.com.adm.userMgr.vo.UserBaselineSub;
import egovframework.com.adm.userMgr.vo.UserBaselineSubInfo;
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
	
	public UserInfo selectUser(UserInfo params);
	
	public int insertUser(UserInfo params);
	
	public int updateUser(UserInfo params);
	
	public int deleteUser(UserInfo params);	
	
	
	public List<UserBaseline> getUserBaselineList(UserBaseline params);
	
	public List<UserBaselineSub> getUserBaselineSubList(UserBaselineSub params);
	
	public Object getUserBaselineSubDetail(UserBaselineDetail params);

	public List<UserBaselineSubInfo> getUserBaselineSubDetailList(UserBaselineSubInfo params);
}
