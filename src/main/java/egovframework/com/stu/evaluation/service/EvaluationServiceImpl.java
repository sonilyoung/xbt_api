package egovframework.com.stu.evaluation.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.com.stu.evaluation.dao.EvaluationDAO;
import egovframework.com.stu.learning.vo.Learning;
import egovframework.com.stu.learning.vo.LearningProblem;
//import lombok.extern.log4j.Log4j2;


/**
 * 사용자관리에 관한 비지니스 클래스를 정의한다.
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
 *   2014.12.08	 이기하			암호화방식 변경(OfficeFileScrty.encryptPassword)
 *
 *      </pre>
 */
@Service("EvaluationService")
public class EvaluationServiceImpl implements EvaluationService {

    @Resource(name = "EvaluationDAO")
	private EvaluationDAO evaluationDAO;


	@Override
	public int selectEvaluationProblemsCount(LearningProblem params) {
		
		return evaluationDAO.selectEvaluationProblemsCount(params);
	}

	@Override
	public int insertEvaluationProblems(LearningProblem params) {
		
		return evaluationDAO.insertEvaluationProblems(params);
	}
	
	
	@Override
	public int updateEvaluationEnd(Learning params) {
		
		return evaluationDAO.updateEvaluationEnd(params);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<LearningProblem> selectEvaluationProblemsList(LearningProblem params) {
		
		return (List<LearningProblem>) evaluationDAO.selectEvaluationProblemsList(params);
	}

	
	@Override
	public Learning selectEvaluationAnswer(Learning params) {
		
		return evaluationDAO.selectEvaluationAnswer(params);
	}	

	@Override
	public int selectEvaluationProcessYnCount(LearningProblem params) {
		
		return evaluationDAO.selectEvaluationProcessYnCount(params);
	}
		
	
	@Override
	public int updateEvaluationAnswer(Learning params) {
		
		return evaluationDAO.updateEvaluationAnswer(params);
	}

	@Override
	public int insertEvaluationResult(Learning params) {
		
		return evaluationDAO.insertEvaluationResult(params);
	}

	@Override
	public Learning selectEvaluationSum(Learning params) {
		
		return evaluationDAO.selectEvaluationSum(params);
	}

	@Override
	public int updateEvaluationResult(Learning params) {
		
		return evaluationDAO.updateEvaluationResult(params);
	}

	@Override
	public Learning selectEvaluationResultCount(Learning params) {
		
		return evaluationDAO.selectEvaluationResultCount(params);
	}

	@Override
	public LearningProblem selectEvaluationProblemsMaxkey(LearningProblem params) {
		
		return evaluationDAO.selectEvaluationProblemsMaxkey(params);
	}

	@Override
	public int selectEvaluationBaselineResultCount(Learning params) {
		
		return evaluationDAO.selectEvaluationBaselineResultCount(params);
	}


		

}
