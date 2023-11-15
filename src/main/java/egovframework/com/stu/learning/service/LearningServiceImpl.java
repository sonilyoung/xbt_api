package egovframework.com.stu.learning.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.com.stu.learning.dao.LearningDAO;
import egovframework.com.stu.learning.vo.Learning;
import egovframework.com.stu.learning.vo.LearningProblem;
import egovframework.com.stu.learning.vo.PointStd;
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
@Service("LearningService")
public class LearningServiceImpl implements LearningService {

    @Resource(name = "LearningDAO")
	private LearningDAO learningDAO;


	@Override
	public Learning selectBaseline(Learning params) {
		// TODO Auto-generated method stub
		return learningDAO.selectBaseline(params);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Learning> selectLearning(Learning params) {
		// TODO Auto-generated method stub
		return (List<Learning>) learningDAO.selectLearning(params);
	}	
	
	@Override
	public Learning selectModuleInfo(Learning params) {
		// TODO Auto-generated method stub
		return learningDAO.selectModuleInfo(params);
	}

	
	@Override
	@SuppressWarnings("unchecked")
	public List<LearningProblem> selectLearningProblems(LearningProblem params) {
		// TODO Auto-generated method stub
		return (List<LearningProblem>)learningDAO.selectLearningProblems(params);
	}

	@Override
	public int selectLearningProblemsCount(LearningProblem params) {
		// TODO Auto-generated method stub
		return learningDAO.selectLearningProblemsCount(params);
	}

	@Override
	public int insertLearningProblems(LearningProblem params) {
		// TODO Auto-generated method stub
		return learningDAO.insertLearningProblems(params);
	}
	
	
	@Override
	public int updateLearningProblems(LearningProblem params) {
		// TODO Auto-generated method stub
		return learningDAO.updateLearningProblems(params);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<LearningProblem> selectLearnProblemsList(LearningProblem params) {
		// TODO Auto-generated method stub
		return (List<LearningProblem>) learningDAO.selectLearnProblemsList(params);
	}
	
	@Override
	public PointStd selectPointStdScore(Learning params) {
		// TODO Auto-generated method stub
		return learningDAO.selectPointStdScore(params);
	}	

	@Override
	@SuppressWarnings("unchecked")
	public List<LearningProblem> selectLearnProblemsResultList(LearningProblem params) {
		// TODO Auto-generated method stub
		return (List<LearningProblem>) learningDAO.selectLearnProblemsResultList(params);
	}	
	

	@Override
	@SuppressWarnings("unchecked")
	public List<LearningProblem> selectLearnProblemsResult(LearningProblem params) {
		// TODO Auto-generated method stub
		return (List<LearningProblem>) learningDAO.selectLearnProblemsResult(params);
	}		

	@Override
	public Learning selectLearnAnswer(Learning params) {
		// TODO Auto-generated method stub
		return learningDAO.selectLearnAnswer(params);
	}	

	@Override
	public int updateLearningAnswer(Learning params) {
		// TODO Auto-generated method stub
		return learningDAO.updateLearningAnswer(params);
	}

	@Override
	public int updateLearningEnd(Learning params) {
		// TODO Auto-generated method stub
		return learningDAO.updateLearningEnd(params);
	}
	
	
	@Override
	public int insertLearningResult(Learning params) {
		// TODO Auto-generated method stub
		return learningDAO.insertLearningResult(params);
	}

	@Override
	public Learning selectLearningSum(Learning params) {
		// TODO Auto-generated method stub
		return learningDAO.selectLearningSum(params);
	}

	@Override
	public int updateLearningResult(Learning params) {
		// TODO Auto-generated method stub
		return learningDAO.updateLearningResult(params);
	}

	@Override
	public Learning selectLearningResultCount(Learning params) {
		// TODO Auto-generated method stub
		return learningDAO.selectLearningResultCount(params);
	}

	@Override
	public LearningProblem selectLearningProblemsMaxkey(LearningProblem params) {
		// TODO Auto-generated method stub
		return learningDAO.selectLearningProblemsMaxkey(params);
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public List<LearningProblem> selectWrongAnswerProblems(LearningProblem params) {
		// TODO Auto-generated method stub
		return (List<LearningProblem>)learningDAO.selectWrongAnswerProblems(params);
	}
	
	@Override
	public LearningProblem selectWrongAnswerProblemsMaxkey(LearningProblem params) {
		// TODO Auto-generated method stub
		return learningDAO.selectWrongAnswerProblemsMaxkey(params);
	}	

	@Override
	public int selectWrongAnswerProblemsCount(LearningProblem params) {
		// TODO Auto-generated method stub
		return learningDAO.selectWrongAnswerProblemsCount(params);
	}

	@Override
	public int insertWrongAnswerProblems(LearningProblem params) {
		// TODO Auto-generated method stub
		return learningDAO.insertWrongAnswerProblems(params);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<LearningProblem> selectWrongAnswerProblemsList(LearningProblem params) {
		// TODO Auto-generated method stub
		return (List<LearningProblem>) learningDAO.selectWrongAnswerProblemsList(params);
	}	
	
	@Override
	public Learning selectWrongAnswer(Learning params) {
		// TODO Auto-generated method stub
		return learningDAO.selectWrongAnswer(params);
	}	

	@Override
	public int updateWrongAnswer(Learning params) {
		// TODO Auto-generated method stub
		return learningDAO.updateWrongAnswer(params);
	}

	
	@Override
	public int updateWrongAnswerEnd(Learning params) {
		// TODO Auto-generated method stub
		return learningDAO.updateWrongAnswerEnd(params);
	}
	
	
	@Override
	public int insertWrongAnswerResult(Learning params) {
		// TODO Auto-generated method stub
		return learningDAO.insertWrongAnswerResult(params);
	}

	@Override
	public Learning selectWrongAnswerSum(Learning params) {
		// TODO Auto-generated method stub
		return learningDAO.selectWrongAnswerSum(params);
	}

	@Override
	public int updateWrongAnswerResult(Learning params) {
		// TODO Auto-generated method stub
		return learningDAO.updateWrongAnswerResult(params);
	}

	@Override
	public Learning selectWrongAnswerResultCount(Learning params) {
		// TODO Auto-generated method stub
		return learningDAO.selectWrongAnswerResultCount(params);
	}	
	
	@Override
	@SuppressWarnings("unchecked")
	public List<LearningProblem> selectWrongAnswerProblemsResultList(LearningProblem params) {
		// TODO Auto-generated method stub
		return (List<LearningProblem>) learningDAO.selectWrongAnswerProblemsResultList(params);
	}

	@Override
	public int selectLearningBaselineResultCount(Learning params) {
		// TODO Auto-generated method stub
		return learningDAO.selectLearningBaselineResultCount(params);
	}	
		
	
	@Override
	public int selectWrongAnswerBaselineResultCount(Learning params) {
		// TODO Auto-generated method stub
		return learningDAO.selectWrongAnswerBaselineResultCount(params);
	}

	
	/* 강화학습 */
	@Override
	public List<LearningProblem> selectForceLearningProblems(LearningProblem params) {
		// TODO Auto-generated method stub
		return (List<LearningProblem>)learningDAO.selectForceLearningProblems(params);
	}

	@Override
	public int selectForceLearningProblemsCount(LearningProblem params) {
		// TODO Auto-generated method stub
		return learningDAO.selectForceLearningProblemsCount(params);
	}

	@Override
	public int insertForceLearningProblems(LearningProblem params) {
		// TODO Auto-generated method stub
		return learningDAO.insertForceLearningProblems(params);
	}

	@Override
	public int updateForceLearningProblems(LearningProblem params) {
		// TODO Auto-generated method stub
		return learningDAO.updateForceLearningProblems(params);
	}

	@Override
	public List<LearningProblem> selectForceLearningProblemsList(LearningProblem params) {
		// TODO Auto-generated method stub
		return (List<LearningProblem>)learningDAO.selectForceLearningProblems(params);
	}

	@Override
	public List<LearningProblem> selectForceLearningProblemsResultList(LearningProblem params) {
		// TODO Auto-generated method stub
		return (List<LearningProblem>)learningDAO.selectForceLearningProblems(params);
	}

	@Override
	public Learning selectForceLearningAnswer(Learning params) {
		// TODO Auto-generated method stub
		return learningDAO.selectForceLearningAnswer(params);
	}

	@Override
	public int updateForceLearningAnswer(Learning params) {
		// TODO Auto-generated method stub
		return learningDAO.updateForceLearningAnswer(params);
	}

	@Override
	public int updateForceLearningEnd(Learning params) {
		// TODO Auto-generated method stub
		return learningDAO.updateForceLearningEnd(params);
	}

	@Override
	public int insertForceLearningResult(Learning params) {
		// TODO Auto-generated method stub
		return learningDAO.insertForceLearningResult(params);
	}

	@Override
	public Learning selectForceLearningSum(Learning params) {
		// TODO Auto-generated method stub
		return learningDAO.selectForceLearningSum(params);
	}

	@Override
	public int selectForceLearningBaselineResultCount(Learning params) {
		// TODO Auto-generated method stub
		return learningDAO.selectForceLearningBaselineResultCount(params);
	}		
	
	@Override
	public Learning selectForceLearningResultCount(Learning params) {
		// TODO Auto-generated method stub
		return learningDAO.selectForceLearningResultCount(params);
	}

	@Override
	public int updateForceLearningResult(Learning params) {
		// TODO Auto-generated method stub
		return learningDAO.updateForceLearningResult(params);
	}

	@Override
	public LearningProblem selectForceLearningProblemsMaxkey(LearningProblem params) {
		// TODO Auto-generated method stub
		return learningDAO.selectForceLearningProblemsMaxkey(params);
	}



	
}
