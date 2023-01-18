package egovframework.com.adm.eduMgr.service;

import java.util.List;

import egovframework.com.adm.eduMgr.vo.EduBaselineDetailProc;
import egovframework.com.adm.eduMgr.vo.EduBaselineMenu;
import egovframework.com.adm.eduMgr.vo.EduBaselineProc;
import egovframework.com.adm.eduMgr.vo.EduBaselineSubProc;
import egovframework.com.adm.eduMgr.vo.EduClass;
import egovframework.com.adm.eduMgr.vo.EduGroupMgr;
import egovframework.com.adm.eduMgr.vo.EduProc;
import egovframework.com.adm.eduMgr.vo.EduProcDetail;

/**
 * 사용자관리에 관한 인터페이스클래스를 정의한다.
 * 
 * @author 공통서비스 개발팀 조재영
 * @since 2009.04.10
 * @see
 * @version 1.0
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
public interface EduMgrService {
	
	public List<EduGroupMgr> getEduGroupList(EduGroupMgr params);
	
	public List<EduClass> getEduClassList(EduClass params);
	
	public List<EduProc> getEduProcList(EduProc params);
	
	public EduProcDetail getEduProcDetail(EduProcDetail params);
	
	public List<EduBaselineProc> getEduBaselineList(EduBaselineProc params);
	
	public EduBaselineDetailProc getEduBaselineDetail(EduBaselineDetailProc params);
	
	public List<EduBaselineSubProc> getEduBaselineSubList(EduBaselineSubProc params);
	
	public List<EduBaselineMenu> getEduBaselineMenuList(EduBaselineMenu params);
	
	public List<EduBaselineMenu> getEduBaselineMenuSubList(EduBaselineMenu params);
	
	
}
