package egovframework.com.stu.theory.dao;

import java.util.List;

import egovframework.com.stu.learning.vo.Learning;
import egovframework.com.stu.learning.vo.LearningProblem;
import egovframework.com.stu.learning.vo.PointStd;
import egovframework.com.stu.theory.vo.StuTheory;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("StuTheoryDAO")
public class StuTheoryDAO extends EgovAbstractMapper{
	
	private static final String Namespace = "egovframework.com.stu.theory.dao.StuTheoryDAO";
	
	

	public List<?> selectTheoryList(StuTheory params) {
		return (List<?>)selectList(Namespace + ".selectTheoryList", params);
	}	
	
	public StuTheory selectTheory(StuTheory params) {
		return selectOne(Namespace + ".selectTheory", params);
	}	
	
	public StuTheory selectTheoryProblemsMaxkey(StuTheory params) {
		return selectOne(Namespace + ".selectTheoryProblemsMaxkey", params);
	}	

	public int selectTheoryProblemsCount(StuTheory params) {
		return selectOne(Namespace + ".selectTheoryProblemsCount", params);
	}
	
	public int insertTheoryProblems(StuTheory params) {
		return insert(Namespace + ".insertTheoryProblems", params);
	}		
	
	public int insertLearningResult(Learning params) {
		return insert(Namespace + ".insertLearningResult", params);
	}	
	
	public List<?> selectTheoryProblemsList(StuTheory params) {
		return (List<?>)selectList(Namespace + ".selectTheoryProblemsList", params);
	}	
		
	
	
}