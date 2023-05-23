package egovframework.com.stu.learning.dao;

import java.util.List;

import egovframework.com.stu.learning.vo.Learning;
import egovframework.com.stu.learning.vo.LearningProblem;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("LearningDAO")
public class LearningDAO extends EgovAbstractMapper{
	
	private static final String Namespace = "egovframework.com.stu.learning.dao.LearningDAO";
	
	
	public Learning selectBaseline(Learning params) {
		return selectOne(Namespace + ".selectBaseline", params);
	}	

	public List<?> selectLearning(Learning params) {
		return (List<?>)selectList(Namespace + ".selectLearning", params);
	}	
	
	public Learning selectModuleInfo(Learning params) {
		return selectOne(Namespace + ".selectModuleInfo", params);
	}		
	
	public List<?> selectLearningProblems(LearningProblem params) {
		return (List<?>)selectList(Namespace + ".selectLearningProblems", params);
	}
	
	
	public int selectLearningProblemsCount(LearningProblem params) {
		return selectOne(Namespace + ".selectLearningProblemsCount", params);
	}
		
	
	public int insertLearningProblems(LearningProblem params) {
		return insert(Namespace + ".insertLearningProblems", params);
	}	
	
	public int updateLearningProblems(LearningProblem params) {
		return update(Namespace + ".updateLearningProblems", params);
	}		
	
	public int updateLearningEnd(Learning params) {
		return update(Namespace + ".updateLearningEnd", params);
	}	
	
	public List<?> selectLearnProblemsList(LearningProblem params) {
		return (List<?>)selectList(Namespace + ".selectLearnProblemsList", params);
	}
	
	public List<?> selectLearnProblemsResultList(LearningProblem params) {
		return (List<?>)selectList(Namespace + ".selectLearnProblemsResultList", params);
	}	
	
	public List<?> selectLearnProblemsResult(LearningProblem params) {
		return (List<?>)selectList(Namespace + ".selectLearnProblemsResult", params);
	}	
	
	public Learning selectLearnAnswer(Learning params) {
		return selectOne(Namespace + ".selectLearnAnswer", params);
	}		
	
	public int updateLearningAnswer(Learning params) {
		return update(Namespace + ".updateLearningAnswer", params);
	}	
	
	
	public int insertLearningResult(Learning params) {
		return insert(Namespace + ".insertLearningResult", params);
	}		
	
	public Learning selectLeaningSum(Learning params) {
		return selectOne(Namespace + ".selectLeaningSum", params);
	}		
	
	public int updateLearningResult(Learning params) {
		return update(Namespace + ".updateLearningResult", params);
	}		
	
	public Learning selectLearningResultCount(Learning params) {
		return selectOne(Namespace + ".selectLearningResultCount", params);
	}
	
	public LearningProblem selectLearningProblemsMaxkey(LearningProblem params) {
		return selectOne(Namespace + ".selectLearningProblemsMaxkey", params);
	}
	
	public List<?> selectWrongAnswerProblems(LearningProblem params) {
		return (List<?>)selectList(Namespace + ".selectWrongAnswerProblems", params);
	}
	
	public LearningProblem selectWrongAnswerProblemsMaxkey(LearningProblem params) {
		return selectOne(Namespace + ".selectWrongAnswerProblemsMaxkey", params);
	}	
	
	public int selectWrongAnswerProblemsCount(LearningProblem params) {
		return selectOne(Namespace + ".selectWrongAnswerProblemsCount", params);
	}
		
	public int insertWrongAnswerProblems(LearningProblem params) {
		return insert(Namespace + ".insertWrongAnswerProblems", params);
	}	
	
	public List<?> selectWrongAnswerProblemsList(LearningProblem params) {
		return (List<?>)selectList(Namespace + ".selectWrongAnswerProblemsList", params);
	}	
		
	public Learning selectWrongAnswer(Learning params) {
		return selectOne(Namespace + ".selectWrongAnswer", params);
	}		
	
	public int updateWrongAnswer(Learning params) {
		return update(Namespace + ".updateWrongAnswer", params);
	}		
	
	public int insertWrongAnswerResult(Learning params) {
		return insert(Namespace + ".insertWrongAnswerResult", params);
	}		
	
	public Learning selectWrongAnswerSum(Learning params) {
		return selectOne(Namespace + ".selectWrongAnswerSum", params);
	}		
	
	public int updateWrongAnswerResult(Learning params) {
		return update(Namespace + ".updateWrongAnswerResult", params);
	}		
	
	public Learning selectWrongAnswerResultCount(Learning params) {
		return selectOne(Namespace + ".selectWrongAnswerResultCount", params);
	}	
	
	public int updateWrongAnswerEnd(Learning params) {
		return update(Namespace + ".updateWrongAnswerEnd", params);
	}	
		
	public List<?> selectWrongAnswerProblemsResultList(LearningProblem params) {
		return (List<?>)selectList(Namespace + ".selectWrongAnswerProblemsResultList", params);
	}		
	
	public int selectLearningBaselineResultCount(Learning params) {
		return selectOne(Namespace + ".selectLearningBaselineResultCount", params);
	}
			
	public int selectWrongAnswerBaselineResultCount(Learning params) {
		return selectOne(Namespace + ".selectWrongAnswerBaselineResultCount", params);
	}
	
}
