package egovframework.com.adm.eduMgr.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.com.adm.eduMgr.dao.EduMgrDAO;
import egovframework.com.adm.eduMgr.vo.EduBaselineDetailProc;
import egovframework.com.adm.eduMgr.vo.EduBaselineMenu;
import egovframework.com.adm.eduMgr.vo.EduBaselineProc;
import egovframework.com.adm.eduMgr.vo.EduBaselineSubProc;
import egovframework.com.adm.eduMgr.vo.EduClass;
import egovframework.com.adm.eduMgr.vo.EduGroupMgr;
import egovframework.com.adm.eduMgr.vo.EduProc;
import egovframework.com.adm.eduMgr.vo.EduProcDetail;
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
@Service("EduMgrService")
public class EduMgrServiceImpl implements EduMgrService {

    @Resource(name = "EduMgrDAO")
	private EduMgrDAO eduMgrDAO;


	@Override
	public List<EduGroupMgr> getEduGroupList(EduGroupMgr params) {
		// TODO Auto-generated method stub
		return (List<EduGroupMgr>) eduMgrDAO.getEduGroupList(params);
	}


	@Override
	public List<EduClass> getEduClassList(EduClass params) {
		// TODO Auto-generated method stub
		return (List<EduClass>) eduMgrDAO.getEduClassList(params);
	}


	@Override
	public List<EduProc> getEduProcList(EduProc params) {
		// TODO Auto-generated method stub
		return (List<EduProc>) eduMgrDAO.getEduProcList(params);
	}


	@Override
	public EduProcDetail getEduProcDetail(EduProcDetail params) {
		// TODO Auto-generated method stub
		return (EduProcDetail) eduMgrDAO.getEduProcDetail(params);
	}


	@Override
	public List<EduBaselineProc> getEduBaselineList(EduBaselineProc params) {
		// TODO Auto-generated method stub
		return (List<EduBaselineProc>) eduMgrDAO.getEduBaselineList(params);
	}


	@Override
	public EduBaselineDetailProc getEduBaselineDetail(EduBaselineDetailProc params) {
		// TODO Auto-generated method stub
		return (EduBaselineDetailProc) eduMgrDAO.getEduBaselineDetail(params);
	}


	@Override
	public List<EduBaselineSubProc> getEduBaselineSubList(EduBaselineSubProc params) {
		// TODO Auto-generated method stub
		return (List<EduBaselineSubProc>) eduMgrDAO.getEduBaselineSubList(params);
	}


	@Override
	public List<EduBaselineMenu> getEduBaselineMenuList(EduBaselineMenu params) {
		// TODO Auto-generated method stub
		return (List<EduBaselineMenu>) eduMgrDAO.getEduBaselineMenuList(params);
	}


	@Override
	public List<EduBaselineMenu> getEduBaselineMenuSubList(EduBaselineMenu params) {
		// TODO Auto-generated method stub
		return (List<EduBaselineMenu>) eduMgrDAO.getEduBaselineMenuSubList(params);
	}



}
