package egovframework.com.stu.evaluation.service;

import java.util.List;

import egovframework.com.stu.learning.vo.Learning;
import egovframework.com.stu.learning.vo.LearningProblem;

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
public interface EvaluationService {

	public int selectEvaluationProblemsCount(LearningProblem params);
	
	public int insertEvaluationProblems(LearningProblem params);
	
	public int updateEvaluationProblems(LearningProblem params);
	
	public List<LearningProblem> selectEvaluationProblemsList(LearningProblem params);
	
	public Learning selectEvaluationAnswer(Learning params);
	
	public int updateEvaluationAnswer(Learning params);
	
	public int insertEvaluationResult(Learning params);
	
	public Learning selectEvaluationSum(Learning params);
	
	public Learning selectEvaluationResultCount(Learning params);
	
	public int updateEvaluationResult(Learning params);
		
	public LearningProblem selectEvaluationProblemsMaxkey(LearningProblem params);
	
	public int selectEvaluationBaselineResultCount(Learning params);
}
