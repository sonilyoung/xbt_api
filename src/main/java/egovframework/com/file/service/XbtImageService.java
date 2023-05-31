package egovframework.com.file.service;

import java.util.List;

import egovframework.com.adm.theory.vo.Theory;
import egovframework.com.common.vo.LearningImg;
import egovframework.com.common.vo.LearningMainImg;
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
public interface XbtImageService {
	
	public List<LearningProblem> selectLeaningImgList(List<LearningProblem> params);
	
	public LearningImg selectLeaningImg(LearningImg params);
	
	public LearningMainImg selectCommonLearningImg(LearningImg params);
	
	public LearningMainImg selectCommonPracticeImg(LearningImg params);
	
	public LearningImg selectPracticeImg(LearningImg params);
	
	public LearningImg selectThreedAngle(LearningImg params);
	
	public LearningImg selectAdmAllUnitImg(LearningImg params);
	
	public LearningImg selectAdmAllBagImg(LearningImg params);	
	
	public Theory selectTheoryImg(Theory params);
	
}
