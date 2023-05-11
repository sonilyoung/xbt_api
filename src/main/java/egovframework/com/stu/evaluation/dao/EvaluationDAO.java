package egovframework.com.stu.evaluation.dao;

import java.util.List;

import egovframework.com.stu.learning.vo.Learning;
import egovframework.com.stu.learning.vo.LearningProblem;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("EvaluationDAO")
public class EvaluationDAO extends EgovAbstractMapper{
	
	private static final String Namespace = "egovframework.com.stu.evaluation.dao.EvaluationDAO";
	
	
	public int selectEvaluationProblemsCount(LearningProblem params) {
		return selectOne(Namespace + ".selectEvaluationProblemsCount", params);
	}
		
	
	public int insertEvaluationProblems(LearningProblem params) {
		return insert(Namespace + ".insertEvaluationProblems", params);
	}	
	
	public int updateEvaluationProblems(LearningProblem params) {
		return update(Namespace + ".updateEvaluationProblems", params);
	}		
	
	public List<?> selectEvaluationProblemsList(LearningProblem params) {
		return (List<?>)selectList(Namespace + ".selectEvaluationProblemsList", params);
	}
	
	public Learning selectEvaluationAnswer(Learning params) {
		return selectOne(Namespace + ".selectEvaluationAnswer", params);
	}		
	
	public int updateEvaluationAnswer(Learning params) {
		return update(Namespace + ".updateEvaluationAnswer", params);
	}	
	
	
	public int insertEvaluationResult(Learning params) {
		return insert(Namespace + ".insertEvaluationResult", params);
	}		
	
	public Learning selectEvaluationSum(Learning params) {
		return selectOne(Namespace + ".selectEvaluationSum", params);
	}
	
	
	public Learning selectEvaluationResultCount(Learning params) {
		return selectOne(Namespace + ".selectEvaluationResultCount", params);
	}		
		
	
	public int updateEvaluationResult(Learning params) {
		return update(Namespace + ".updateEvaluationResult", params);
	}		
	
	public LearningProblem selectEvaluationProblemsMaxkey(LearningProblem params) {
		return selectOne(Namespace + ".selectEvaluationProblemsMaxkey", params);
	}		
	
}
