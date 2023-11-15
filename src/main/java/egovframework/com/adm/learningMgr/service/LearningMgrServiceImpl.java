package egovframework.com.adm.learningMgr.service;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import egovframework.com.adm.learningMgr.dao.LearningMgrDAO;
import egovframework.com.adm.learningMgr.vo.EduModule;
import egovframework.com.adm.learningMgr.vo.EduModulePop;
import egovframework.com.adm.learningMgr.vo.EduType;
import egovframework.com.adm.learningMgr.vo.PointStd;
import egovframework.com.adm.learningMgr.vo.XrayPoint;
import egovframework.com.adm.learningMgr.vo.XrayPointDetail;
import egovframework.com.common.dao.CommonDAO;
import egovframework.com.common.vo.Common;
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
    
    @Resource(name = "CommonDAO")
	private CommonDAO commonDAO;    


	@Override
	public List<EduModule> selectModuleList(EduModule params) {
		// TODO Auto-generated method stub
		return (List<EduModule>) learningMgrDAO.selectModuleList(params);
	}

	@Override
	public List<EduModule> selectModuleQuestion(EduModule params) {
		// TODO Auto-generated method stub
		return (List<EduModule>) learningMgrDAO.selectModuleQuestion(params);
	}
	
	@Override
	public List<EduModulePop> selectModuleRandom(EduModulePop params) {
		// TODO Auto-generated method stub
		return (List<EduModulePop>) learningMgrDAO.selectModuleRandom(params);
	}
	
		
	
	@Override
	@Transactional
	public int insertModule(EduModule params) throws Exception{
		// TODO Auto-generated method stub
		//12	학습컷	lc
		//13	학습슬라이드	ls
		//24	오답문제풀이컷	oxc
		//25	오답문제풀이슬라이드	oxs
		//14	AI강화학습컷 	ac
		//15	AI강화학습슬라이드 	as
		//21	XBT 평가컷	ec
		//22	XBT 평가슬라이드	es		
		
		int result = learningMgrDAO.insertModule(params);
		if(params.getBagList()!=null && params.getBagList().get(0)!=null) {
			for(String p : params.getBagList()) {
				EduModule dp = new EduModule();
				dp.setBagScanId(p);
				EduModule bagInfo = learningMgrDAO.selectXrayModuleContents(dp);
				bagInfo.setModuleId(params.getModuleId());
				bagInfo.setInsertId(params.getInsertId());
				learningMgrDAO.insertModuleQuestion(bagInfo);
			}
		}
		return result;	
	}
	
	@Override
	public int insertModuleQuestion(EduModule params) {
		// TODO Auto-generated method stub
		return learningMgrDAO.insertModuleQuestion(params);	
	}	

	@Override
	public int updateModule(EduModule params) {
		// TODO Auto-generated method stub
		int result = learningMgrDAO.updateModule(params);
		
		if(params.getBagList()!=null && params.getBagList().get(0)!=null) {
			learningMgrDAO.deleteModuleQuestion(params);
			for(String p : params.getBagList()) {
				EduModule dp = new EduModule();
				dp.setBagScanId(p);
				EduModule bagInfo = learningMgrDAO.selectXrayModuleContents(dp);
				bagInfo.setInsertId(params.getInsertId());
				bagInfo.setModuleId(params.getModuleId());
				learningMgrDAO.insertModuleQuestion(bagInfo);
			}
		}		
		return result;
	}


	@Override
	public int deleteModule(EduModule params) {
		// TODO Auto-generated method stub
		int result = learningMgrDAO.deleteModule(params);
		learningMgrDAO.deleteModuleQuestion(params);
		return result;
	}

	@Override
	public int deleteModuleQuestion(EduModule params) {
		// TODO Auto-generated method stub
		return learningMgrDAO.deleteModuleQuestion(params);
	}
	
	@Override
	public EduModule selectModule(EduModule params) {
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

	@Override
	public EduModule selectXrayModuleContents(EduModule params) {
		// TODO Auto-generated method stub
		return learningMgrDAO.selectXrayModuleContents(params);
	}

	@Override
	public List<EduModulePop> selectModuleXrayPopList(EduModulePop params) {
		// TODO Auto-generated method stub
		return (List<EduModulePop>) learningMgrDAO.selectModuleXrayPopList(params);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<PointStd> selectPointStdList(PointStd params) {
		// TODO Auto-generated method stub
		return (List<PointStd>)learningMgrDAO.selectPointStdList(params);
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public int insertPointStd(PointStd params) throws Exception{
		// TODO Auto-generated method stub
		int result = learningMgrDAO.insertPointStd(params);
		Common c = new Common();
		c.setLanguageCode("kr");
		c.setGroupId("actionDiv");
		List<Common> comList = (List<Common>) commonDAO.selectCommonList(c);		
		
		for(Common cl : comList) {
			params.setActionDiv(cl.getCodeValue());
			learningMgrDAO.insertPointStdDetail(params);
		}
		return result;
	}

	@Override
	public int updatePointStd(PointStd params) {
		// TODO Auto-generated method stub
		return learningMgrDAO.updatePointStd(params);
	}

	@Override
	public int deletePointStd(PointStd params) {
		// TODO Auto-generated method stub
		return learningMgrDAO.deletePointStd(params);
	}

	@Override
	public PointStd selectPointStd(PointStd params) {
		// TODO Auto-generated method stub
		return learningMgrDAO.selectPointStd(params);
	}	

	@Override
	@SuppressWarnings("unchecked")
	public List<PointStd> selectPointStdDetailList(PointStd params) {
		// TODO Auto-generated method stub
		return (List<PointStd>)learningMgrDAO.selectPointStdDetailList(params);
	}

	@Override
	public int insertPointStdDetail(PointStd params) {
		// TODO Auto-generated method stub
		return learningMgrDAO.insertPointStdDetail(params);
	}

	@Override
	public int updatePointStdDetail(PointStd params) {
		// TODO Auto-generated method stub
		return learningMgrDAO.updatePointStdDetail(params);
	}

	@Override
	public int deletePointStdDetail(PointStd params) {
		// TODO Auto-generated method stub
		return learningMgrDAO.deletePointStdDetail(params);
	}

	@Override
	public PointStd selectPointStdDetail(PointStd params) {
		// TODO Auto-generated method stub
		return learningMgrDAO.selectPointStdDetail(params);
	}

	@Override
	@Transactional
	public int insertModuleMasterCopy(EduModule params){
		// TODO Auto-generated method stub
		int result = learningMgrDAO.insertModuleMasterCopy(params);
		learningMgrDAO.insertModuleDetailCopy(params);
		return result;
	}

	@Override
	public int insertModuleDetailCopy(EduModule params) {
		// TODO Auto-generated method stub
		return learningMgrDAO.insertModuleDetailCopy(params);
	}	
	
}
