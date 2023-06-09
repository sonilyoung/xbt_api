package egovframework.com.adm.eduMgr.service;

import java.util.List;

import egovframework.com.adm.eduMgr.vo.Baseline;
import egovframework.com.adm.eduMgr.vo.EduDate;
import egovframework.com.adm.eduMgr.vo.Student;

/**
 * 사용자관리에 관한 인터페이스클래스를 정의한다.
 * 
 * @author 공통서비스 개발팀 조재영
 * @since 2009.04.10
 * @see
 * @version 1.0
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
public interface EduMgrService {
	
	public List<Baseline> selectBaselineList(Baseline params);
	
	public int insertBaseline(Baseline params);
	
	public int updateBaseline(Baseline params);
	
	public int deleteBaseline(Baseline params);

	public Baseline selectBaseline(Baseline params);	
	
	public List<Student> selectBaselineStudentList(Student params);
	
	public Student selectBaselineStudent(Student params);
	
	public int insertBaselineStudent(Student params);
	
	public int deleteBaselineStudent(Student params);
	
	public int deleteBaselineStudentAll(Student params);	
	
	public List<EduDate> selectEduDateList(EduDate params);
	
	public List<EduDate> selectEduDateListPop(EduDate params);
	
	public List<EduDate> selectEduMenuList(EduDate params);
	
	public int insertEduDate(EduDate params);
	
	public int deleteEduDate(EduDate params);
	
	public int deleteEduDateAll(EduDate params);
	
}
