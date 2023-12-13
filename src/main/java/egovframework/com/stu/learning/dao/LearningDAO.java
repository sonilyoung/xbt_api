package egovframework.com.stu.learning.dao;

import java.util.List;

import egovframework.com.stu.learning.vo.Learning;
import egovframework.com.stu.learning.vo.LearningProblem;
import egovframework.com.stu.learning.vo.PointStd;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("LearningDAO")
public class LearningDAO extends EgovAbstractMapper{
	
	private static final String Namespace = "egovframework.com.stu.learning.dao.LearningDAO";
	
	
	public Learning selectBaseline(Learning params) {
		return selectOne(Namespace + ".selectBaseline", params);
	}	

	public Learning selectModuleInfo(Learning params) {
		return selectOne(Namespace + ".selectModuleInfo", params);
	}		
	
	public List<?> selectLearning(Learning params) {
		return (List<?>)selectList(Namespace + ".selectLearning", params);
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
	
	public LearningProblem selectXrayAnswer(LearningProblem params) {
		return selectOne(Namespace + ".selectXrayAnswer", params);
	}		
	
	public List<?> selectOxLearning(LearningProblem params) {
		return (List<?>)selectList(Namespace + ".selectOxLearning", params);
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
	
	public Learning selectLearningSum(Learning params) {
		return selectOne(Namespace + ".selectLearningSum", params);
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
	
	/* 오답문제풀이 */
	public List<?> selectWrongAnswerProblems(LearningProblem params) {
		return (List<?>)selectList(Namespace + ".selectWrongAnswerProblems", params);
	}
	
	public PointStd selectPointStdScore(Learning params) {
		return selectOne(Namespace + ".selectPointStdScore", params);
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
	
	
	/* 강화학습 */
	public List<?> selectForceLearningProblems(LearningProblem params) {
		return (List<?>)selectList(Namespace + ".selectForceLearningProblems", params);
	}
	
	public int selectForceLearningProblemsCount(LearningProblem params) {
		return selectOne(Namespace + ".selectForceLearningProblemsCount", params);
	}
	
	public int insertForceLearningProblems(LearningProblem params) {
		return insert(Namespace + ".insertForceLearningProblems", params);
	}	
	
	public int updateForceLearningProblems(LearningProblem params) {
		return update(Namespace + ".updateForceLearningProblems", params);
	}		
	
	public int updateForceLearningEnd(Learning params) {
		return update(Namespace + ".updateForceLearningEnd", params);
	}	
	
	public List<?> selectForceLearningProblemsList(LearningProblem params) {
		return (List<?>)selectList(Namespace + ".selectForceLearningProblemsList", params);
	}
	
	public List<?> selectForceLearningProblemsResultList(LearningProblem params) {
		return (List<?>)selectList(Namespace + ".selectForceLearningProblemsResultList", params);
	}	
	
	public Learning selectForceLearningAnswer(Learning params) {
		return selectOne(Namespace + ".selectForceLearningAnswer", params);
	}		
	
	public int updateForceLearningAnswer(Learning params) {
		return update(Namespace + ".updateForceLearningAnswer", params);
	}	
	
	
	public int insertForceLearningResult(Learning params) {
		return insert(Namespace + ".insertForceLearningResult", params);
	}		
	
	public Learning selectForceLearningSum(Learning params) {
		return selectOne(Namespace + ".selectForceLearningSum", params);
	}		
	
	public int updateForceLearningResult(Learning params) {
		return update(Namespace + ".updateForceLearningResult", params);
	}		
	
	public int selectForceLearningBaselineResultCount(Learning params) {
		return selectOne(Namespace + ".selectForceLearningBaselineResultCount", params);
	}	
	
	public Learning selectForceLearningResultCount(Learning params) {
		return selectOne(Namespace + ".selectForceLearningResultCount", params);
	}
	
	public LearningProblem selectForceLearningProblemsMaxkey(LearningProblem params) {
		return selectOne(Namespace + ".selectForceLearningProblemsMaxkey", params);
	}	
}
