package egovframework.com.adm.learningMgr.service;

import java.util.List;

import egovframework.com.adm.learningMgr.vo.EduModule;
import egovframework.com.adm.learningMgr.vo.EduModulePop;
import egovframework.com.adm.learningMgr.vo.EduType;
import egovframework.com.adm.learningMgr.vo.PointStd;
import egovframework.com.adm.learningMgr.vo.XrayPoint;
import egovframework.com.adm.learningMgr.vo.XrayPointDetail;

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
public interface LearningMgrService {
	
	public List<EduModule> selectModuleList(EduModule params);
	
	public List<EduModule> selectModuleQuestion(EduModule params);
	
	public List<EduModulePop> selectModuleRandom(EduModulePop params);
	
	public int insertModule(EduModule params)throws Exception;
	
	public int insertModuleQuestion(EduModule params);
	
	public int updateModule(EduModule params);
	
	public int deleteModule(EduModule params);
	
	public int deleteModuleQuestion(EduModule params);

	public EduModule selectModule(EduModule params);	
	
	public List<XrayPoint> getXrayPointList(XrayPoint params);
	
	public List<XrayPointDetail> getXrayPointDetailList(XrayPointDetail params);
	
	public List<EduType> getEduTypeList(EduType params);
	
	public EduModule selectXrayModuleContents(EduModule params);
	
	public List<EduModulePop> selectModuleXrayPopList(EduModulePop params);
	
	public List<PointStd> selectPointStdList(PointStd params);
	
	public int insertPointStd(PointStd params)throws Exception;
	
	public int updatePointStd(PointStd params);
	
	public int deletePointStd(PointStd params);

	public PointStd selectPointStd(PointStd params);	
	
	
	public List<PointStd> selectPointStdDetailList(PointStd params);
	
	public int insertPointStdDetail(PointStd params);
	
	public int updatePointStdDetail(PointStd params);
	
	public int deletePointStdDetail(PointStd params);

	public PointStd selectPointStdDetail(PointStd params);	
	
	public int insertModuleMasterCopy(EduModule params);
	
	public int insertModuleDetailCopy(EduModule params);
}
