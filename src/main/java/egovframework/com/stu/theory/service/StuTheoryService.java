package egovframework.com.stu.theory.service;

import java.util.List;

import egovframework.com.stu.learning.vo.Learning;
import egovframework.com.stu.learning.vo.LearningProblem;
import egovframework.com.stu.theory.vo.StuTheory;

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
public interface StuTheoryService {
	
	
	public List<StuTheory> selectTheoryList(StuTheory params);
	
	public StuTheory selectTheory(StuTheory params);

	public StuTheory selectTheoryProblemsMaxkey(StuTheory params);
	
	public StuTheory selectStudyLvlTheory(StuTheory params);

	public int selectTheoryProcessYnCount(StuTheory params);
	
	public int selectTheoryProblemsCount(StuTheory params);
	
	public int insertTheoryProblems(StuTheory params);
	
	public List<StuTheory> selectTheoryProblemsList(StuTheory params);
	
	public StuTheory selectTheoryAnswer(StuTheory params);
	
	public int updateTheoryAnswer(StuTheory params);
	
	public int selectTheoryBaselineResultCount(StuTheory params);
	
	public int insertTheoryResult(StuTheory params);
	
	public StuTheory selectTheorySum(StuTheory params);
	
	public StuTheory selectTheoryResultCount(StuTheory params);
	
	public int updateTheoryEnd(StuTheory params);
	
	public int updateTheoryResult(StuTheory params);
}
