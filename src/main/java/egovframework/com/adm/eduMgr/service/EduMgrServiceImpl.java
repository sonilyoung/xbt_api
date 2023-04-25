package egovframework.com.adm.eduMgr.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.com.adm.eduMgr.dao.EduMgrDAO;
import egovframework.com.adm.eduMgr.vo.Baseline;
import egovframework.com.adm.eduMgr.vo.EduDate;
import egovframework.com.adm.eduMgr.vo.Student;
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
@Service("EduMgrService")
public class EduMgrServiceImpl implements EduMgrService {

    @Resource(name = "EduMgrDAO")
	private EduMgrDAO eduMgrDAO;


	@Override
	public List<Baseline> selectBaselineList(Baseline params) {
		// TODO Auto-generated method stub
		return (List<Baseline>) eduMgrDAO.selectBaselineList(params);
	}
	
	@Override
	public int insertBaseline(Baseline params) {
		// TODO Auto-generated method stub
		return eduMgrDAO.insertBaseline(params);
	}

	@Override
	public int updateBaseline(Baseline params) {
		// TODO Auto-generated method stub
		return eduMgrDAO.updateBaseline(params);
	}

	@Override
	public int deleteBaseline(Baseline params) {
		// TODO Auto-generated method stub
		return eduMgrDAO.deleteBaseline(params);
	}

	@Override
	public Baseline selectBaseline(Baseline params) {
		// TODO Auto-generated method stub
		return eduMgrDAO.selectBaseline(params);
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Student> selectBaselineStudentList(Student params) {
		// TODO Auto-generated method stub
		return (List<Student>)eduMgrDAO.selectBaselineStudentList(params);
	}

	@Override
	public int insertBaselineStudent(Student params) {
		// TODO Auto-generated method stub
		return eduMgrDAO.insertBaselineStudent(params);
	}


	@Override
	public int deleteBaselineStudent(Student params) {
		// TODO Auto-generated method stub
		return eduMgrDAO.deleteBaselineStudent(params);
	}	
		
	@Override
	@SuppressWarnings("unchecked")
	public List<EduDate> selectEduDateList(EduDate params) {
		// TODO Auto-generated method stub
		return (List<EduDate>)eduMgrDAO.selectEduDateList(params);
	}

	@Override
	public int insertEduDate(EduDate params) {
		// TODO Auto-generated method stub
		return eduMgrDAO.insertEduDate(params);
	}


	@Override
	public int deleteEduDate(EduDate params) {
		// TODO Auto-generated method stub
		return eduMgrDAO.deleteEduDate(params);
	}


}
