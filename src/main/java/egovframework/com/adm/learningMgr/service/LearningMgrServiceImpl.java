package egovframework.com.adm.learningMgr.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.com.adm.learningMgr.dao.LearningMgrDAO;
import egovframework.com.adm.learningMgr.vo.EduModule;
import egovframework.com.adm.learningMgr.vo.EduType;
import egovframework.com.adm.learningMgr.vo.XrayPoint;
import egovframework.com.adm.learningMgr.vo.XrayPointDetail;
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
@Service("LearningMgrService")
public class LearningMgrServiceImpl implements LearningMgrService {

    @Resource(name = "LearningMgrDAO")
	private LearningMgrDAO learningMgrDAO;


	@Override
	public List<EduModule> selectModuleList(EduModule params) {
		// TODO Auto-generated method stub
		return (List<EduModule>) learningMgrDAO.selectModuleList(params);
	}

	@Override
	public int insertModule(EduModule params) {
		// TODO Auto-generated method stub
		return learningMgrDAO.insertModule(params);	}


	@Override
	public int updateModule(EduModule params) {
		// TODO Auto-generated method stub
		return learningMgrDAO.updateModule(params);
	}


	@Override
	public int deleteModule(EduModule params) {
		// TODO Auto-generated method stub
		return learningMgrDAO.deleteModule(params);
	}


	@Override
	public Module selectModule(EduModule params) {
		// TODO Auto-generated method stub
		return learningMgrDAO.selectModule(params);
	}	
	

	@Override
	public List<XrayPoint> getXrayPointList(XrayPoint params) {
		// TODO Auto-generated method stub
		return (List<XrayPoint>) learningMgrDAO.getXrayPointList(params);
	}


	@Override
	public List<XrayPointDetail> getXrayPointDetailList(XrayPointDetail params) {
		// TODO Auto-generated method stub
		return (List<XrayPointDetail>) learningMgrDAO.getXrayPointDetailList(params);
	}
	
	@Override
	public List<EduType> getEduTypeList(EduType params) {
		// TODO Auto-generated method stub
		return (List<EduType>) learningMgrDAO.getEduTypeList(params);
	}

	

}
