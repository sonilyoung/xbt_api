package egovframework.com.stu.learning.service;

import java.util.List;

import egovframework.com.stu.learning.vo.Learning;
import egovframework.com.stu.learning.vo.LearningProblem;
import egovframework.com.stu.learning.vo.PointStd;

/**
 * 사용자관리에 관한 인터페이스클래스를 정의한다.
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
 *
 *      </pre>
 */
public interface LearningService {
	
	public Learning selectBaseline(Learning params);
	
	public List<Learning> selectLearning(Learning params);
	
	public Learning selectModuleInfo(Learning params);

	public List<LearningProblem> selectLearningProblems(LearningProblem params);
	
	public int selectLearningProblemsCount(LearningProblem params);
	
	public int insertLearningProblems(LearningProblem params);
	
	public int updateLearningProblems(LearningProblem params);
	
	public List<LearningProblem> selectLearnProblemsList(LearningProblem params);
	
	public List<LearningProblem> selectLearnProblemsResultList(LearningProblem params);
	
	public List<LearningProblem> selectLearnProblemsResult(LearningProblem params);
	
	public Learning selectLearnAnswer(Learning params);
	
	public int updateLearningAnswer(Learning params);
	
	public int updateLearningEnd(Learning params);
	
	public int insertLearningResult(Learning params);
	
	public Learning selectLearningSum(Learning params);
	
	public Learning selectLearningResultCount(Learning params);
	
	public int updateLearningResult(Learning params);
	
	public LearningProblem selectLearningProblemsMaxkey(LearningProblem params);
	
	public int selectLearningBaselineResultCount(Learning params);
	
	public int selectWrongAnswerBaselineResultCount(Learning params);
	
	public List<LearningProblem> selectWrongAnswerProblems(LearningProblem params);
	
	public PointStd selectPointStdScore(Learning params);
	
	public LearningProblem selectWrongAnswerProblemsMaxkey(LearningProblem params);
	
	public int selectWrongAnswerProblemsCount(LearningProblem params);
	
	public int insertWrongAnswerProblems(LearningProblem params);
	
	public List<LearningProblem> selectWrongAnswerProblemsList(LearningProblem params);
	
	public Learning selectWrongAnswer(Learning params);
	
	public int updateWrongAnswer(Learning params);
	
	public int updateWrongAnswerEnd(Learning params);
	
	public int insertWrongAnswerResult(Learning params);
	
	public Learning selectWrongAnswerSum(Learning params);
	
	public Learning selectWrongAnswerResultCount(Learning params);
	
	public int updateWrongAnswerResult(Learning params);
		
	public List<LearningProblem> selectWrongAnswerProblemsResultList(LearningProblem params);
	
	
	
	/*강화학습*/
	public List<LearningProblem> selectForceLearningProblems(LearningProblem params);
	
	public int selectForceLearningProblemsCount(LearningProblem params);
	
	public int insertForceLearningProblems(LearningProblem params);
	
	public int updateForceLearningProblems(LearningProblem params);
	
	public List<LearningProblem> selectForceLearningProblemsList(LearningProblem params);
	
	public List<LearningProblem> selectForceLearningProblemsResultList(LearningProblem params);
	
	public Learning selectForceLearningAnswer(Learning params);
	
	public int updateForceLearningAnswer(Learning params);
	
	public int updateForceLearningEnd(Learning params);
	
	public int insertForceLearningResult(Learning params);
	
	public Learning selectForceLearningSum(Learning params);
	
	public int selectForceLearningBaselineResultCount(Learning params);
	
	public Learning selectForceLearningResultCount(Learning params);
	
	public int updateForceLearningResult(Learning params);
	
	public LearningProblem selectForceLearningProblemsMaxkey(LearningProblem params);
	
	public int selectCommonScoreResult(Learning params);
	
}
