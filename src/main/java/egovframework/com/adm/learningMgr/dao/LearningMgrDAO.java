package egovframework.com.adm.learningMgr.dao;

import java.util.List;

import egovframework.com.adm.learningMgr.vo.EduModule;
import egovframework.com.adm.learningMgr.vo.EduType;
import egovframework.com.adm.learningMgr.vo.XrayPoint;
import egovframework.com.adm.learningMgr.vo.XrayPointDetail;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("LearningMgrDAO")
public class LearningMgrDAO extends EgovAbstractMapper{
	
	private static final String Namespace = "egovframework.com.adm.learningMgr.dao.LearningMgrDAO";
	
	public List<?> getXrayModuleList(EduModule params) {
		return (List<?>)selectList(Namespace + ".getXrayModuleList", params);
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
}
