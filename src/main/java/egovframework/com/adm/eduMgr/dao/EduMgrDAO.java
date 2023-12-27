package egovframework.com.adm.eduMgr.dao;

import java.util.List;

import egovframework.com.adm.eduMgr.vo.Baseline;
import egovframework.com.adm.eduMgr.vo.EduDate;
import egovframework.com.adm.eduMgr.vo.Student;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("EduMgrDAO")
public class EduMgrDAO extends EgovAbstractMapper{
	
	private static final String Namespace = "egovframework.com.adm.eduMgr.dao.EduMgrDAO";
	
	public List<?> selectBaselineList(Baseline params) {
		return (List<?>)selectList(Namespace + ".selectBaselineList", params);
	}
	
	public int insertBaseline(Baseline params) {
		return insert(Namespace + ".insertBaseline", params);
	}
	
	public int updateBaseline(Baseline params) {
		return update(Namespace + ".updateBaseline", params);
	}	
	
	public int deleteBaseline(Baseline params) {
		return delete(Namespace + ".deleteBaseline", params);
	}	

	public Baseline selectBaseline(Baseline params) {
		return selectOne(Namespace + ".selectBaseline", params);
	}	
	
	
	public List<?> selectBaselineStudentList(Student params) {
		return (List<?>)selectList(Namespace + ".selectBaselineStudentList", params);
	}
	
	
	public Student selectBaselineStudent(Student params) {
		return selectOne(Namespace + ".selectBaselineStudent", params);
	}	
	
	public int insertBaselineStudent(Student params) {
		return insert(Namespace + ".insertBaselineStudent", params);
	}
	
	
	public int deleteBaselineStudent(Student params) {
		return delete(Namespace + ".deleteBaselineStudent", params);
	}		
	
	public int deleteBaselineStudentAll(Student params) {
		return delete(Namespace + ".deleteBaselineStudentAll", params);
	}		
	
	public int deleteBaselineLearning(EduDate params) {
		return delete(Namespace + ".deleteBaselineLearning", params);
	}		
	
	public int deleteBaselineLearningResult(EduDate params) {
		return delete(Namespace + ".deleteBaselineLearningResult", params);
	}		
	
	public int deleteBaselineEvaluationAll(EduDate params) {
		return delete(Namespace + ".deleteBaselineEvaluationAll", params);
	}		
	
	public int deleteBaselineEvaluationResultAll(EduDate params) {
		return delete(Namespace + ".deleteBaselineEvaluationResultAll", params);
	}
	
	public int deleteBaselineTheoryAll(EduDate params) {
		return delete(Namespace + ".deleteBaselineTheoryAll", params);
	}		
	
	public int deleteBaselineTheoryResultAll(EduDate params) {
		return delete(Namespace + ".deleteBaselineTheoryResultAll", params);
	}		
	
	public List<?> selectEduDateList(EduDate params) {
		return (List<?>)selectList(Namespace + ".selectEduDateList", params);
	}
	
	public List<?> selectEduDateListPop(EduDate params) {
		return (List<?>)selectList(Namespace + ".selectEduDateListPop", params);
	}
	
	public EduDate selectEduModuleList(EduDate params) {
		return selectOne(Namespace + ".selectEduModuleList", params);
	}	
	
	public List<?> selectEduMenuList(EduDate params) {
		return (List<?>)selectList(Namespace + ".selectEduMenuList", params);
	}	
	
	public int insertEduDate(EduDate params) {
		return insert(Namespace + ".insertEduDate", params);
	}
	
	
	public int deleteEduDate(EduDate params) {
		return delete(Namespace + ".deleteEduDate", params);
	}	

	
	public int deleteEduDateAll(EduDate params) {
		return delete(Namespace + ".deleteEduDateAll", params);
	}		
	
	public int insertBaselineCopy(Baseline params) {
		return insert(Namespace + ".insertBaselineCopy", params);
	}
	
	public int insertBaselineDateCopy(Baseline params) {
		return insert(Namespace + ".insertBaselineDateCopy", params);
	}
	
	public int insertBaselineStudentCopy(Baseline params) {
		return insert(Namespace + ".insertBaselineStudentCopy", params);
	}	
	
	public List<?> selectEduDateInfoList(Baseline params) {
		return (List<?>)selectList(Namespace + ".selectEduDateInfoList", params);
	}	
	
	public int updateBaselineEduDate(Baseline params) {
		return update(Namespace + ".updateBaselineEduDate", params);
	}		
	
	public int selectBaselineDataCount(Baseline params) {
		return selectOne(Namespace + ".selectBaselineDataCount", params);
	}
	
	public List<?> selectBaselineProcSeqList(Baseline params) {
		return (List<?>)selectList(Namespace + ".selectBaselineProcSeqList", params);
	}			
}
