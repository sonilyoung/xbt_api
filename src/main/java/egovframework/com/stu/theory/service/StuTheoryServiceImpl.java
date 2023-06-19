package egovframework.com.stu.theory.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.com.stu.learning.vo.Learning;
import egovframework.com.stu.learning.vo.LearningProblem;
import egovframework.com.stu.theory.dao.StuTheoryDAO;
import egovframework.com.stu.theory.vo.StuTheory;
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
@Service("StuTheoryService")
public class StuTheoryServiceImpl implements StuTheoryService {

    @Resource(name = "StuTheoryDAO")
	private StuTheoryDAO stuTheoryDAO;



	
	@Override
	@SuppressWarnings("unchecked")
	public List<StuTheory> selectTheoryList(StuTheory params) {
		// TODO Auto-generated method stub
		return (List<StuTheory>) stuTheoryDAO.selectTheoryList(params);
	}
	
	@Override
	public StuTheory selectTheory(StuTheory params) {
		// TODO Auto-generated method stub
		return stuTheoryDAO.selectTheory(params);
	}

	@Override
	public StuTheory selectTheoryProblemsMaxkey(StuTheory params) {
		// TODO Auto-generated method stub
		return stuTheoryDAO.selectTheoryProblemsMaxkey(params);
	}

	@Override
	public int selectTheoryProblemsCount(StuTheory params) {
		// TODO Auto-generated method stub
		return stuTheoryDAO.selectTheoryProblemsCount(params);
	}
	

	@Override
	public int insertTheoryProblems(StuTheory params) {
		// TODO Auto-generated method stub
		return stuTheoryDAO.insertTheoryProblems(params);
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public List<StuTheory> selectTheoryProblemsList(StuTheory params) {
		// TODO Auto-generated method stub
		return (List<StuTheory>) stuTheoryDAO.selectTheoryProblemsList(params);
	}

	@Override
	public StuTheory selectTheoryAnswer(StuTheory params) {
		// TODO Auto-generated method stub
		return stuTheoryDAO.selectTheoryAnswer(params);
	}
	

	@Override
	public int selectTheoryProcessYnCount(StuTheory params) {
		// TODO Auto-generated method stub
		return stuTheoryDAO.selectTheoryProcessYnCount(params);
	}
	

	@Override
	public int updateTheoryAnswer(StuTheory params) {
		// TODO Auto-generated method stub
		return stuTheoryDAO.updateTheoryAnswer(params);
	}

	@Override
	public int selectTheoryBaselineResultCount(StuTheory params) {
		// TODO Auto-generated method stub
		return stuTheoryDAO.selectTheoryBaselineResultCount(params);
	}

	@Override
	public int insertTheoryResult(StuTheory params) {
		// TODO Auto-generated method stub
		return stuTheoryDAO.insertTheoryResult(params);
	}	
	
	@Override
	public StuTheory selectTheorySum(StuTheory params) {
		// TODO Auto-generated method stub
		return stuTheoryDAO.selectTheorySum(params);
	}

	@Override
	public StuTheory selectTheoryResultCount(StuTheory params) {
		// TODO Auto-generated method stub
		return stuTheoryDAO.selectTheoryResultCount(params);
	}

	@Override
	public int updateTheoryEnd(StuTheory params) {
		// TODO Auto-generated method stub
		return stuTheoryDAO.updateTheoryEnd(params);
	}

	@Override
	public int updateTheoryResult(StuTheory params) {
		// TODO Auto-generated method stub
		return stuTheoryDAO.updateTheoryResult(params);
	}

	@Override
	public StuTheory selectStudyLvlTheory(StuTheory params) {
		// TODO Auto-generated method stub
		return stuTheoryDAO.selectStudyLvlTheory(params);
	}
	
		
}
