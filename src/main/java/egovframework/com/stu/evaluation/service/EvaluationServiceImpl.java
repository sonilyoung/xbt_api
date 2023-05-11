package egovframework.com.stu.evaluation.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.com.stu.evaluation.dao.EvaluationDAO;
import egovframework.com.stu.learning.vo.Learning;
import egovframework.com.stu.learning.vo.LearningProblem;
import lombok.extern.log4j.Log4j2;


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
@Log4j2
@Service("EvaluationService")
public class EvaluationServiceImpl implements EvaluationService {

    @Resource(name = "EvaluationDAO")
	private EvaluationDAO evaluationDAO;


	@Override
	public int selectEvaluationProblemsCount(LearningProblem params) {
		// TODO Auto-generated method stub
		return evaluationDAO.selectEvaluationProblemsCount(params);
	}

	@Override
	public int insertEvaluationProblems(LearningProblem params) {
		// TODO Auto-generated method stub
		return evaluationDAO.insertEvaluationProblems(params);
	}
	
	
	@Override
	public int updateEvaluationProblems(LearningProblem params) {
		// TODO Auto-generated method stub
		return evaluationDAO.updateEvaluationProblems(params);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<LearningProblem> selectEvaluationProblemsList(LearningProblem params) {
		// TODO Auto-generated method stub
		return (List<LearningProblem>) evaluationDAO.selectEvaluationProblemsList(params);
	}

	
	@Override
	public Learning selectEvaluationAnswer(Learning params) {
		// TODO Auto-generated method stub
		return evaluationDAO.selectEvaluationAnswer(params);
	}	

	@Override
	public int updateEvaluationAnswer(Learning params) {
		// TODO Auto-generated method stub
		return evaluationDAO.updateEvaluationAnswer(params);
	}

	@Override
	public int insertEvaluationResult(Learning params) {
		// TODO Auto-generated method stub
		return evaluationDAO.insertEvaluationResult(params);
	}

	@Override
	public Learning selectEvaluationSum(Learning params) {
		// TODO Auto-generated method stub
		return evaluationDAO.selectEvaluationSum(params);
	}

	@Override
	public int updateEvaluationResult(Learning params) {
		// TODO Auto-generated method stub
		return evaluationDAO.updateEvaluationResult(params);
	}

	@Override
	public Learning selectEvaluationResultCount(Learning params) {
		// TODO Auto-generated method stub
		return evaluationDAO.selectEvaluationResultCount(params);
	}

	@Override
	public LearningProblem selectEvaluationProblemsMaxkey(LearningProblem params) {
		// TODO Auto-generated method stub
		return evaluationDAO.selectEvaluationProblemsMaxkey(params);
	}


		

}
