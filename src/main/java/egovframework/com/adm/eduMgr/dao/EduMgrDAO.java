package egovframework.com.adm.eduMgr.dao;

import java.util.List;

import egovframework.com.adm.eduMgr.vo.EduBaselineDetailProc;
import egovframework.com.adm.eduMgr.vo.EduBaselineMenu;
import egovframework.com.adm.eduMgr.vo.EduBaselineProc;
import egovframework.com.adm.eduMgr.vo.EduBaselineSubProc;
import egovframework.com.adm.eduMgr.vo.EduClass;
import egovframework.com.adm.eduMgr.vo.EduGroupMgr;
import egovframework.com.adm.eduMgr.vo.EduProc;
import egovframework.com.adm.eduMgr.vo.EduProcDetail;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("EduMgrDAO")
public class EduMgrDAO extends EgovAbstractMapper{
	
	private static final String Namespace = "egovframework.com.adm.eduMgr.dao.EduMgrDAO";
	
	public List<?> getEduGroupList(EduGroupMgr params) {
		return (List<?>)selectList(Namespace + ".getEduGroupList", params);
	}
	
	public List<?> getEduClassList(EduClass params) {
		return (List<?>)selectList(Namespace + ".getEduClassList", params);
	}	
	
	public List<?> getEduProcList(EduProc params) {
		return (List<?>)selectList(Namespace + ".getEduProcList", params);
	}	
	
	public Object getEduProcDetail(EduProcDetail params) {
		return selectOne(Namespace + ".getEduProcDetail", params);
	}		
	
	public List<?> getEduBaselineList(EduBaselineProc params) {
		return (List<?>)selectList(Namespace + ".getEduBaselineList", params);
	}
	
	public Object getEduBaselineDetail(EduBaselineDetailProc params) {
		return selectOne(Namespace + ".getEduBaselineDetail", params);
	}	
	
	public List<?> getEduBaselineSubList(EduBaselineSubProc params) {
		return (List<?>)selectList(Namespace + ".getEduBaselineSubList", params);
	}
	
	public List<?> getEduBaselineMenuList(EduBaselineMenu params) {
		return (List<?>)selectList(Namespace + ".getEduBaselineMenuList", params);
	}	
	
	public List<?> getEduBaselineMenuSubList(EduBaselineMenu params) {
		return (List<?>)selectList(Namespace + ".getEduBaselineMenuSubList", params);
	}		
	
	
}
