package egovframework.com.stu.learning.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import egovframework.com.adm.contents.vo.Language;
import egovframework.com.adm.contents.vo.UnitGroup;
import egovframework.com.adm.contents.vo.UnitImg;
import egovframework.com.adm.contents.vo.UnitInformation;
import egovframework.com.adm.contents.vo.XbtSeq;
import egovframework.com.adm.contents.vo.XrayContents;
import egovframework.com.adm.contents.vo.XrayImgContents;
import egovframework.com.stu.learning.vo.Learning;
import egovframework.com.stu.learning.vo.LearningImg;
import egovframework.com.stu.learning.vo.LearningMainImg;
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
public interface LearningService {
	
	public Learning selectBaseline(Learning params);
	
	public Learning selectLearning(Learning params);
	
	public Learning selectModuleInfo(Learning params);

	public List<LearningProblem> selectLearningProblems(LearningProblem params);
	
	public int selectLearningProblemsCount(LearningProblem params);
	
	public int insertLearningProblems(LearningProblem params);
	
	public int updateLearningProblems(LearningProblem params);
	
	public List<LearningProblem> selectLearnProblemsList(LearningProblem params);
	
	public List<LearningProblem> selectLearnProblemsResultList(LearningProblem params);
	
	public List<LearningProblem> selectLearnProblemsResult(LearningProblem params);
	
	public List<LearningProblem> selectLeaningImgList(List<LearningProblem> params);
	
	public LearningImg selectLeaningImg(LearningImg params);
	
	public LearningMainImg selectCommonLearningImg(LearningImg params);
	
	public LearningMainImg selectCommonPracticeImg(LearningImg params);
	
	public LearningImg selectPracticeImg(LearningImg params);
	
	public LearningImg selectThreedAngle(LearningImg params);
	
	public Learning selectLearnAnswer(Learning params);
	
	public int updateLearningAnswer(Learning params);
	
	public int updateLearningEnd(Learning params);
	
	public int insertLearningResult(Learning params);
	
	public Learning selectLeaningSum(Learning params);
	
	public Learning selectLearningResultCount(Learning params);
	
	public int updateLearningResult(Learning params);
	
	public LearningProblem selectLearningProblemsMaxkey(LearningProblem params);
	
	public List<LearningProblem> selectWrongAnswerProblems(LearningProblem params);
	
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
	
	public int selectLearningBaselineResultCount(Learning params);
	
	public int selectWrongAnswerBaselineResultCount(Learning params);
	
}
