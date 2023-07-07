package egovframework.com.adm.learningMgr.dao;

import java.util.List;

import egovframework.com.adm.learningMgr.vo.EduModule;
import egovframework.com.adm.learningMgr.vo.EduModulePop;
import egovframework.com.adm.learningMgr.vo.EduType;
import egovframework.com.adm.learningMgr.vo.PointStd;
import egovframework.com.adm.learningMgr.vo.XrayPoint;
import egovframework.com.adm.learningMgr.vo.XrayPointDetail;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("LearningMgrDAO")
public class LearningMgrDAO extends EgovAbstractMapper{
	
	private static final String Namespace = "egovframework.com.adm.learningMgr.dao.LearningMgrDAO";
	
	public List<?> selectModuleList(EduModule params) {
		return (List<?>)selectList(Namespace + ".selectModuleList", params);
	}
	
	public List<?> selectModuleQuestion(EduModule params) {
		return (List<?>)selectList(Namespace + ".selectModuleQuestion", params);
	}
		
	
	public List<?> selectModuleRandom(EduModulePop params) {
		return (List<?>)selectList(Namespace + ".selectModuleRandom", params);
	}
			
	
	public int insertModule(EduModule params) {
		return insert(Namespace + ".insertModule", params);
	}
	
	public int insertModuleQuestion(EduModule params) {
		return insert(Namespace + ".insertModuleQuestion", params);
	}	
	
	public int updateModule(EduModule params) {
		return update(Namespace + ".updateModule", params);
	}	
	
	public int deleteModule(EduModule params) {
		return delete(Namespace + ".deleteModule", params);
	}
	
	public int deleteModuleQuestion(EduModule params) {
		return delete(Namespace + ".deleteModuleQuestion", params);
	}
	
	public EduModule selectModule(EduModule params) {
		return selectOne(Namespace + ".selectModule", params);
	}	

	public List<?> getXrayPointList(XrayPoint params) {
		return (List<?>)selectList(Namespace + ".getXrayPointList", params);
	}
	
	public List<?> getXrayPointDetailList(XrayPointDetail params) {
		return (List<?>)selectList(Namespace + ".getXrayPointDetailList", params);
	}
	
	public List<?> getEduTypeList(EduType params) {
		return (List<?>)selectList(Namespace + ".getEduTypeList", params);
	}			
	
	public EduModule selectXrayModuleContents(EduModule params) {
		return selectOne(Namespace + ".selectXrayModuleContents", params);
	}		
	
	public List<?> selectModuleXrayPopList(EduModulePop params) {
		return (List<?>)selectList(Namespace + ".selectModuleXrayPopList", params);
	}	
	
	public List<?> selectPointStdList(PointStd params) {
		return (List<?>)selectList(Namespace + ".selectPointStdList", params);
	}
	
	public int insertPointStd(PointStd params) {
		return insert(Namespace + ".insertPointStd", params);
	}
	
	public int updatePointStd(PointStd params) {
		return update(Namespace + ".updatePointStd", params);
	}	
	
	public int deletePointStd(PointStd params) {
		return delete(Namespace + ".deletePointStd", params);
	}	

	public PointStd selectPointStd(PointStd params) {
		return selectOne(Namespace + ".selectPointStd", params);
	}	
	
	public List<?> selectPointStdDetailList(PointStd params) {
		return (List<?>)selectList(Namespace + ".selectPointStdDetailList", params);
	}
	
	public int insertPointStdDetail(PointStd params) {
		return insert(Namespace + ".insertPointStdDetail", params);
	}
	
	public int updatePointStdDetail(PointStd params) {
		return update(Namespace + ".updatePointStdDetail", params);
	}	
	
	public int deletePointStdDetail(PointStd params) {
		return delete(Namespace + ".deletePointStdDetail", params);
	}	

	public PointStd selectPointStdDetail(PointStd params) {
		return selectOne(Namespace + ".selectPointStdDetail", params);
	}		
	
	public int insertModuleMasterCopy(EduModule params) {
		return insert(Namespace + ".insertModuleMasterCopy", params);
	}
	
	public int insertModuleDetailCopy(EduModule params) {
		return insert(Namespace + ".insertModuleDetailCopy", params);
	}		
}
